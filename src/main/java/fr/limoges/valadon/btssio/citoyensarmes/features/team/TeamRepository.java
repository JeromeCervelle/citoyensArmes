package fr.limoges.valadon.btssio.citoyensarmes.features.team;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
    List<String> findAllByIdIn(List<String> ids);
}
