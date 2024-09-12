package com.webapp.ftm.service.impl;

import com.webapp.ftm.dto.SeasonDTO;
import com.webapp.ftm.exception.custom.InvalidSeasonNameException;
import com.webapp.ftm.model.SeasonEntity;
import com.webapp.ftm.repository.SeasonRepository;
import com.webapp.ftm.service.SeasonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SeasonEntity createSeason(SeasonDTO seasonDTO) {
        if(seasonRepository.findByName(seasonDTO.getName()).size()>0)
            throw new InvalidSeasonNameException("Tên mùa giải đã tồn tại!",seasonDTO);
        SeasonEntity seasonEntity= modelMapper.map(seasonDTO,SeasonEntity.class);
        return seasonRepository.save(seasonEntity);
    }

    @Override
    public Map<String, UUID> getSeasons() {
        Map<String,UUID> res=new TreeMap<>();
        List<SeasonEntity> seasonEntities=seasonRepository.findAll();
        for(SeasonEntity seasonEntity:seasonEntities){
            res.put(seasonEntity.getName(),seasonEntity.getId());
        }
        return res;
    }
}
