package fr.limoges.valadon.btssio.citoyensarmes.features.tournament;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends MongoRepository<Tournament, String> {
}
