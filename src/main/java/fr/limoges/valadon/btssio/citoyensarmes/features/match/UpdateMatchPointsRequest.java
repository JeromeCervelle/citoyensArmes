package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import lombok.Data;

@Data
public class UpdateMatchPointsRequest {
    private String teamId;
    private int score;
}
