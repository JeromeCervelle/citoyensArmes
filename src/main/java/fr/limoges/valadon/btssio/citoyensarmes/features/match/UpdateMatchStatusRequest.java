package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import lombok.Data;

@Data
public class UpdateMatchStatusRequest {
    private MatchStatus status;
}
