package fr.limoges.valadon.btssio.citoyensarmes.features.team;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamDTO {
    private String id;
    private String name;
    private int points;
    private String imageUrl;
}
