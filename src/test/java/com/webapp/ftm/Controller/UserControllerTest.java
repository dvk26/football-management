package com.webapp.ftm.service.impl;

import com.webapp.ftm.dto.TournamentDTO;
import com.webapp.ftm.dto.response.TeamStatisticResponseDTO;
import com.webapp.ftm.exception.custom.InvalidTournamentNameException;
import com.webapp.ftm.model.*;
import com.webapp.ftm.model.pk.TournamentSeasonPk;
import com.webapp.ftm.repository.*;
import com.webapp.ftm.utility.mapper.impl.MatchMapper;
import com.webapp.ftm.utility.mapper.impl.TeamStatisticMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentServiceImplTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private TeamStatisticRepository teamStatisticRepository;

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private TeamStatisticMapper teamStatisticMapper;

    @Mock
    private MatchMapper matchMapper;

    @Mock
    private TournamentSeasonRepository tournamentSeasonRepository;

    @Mock
    private MatchesRepository matchesRepository;

    @Mock
    private SeasonRepository seasonRepository;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    private TournamentDTO tournamentDTO;
    private TournamentEntity tournamentEntity;
    private UUID tournamentId;
    private UUID seasonId;
    private List<TeamStatisticEntity> teamStatisticEntities;
    private RuleEntity ruleEntity;
    private TournamentSeasonEntity tournamentSeasonEntity;

    @BeforeEach
    void setUp() {
        tournamentDTO = new TournamentDTO();
        tournamentDTO.setName("Sample Tournament");

        tournamentEntity = new TournamentEntity();
        tournamentEntity.setId(UUID.randomUUID());
        tournamentEntity.setName("Sample Tournament");

        tournamentId = tournamentEntity.getId();
        seasonId = UUID.randomUUID();

        tournamentSeasonEntity = new TournamentSeasonEntity();
        tournamentSeasonEntity.setRule(ruleEntity);
        tournamentSeasonEntity.setTournamentSeasonPk(new TournamentSeasonPk(tournamentEntity, new SeasonEntity()));
    }

    @Test
    void createTournament_shouldCreateTournament() {
        when(tournamentRepository.findByName(tournamentDTO.getName())).thenReturn(Collections.emptyList());
        when(tournamentRepository.save(any(TournamentEntity.class))).thenReturn(tournamentEntity);

        TournamentEntity createdTournament = tournamentService.createTournament(tournamentDTO);

        assertThat(createdTournament).isNotNull();
        assertThat(createdTournament.getName()).isEqualTo(tournamentDTO.getName());

        verify(tournamentRepository).save(any(TournamentEntity.class));
    }

    @Test
    void createTournament_shouldThrowExceptionWhenNameExists() {
        when(tournamentRepository.findByName(tournamentDTO.getName())).thenReturn(List.of(tournamentEntity));

        assertThatThrownBy(() -> tournamentService.createTournament(tournamentDTO))
                .isInstanceOf(InvalidTournamentNameException.class)
                .hasMessageContaining("Tên mùa giải đã tồn tại!");

        verify(tournamentRepository, never()).save(any(TournamentEntity.class));
    }

    @Test
    void findIdByName_shouldReturnTournamentId() {
        when(tournamentRepository.findByName(tournamentEntity.getName())).thenReturn(List.of(tournamentEntity));

        UUID foundId = tournamentService.findIdByName(tournamentEntity.getName());

        assertThat(foundId).isEqualTo(tournamentId);
    }

    @Test
    void ranking_shouldReturnRankedTeams() {
        when(teamStatisticRepository.findAllBy_TournamentId_And_SeasonId(tournamentId, seasonId)).thenReturn(teamStatisticEntities);
        when(tournamentSeasonRepository.findByTournamentSeasonPk_TournamentEntity_IdAndTournamentSeasonPk_SeasonEntity_Id(tournamentId, seasonId))
                .thenReturn(Optional.of(tournamentSeasonEntity));
        when(teamStatisticMapper.listMapper(any())).thenReturn(new ArrayList<>());

        List<TeamStatisticResponseDTO> rankedTeams = tournamentService.ranking(tournamentId, seasonId);

        assertThat(rankedTeams).isNotNull();
        verify(teamStatisticMapper).listMapper(any());
    }

}
