package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {
    private String id;
    private String team1Id;
    private String team2Id;
    private int team1Point;
    private int team2Point;
    private MatchStatus status;
}
