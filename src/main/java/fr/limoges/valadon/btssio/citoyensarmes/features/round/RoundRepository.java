package fr.limoges.valadon.btssio.citoyensarmes.features.round;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends MongoRepository<Round, String> {
    List<Round> findByTournamentId(String tournamentId);
}
