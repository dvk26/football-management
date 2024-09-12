package com.webapp.ftm.repository.custom.impl;

import com.webapp.ftm.dto.request.PlayerSearchRequestDTO;
import com.webapp.ftm.model.PlayerEntity;
import com.webapp.ftm.repository.custom.PlayerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;


@Repository
public class PlayerRepositoryCustomImpl implements PlayerRepositoryCustom {
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<PlayerEntity> findAllPlayers(PlayerSearchRequestDTO playerSearchRequestDTO) {
        StringBuilder jpql=new StringBuilder( "Select distinct p from PlayerEntity p ");

        jpql.append(specialQuery(playerSearchRequestDTO));
        jpql.append(basicQuery(playerSearchRequestDTO));
        jpql.append(complexQuery(playerSearchRequestDTO));

        TypedQuery<PlayerEntity> query = em.createQuery( jpql.toString(),PlayerEntity.class);
        if(playerSearchRequestDTO.getTeamId()!= null)
            query.setParameter("team",playerSearchRequestDTO.getTeamId());
        if(playerSearchRequestDTO.getSeasonId()!=null)
            query.setParameter("season",playerSearchRequestDTO.getSeasonId());
        if(playerSearchRequestDTO.getTournamentId()!=null)
            query.setParameter("tournament",playerSearchRequestDTO.getTournamentId());
        if(playerSearchRequestDTO.getGoalScoresFrom()!=null)
            query.setParameter("goalScoreFrom",playerSearchRequestDTO.getGoalScoresFrom());
        if(playerSearchRequestDTO.getGoalScoresTo()!=null)
            query.setParameter("goalScoreTo",playerSearchRequestDTO.getGoalScoresTo());
        if(playerSearchRequestDTO.getYearOfBirth()!=null)
            query.setParameter("yearOfBirth",playerSearchRequestDTO.getYearOfBirth());
        return query.getResultList();
    }

    private String basicQuery(PlayerSearchRequestDTO playerSearchRequestDTO) {
        StringBuilder jpql=new StringBuilder("  ");
        try{
            Field[] fields = PlayerSearchRequestDTO.class.getDeclaredFields();
            System.out.println(fields.length);
            for(Field field: fields) {
                field.setAccessible(true);
                Object value=field.get(playerSearchRequestDTO);
                if(field.getName().endsWith("Id")||field.getName().startsWith("goalScores")||field.getName().equals("yearOfBirth")) continue;
                if(value!=null&&value.toString().length()>0) {
                    jpql.append(" And p."+field.getName()+ " LIKE '%"+value.toString()+"%' ");
                }
            }
            if(playerSearchRequestDTO.getYearOfBirth()!=null)
                jpql.append(" And YEAR(p.dateOfBirth) =: yearOfBirth ") ;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jpql.toString();
    }

    private String specialQuery(PlayerSearchRequestDTO playerSearchRequestDTO) {
        if(playerSearchRequestDTO.getTeamId()==null
                &&playerSearchRequestDTO.getTournamentId()==null
                &&playerSearchRequestDTO.getSeasonId()==null
                &&playerSearchRequestDTO.getGoalScoresFrom()==null
                &&playerSearchRequestDTO.getGoalScoresTo()==null
        ){
            return " where 1 = 1 ";
        }
        StringBuilder jpql =new StringBuilder(" join PlayerStatisticEntity ps on ps.playerStatisticPk.playerEntity.id= p.id  where 1 = 1  ");
        if(playerSearchRequestDTO.getTeamId()!=null){
            jpql.append(" and ps.playerStatisticPk.teamEntity.id =:team ");
        }
        if(playerSearchRequestDTO.getSeasonId()!=null){
            jpql.append(" and ps.seasonEntity.id =:season ");
        }
        if(playerSearchRequestDTO.getTournamentId()!=null){
            jpql.append(" and ps.tournamentEntity.id =:tournament ");
        }
        return jpql.toString();
    }

    private String complexQuery(PlayerSearchRequestDTO playerSearchRequestDTO){
        StringBuilder jpql= new StringBuilder(" ");
        if (playerSearchRequestDTO.getGoalScoresFrom()!=null||playerSearchRequestDTO.getGoalScoresTo()!=null) {
            jpql.append(" group by p.id ");
            jpql.append(" having 1 = 1 ");
            if(playerSearchRequestDTO.getGoalScoresFrom()!=null)
                jpql.append(" and  sum(ps.goalScore) >=: goalScoreFrom " );
            if(playerSearchRequestDTO.getGoalScoresTo()!=null)
                jpql.append(" and  sum(ps.goalScore) <=: goalScoreTo " );
        }

        return jpql.toString();
    }
}
