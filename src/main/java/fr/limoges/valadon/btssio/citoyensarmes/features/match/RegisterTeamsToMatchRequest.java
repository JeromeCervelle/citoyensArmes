package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import lombok.Data;

/**
 * Requête pour inscrire deux équipes à un match.
 */
@Data
public class RegisterTeamsToMatchRequest {
    private String team1Id;
    private String team2Id;
}
