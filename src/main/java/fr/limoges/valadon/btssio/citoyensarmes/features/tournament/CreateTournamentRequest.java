package fr.limoges.valadon.btssio.citoyensarmes.features.tournament;

import lombok.Data;

@Data
public class CreateTournamentRequest {
    private String name;
    private String game;
    private Integer numberOfTeams;
}
