package fr.limoges.valadon.btssio.citoyensarmes.features.log;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConnectionLogRepository extends MongoRepository<ConnectionLog, String> {
    List<ConnectionLog> findByUserId(String userId);
    List<ConnectionLog> findByConnectionDateTimeAfter(LocalDateTime date);
}
