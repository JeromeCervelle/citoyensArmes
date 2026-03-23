package fr.limoges.valadon.btssio.citoyensarmes.features.tournament;

import lombok.Data;

@Data
public class UpdateTournamentStatusRequest {
    private TournamentStatus status;
}
