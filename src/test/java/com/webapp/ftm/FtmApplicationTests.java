package com.webapp.ftm;

import com.webapp.ftm.enums.GoalType;
import com.webapp.ftm.model.TeamEntity;
import com.webapp.ftm.repository.GoalRepository;
import com.webapp.ftm.repository.MatchesRepository;
import com.webapp.ftm.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class FtmApplicationTests {
	private final TeamRepository teamRepository;
	private final MatchesRepository matchesRepository;
	private final GoalRepository goalRepository;
	@Autowired
    FtmApplicationTests(TeamRepository teamRepository, MatchesRepository matchesRepository, GoalRepository goalRepository) {
        this.teamRepository = teamRepository;
        this.matchesRepository = matchesRepository;
        this.goalRepository = goalRepository;
    }
	@Test
	public void testTeamRepository() {
		List<TeamEntity> result = teamRepository.findAllByTournamentAndSeason(UUID.randomUUID(),UUID.randomUUID());
		System.out.println(result);
	}
	@Test
	public void Test() {
		goalRepository.saveGoal(UUID.fromString("b9b316a7-7fe6-499a-b21a-8181c8307733"), UUID.fromString("3baaa7a0-0425-48a5-b470-490a13fbc6a7"), UUID.fromString("69c1c486-a665-444f-b192-14f1113479cd"), GoalType.GOAL.getValue(),5l);
		System.out.println(GoalType.GOAL.getValue());
	}
}
