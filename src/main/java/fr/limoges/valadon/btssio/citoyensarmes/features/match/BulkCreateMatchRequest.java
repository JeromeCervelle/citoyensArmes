package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import lombok.Data;

/**
 * Requête pour créer plusieurs matchs en une seule fois dans un round.
 */
@Data
public class BulkCreateMatchRequest {
    /** Identifiant du round cible */
    private String roundId;
    /** Nombre de matchs à créer */
    private int count;
}
