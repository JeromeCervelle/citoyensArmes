package fr.limoges.valadon.btssio.citoyensarmes.features.round;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Représente une manche d'un tournoi.
 */
@Data
@Document(collection = "rounds")
public class Round {
    @Id
    private String id;
    private String name;
    
    private String tournamentId;
    
    // Une manche inclut un ou plusieurs matchs
    private List<String> matchIds;
}
