package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {
    List<Match> findByRoundIdIn(List<String> roundIds);
    void deleteByRoundIdIn(List<String> roundIds);
}
