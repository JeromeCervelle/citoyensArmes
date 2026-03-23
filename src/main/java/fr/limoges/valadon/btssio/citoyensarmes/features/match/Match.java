package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Représente un match entre deux équipes.
 */
@Data
@Document(collection = "matches")
public class Match {
    @Id
    private String id;
    
    // Un match appartient à une manche (Round)
    private String roundId;
    
    private String team1Id;
    private String team2Id;
    private int team1Point;
    private int team2Point;
    private MatchStatus status;
}
