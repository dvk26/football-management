package com.webapp.ftm.service.impl;

import com.webapp.ftm.dto.PlayerDTO;
import com.webapp.ftm.dto.TeamDTO;
import com.webapp.ftm.dto.response.MatchResponseDTO;
import com.webapp.ftm.enums.TournamentStatus;
import com.webapp.ftm.exception.custom.*;
import com.webapp.ftm.model.*;
import com.webapp.ftm.model.pk.PlayerStatisticPk;
import com.webapp.ftm.model.pk.TeamStatisticPk;
import com.webapp.ftm.model.pk.TournamentSeasonPk;
import com.webapp.ftm.repository.*;
import com.webapp.ftm.service.TeamRegisterService;
import org.apache.tomcat.util.digester.Rule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Period;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TeamRegisterServiceImpl implements TeamRegisterService {

    @Autowired
    private TournamentSeasonRepository tournamentSeasonRepository;
    @Autowired
    private TeamStatisticRepository teamStatisticRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerStatisticRepository playerStatisticRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TeamStatisticEntity registerTeam(TeamDTO teamDTO) {
        TournamentSeasonEntity tournamentSeasonEntity=new TournamentSeasonEntity();

        // Find TournamentSeasonEntity if not found save new TournamentSeasonEntity
        try{
            tournamentSeasonEntity=tournamentSeasonRepository.findByTournamentSeasonPk_TournamentEntity_IdAndTournamentSeasonPk_SeasonEntity_Id(teamDTO.getTournamentId(),teamDTO.getSeasonId()).get();
        }catch(Exception e){
            // Find tournament and season entity from database
            TournamentEntity tournamentEntity = tournamentRepository.findById(teamDTO.getTournamentId()).get();
            SeasonEntity seasonEntity = seasonRepository.findById(teamDTO.getSeasonId()).get();
            //Set attribute for tournament season
            TournamentSeasonPk tournamentSeasonPk = new TournamentSeasonPk();
            tournamentSeasonPk.setSeasonEntity(seasonEntity);
            tournamentSeasonPk.setTournamentEntity(tournamentEntity);
            tournamentSeasonEntity.setTournamentSeasonPk(tournamentSeasonPk);
            tournamentSeasonEntity.setStatus(TournamentStatus.BEGINSOON);
            tournamentSeasonEntity.setRule(ruleRepository.findAllByOrderByTimeCreatedDesc().get(0));// Get the newest rule
            //Save tournamentEntity
            tournamentSeasonEntity=tournamentSeasonRepository.save(tournamentSeasonEntity);
        }
        if(tournamentSeasonEntity.getStatus().name() != TournamentStatus.BEGINSOON.name())
            throw new InvalidTournamentSeasonException("Lỗi vì tại giải đấu "+tournamentSeasonEntity.getTournamentSeasonPk().getTournamentEntity().getName().toString()+
                    " vào mùa giải " +tournamentSeasonEntity.getTournamentSeasonPk().getSeasonEntity().getName().toString()+ " đã được thi đấu không thể thêm đội bóng ",
                    teamDTO);
        if(checkTeamName(tournamentSeasonEntity,teamDTO))
            throw new InvalidTeamNameException("Đã tồn tại tên đội bóng " + teamDTO.getName()+ " giải đấu " +
                    tournamentSeasonEntity.getTournamentSeasonPk().getTournamentEntity().getName().toString()+
                    " vào mùa giải " +tournamentSeasonEntity.getTournamentSeasonPk().getSeasonEntity().getName().toString()+".",
                    teamDTO);
        //Save TeamEntity
        TeamEntity teamEntity=new TeamEntity();
        teamEntity.setName(teamDTO.getName());
        teamEntity.setHomeCourt(teamDTO.getHomeCourt());
        teamEntity= teamRepository.save(teamEntity);
        //Create primary key for teamStatistic
        TeamStatisticPk teamStatisticPk= new TeamStatisticPk();
        teamStatisticPk.setTeamEntity(teamEntity);
        teamStatisticPk.setTournamentSeasonEntity(tournamentSeasonEntity);
        // Initialize teamStatisticsEntity
        TeamStatisticEntity teamStatisticEntity= TeamStatisticEntity.builder()
                .lost(0L).drawn(0L).won(0L).played(0L).scored(0L).conceded(0L).point(0L).teamStatisticPk(teamStatisticPk).build();
        System.out.println(teamStatisticEntity.getTeamStatisticPk().getTournamentSeasonEntity().getRule().getId());
        return teamStatisticRepository.save(teamStatisticEntity);// save teamStatistice entity
    }
    private Boolean checkTeamName(TournamentSeasonEntity tournamentSeasonEntity, TeamDTO teamDTO){
        List<TeamStatisticEntity> teamStatistics=tournamentSeasonEntity.getTeamStatisticEntities();
        if(teamStatistics==null) return false;
        List<String> teamNames= teamStatistics.stream()
                .map(s->s.getTeamStatisticPk().getTeamEntity().getName()).toList();
        if(teamNames.contains(teamDTO.getName()))
            return true;
        return false;
    }

    @Override
    public void registerPlayers(List<PlayerDTO> playerDTOS) {
        RuleEntity ruleEntity= ruleRepository.findAll().get(0);
        if(playerDTOS.size()>ruleEntity.getMaxNumOfPlayers()||playerDTOS.size()<ruleEntity.getMinNumOfPlayers())
            throw new InvalidPlayerListSizeException("Lỗi vì số lượng cầu thủ phải từ "+ ruleEntity.getMinNumOfPlayers().toString()+
                    " đến "+ruleEntity.getMaxNumOfPlayers().toString()+".",playerDTOS);
        if(!checkMinMaxAges(playerDTOS))
            throw  new InvalidPlayerAgeException("Lỗi vì tồn tại cầu thủ không ở trong độ tuổi từ  "+ruleEntity.getMinNumOfPlayers().toString()+" đến "+
                    ruleEntity.getMaxNumOfPlayers().toString()+" ages.",playerDTOS);
        if(!checkMaxNumOfForeigners(playerDTOS))
            throw new InvalidForeignPlayerException("Lỗi vì số lượng cầu thủ nước ngoài nhiều hơn "+ ruleEntity.getMaxNumOfForeigners()+ ".",playerDTOS);

        List<PlayerEntity> playerEntities= new ArrayList<>();
        for(PlayerDTO playerDTO: playerDTOS){
            PlayerEntity playerEntity= modelMapper.map(playerDTO, PlayerEntity.class);
            playerEntities.add(playerEntity);
        }
        playerEntities=playerRepository.saveAll(playerEntities);

        TeamEntity teamEntity=teamRepository.findById(playerDTOS.get(0).getTeamId()).get();
        TournamentSeasonEntity tournamentSeasonEntity = tournamentSeasonRepository.findByTournamentSeasonPk_TournamentEntity_IdAndTournamentSeasonPk_SeasonEntity_Id(playerDTOS.get(0).getTournamentId(),playerDTOS.get(0).getSeasonId()).get();

        for(PlayerEntity playerEntity:playerEntities){
            PlayerStatisticPk playerStatisticPk=new PlayerStatisticPk();
            playerStatisticPk.setPlayerEntity(playerEntity);
            playerStatisticPk.setTournamentSeasonEntity(tournamentSeasonEntity);
            playerStatisticPk.setTeamEntity(teamEntity);
            PlayerStatisticEntity playerStatisticEntity=new PlayerStatisticEntity();
            playerStatisticEntity.setPlayerStatisticPk(playerStatisticPk);
            playerStatisticEntity.setGoalScore(0L);
            playerStatisticRepository.save(playerStatisticEntity);
        }
    }
    private Boolean checkMinMaxAges(List<PlayerDTO> players){
        //Check following the newest rule
        RuleEntity ruleEntity= ruleRepository.findAllByOrderByTimeCreatedDesc().get(0);
        //Check the min,max age of each player.
        for(int i=0; i<players.size();i++){
            LocalDate currentDate= LocalDate.now();
            LocalDate dateOfBirth= players.get(i).getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(dateOfBirth,currentDate).getYears();
            if(age>ruleEntity.getMaxAge().intValue()||age<ruleEntity.getMinAge().intValue()) return false;
        }
        return true;
    }
    private Boolean checkMaxNumOfForeigners(List<PlayerDTO> players){
        //Check following the newest rule
        RuleEntity ruleEntity= ruleRepository.findAllByOrderByTimeCreatedDesc().get(0);
        //Check the number of foreigners
        int cnt=0;
        for(int i=0; i<players.size();i++){
            if(players.get(i).getType().equals("FOREIGN")) cnt++;
        }
        return cnt<=ruleEntity.getMaxNumOfForeigners().intValue();
    }
}
