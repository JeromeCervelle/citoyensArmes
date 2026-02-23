package fr.limoges.valadon.btssio.citoyensarmes.features.team;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Représente une équipe participant à un tournoi.
 */
@Data
@Document(collection = "teams")
public class Team {
    @Id
    private String id;
    private String name;
    
    // Les points seront recalculés dynamiquement dans le service
    private int points;
    
    // URL vers un stockage externe
    private String imageUrl;
    
    // Une équipe peut jouer dans plusieurs tournois
    private List<String> tournamentIds;
    
    // Une équipe joue dans des matchs
    private List<String> matchIds;
}
