package fr.limoges.valadon.btssio.citoyensarmes.features.tournament;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TournamentDTO {
    private String id;
    private String name;
    private String game;
    private TournamentStatus status;
    private int numberOfTeams;
}
