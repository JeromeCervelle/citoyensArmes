package fr.limoges.valadon.btssio.citoyensarmes.features.tournament;

import lombok.Data;

@Data
public class UpdateTournamentRequest {
    private String name;
    private String game;
    private TournamentStatus status;
}
