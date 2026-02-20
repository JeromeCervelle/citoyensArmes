package fr.limoges.valadon.btssio.citoyensarmes.features.tournament;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Représente un tournoi.
 */
@Data
@Document(collection = "tournaments")
public class Tournament {
    @Id
    private String id;
    private String name;
    private String game;
    private String status;
    
    // Un tournoi est organisé par un utilisateur
    private String organizerId;
    
    // Un tournoi possède des équipes (Team) et des manches (Round)
    private List<String> teamIds;
    private List<String> roundIds;
}
