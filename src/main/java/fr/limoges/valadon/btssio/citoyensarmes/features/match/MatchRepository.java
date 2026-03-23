package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {
    List<Match> findByRoundIdIn(List<String> roundIds);
    List<Match> findByTeam1IdOrTeam2Id(String team1Id, String team2Id);
    void deleteByRoundIdIn(List<String> roundIds);
}
