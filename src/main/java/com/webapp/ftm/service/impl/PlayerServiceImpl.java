package com.webapp.ftm.service.impl;

import com.webapp.ftm.dto.PlayerDTO;

import com.webapp.ftm.dto.PlayerSearchResponseDTO;
import com.webapp.ftm.dto.request.PlayerSearchRequestDTO;
import com.webapp.ftm.exception.custom.InvalidForeignPlayerException;
import com.webapp.ftm.exception.custom.InvalidPlayerAgeException;
import com.webapp.ftm.model.PlayerEntity;
import com.webapp.ftm.model.PlayerStatisticEntity;
import com.webapp.ftm.model.RuleEntity;
import com.webapp.ftm.repository.PlayerRepository;
import com.webapp.ftm.repository.PlayerStatisticRepository;
import com.webapp.ftm.service.PlayerService;
import com.webapp.ftm.utility.mapper.impl.PlayerSearchResponseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerStatisticRepository playerStatisticRepository;
    @Autowired
    private PlayerSearchResponseMapper playerSearchResponseMapper;



    @Override
    public List<PlayerSearchResponseDTO> findAll(PlayerSearchRequestDTO playerSearchRequestDTO) {
        List<PlayerEntity> playerEntities= playerRepository.findAllPlayers(playerSearchRequestDTO);
        //Convert all playerEntitíes to playerSearchResponses
        return playerSearchResponseMapper.listMapper(playerEntities);
    }


    @Override
    public void updatePlayer(PlayerDTO playerDTO) {
        PlayerEntity playerEntity= playerRepository.findById(playerDTO.getId()).get();
        List<PlayerStatisticEntity> playerStatisticEntities= playerEntity.getPlayerStatistics();
        for(PlayerStatisticEntity playerStatistic: playerStatisticEntities){
            RuleEntity ruleEntity= playerStatistic.getPlayerStatisticPk().getTournamentSeasonEntity().getRule();
            if(!checkDateOfBirth(playerDTO,playerStatistic))
                throw  new InvalidPlayerAgeException("Lỗi vì tồn tại cầu thủ không ở trong độ tuổi từ  "+ruleEntity.getMinAge().toString()+" đến "+
                        ruleEntity.getMaxAge().toString()+" ages.",null);
            if(!checkType(playerDTO,playerStatistic))
                throw new InvalidForeignPlayerException("Lỗi vì số lượng cầu thủ nước ngoài nhiều hơn "+ ruleEntity.getMaxNumOfForeigners()+ ".",null);
            playerEntity.setName(playerDTO.getName());
            playerEntity.setType(playerDTO.getType());
            playerEntity.setDateOfBirth(playerDTO.getDateOfBirth());
            playerEntity.setNote(playerDTO.getNote());
            playerRepository.save(playerEntity);
        }
    }

    private Boolean checkDateOfBirth(PlayerDTO playerDTO, PlayerStatisticEntity playerStatisticEntity){
        RuleEntity ruleEntity= playerStatisticEntity.getPlayerStatisticPk().getTournamentSeasonEntity().getRule();
        LocalDate currentDate= LocalDate.now();
        LocalDate dateOfBirth= playerDTO.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(dateOfBirth,currentDate).getYears();
        if(age>ruleEntity.getMaxAge().intValue()||age<ruleEntity.getMinAge().intValue()) return false;
        return true;
    }

    private Boolean checkType(PlayerDTO playerDTO,PlayerStatisticEntity playerStatisticEntity){
        if(playerDTO.getType().startsWith("NATIONAL")) return true;
        PlayerEntity playerEntity= playerRepository.findById(playerDTO.getId()).get();
        if(playerDTO.getType().equals(playerEntity.getType())) return true;
        Long maxNumOfForeigners= playerStatisticEntity.getPlayerStatisticPk().getTournamentSeasonEntity().getRule().getMaxNumOfForeigners();
        List<PlayerStatisticEntity> playerStatisticsOfTeam =
                playerStatisticRepository.findAllByTournamentAndSeasonAndTeam(
                        playerStatisticEntity.getPlayerStatisticPk().getTournamentSeasonEntity().getTournamentSeasonPk().getTournamentEntity().getId(),
                        playerStatisticEntity.getPlayerStatisticPk().getTournamentSeasonEntity().getTournamentSeasonPk().getSeasonEntity().getId(),
                        playerStatisticEntity.getPlayerStatisticPk().getTeamEntity().getId());
        Long cnt=0L;
        for(PlayerStatisticEntity playerStatistic: playerStatisticsOfTeam){
            if(playerStatistic.getPlayerStatisticPk().getPlayerEntity().getType().startsWith("FOREIGN")) cnt++;
        }
        //Check the number of foreigners in the team list.
        if (cnt==maxNumOfForeigners) return false;
        return true;
    }

}
