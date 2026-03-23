package fr.limoges.valadon.btssio.citoyensarmes.features.round;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoundDTO {
    private String id;
    private String name;
    private int format;
    private List<String> matchIds;
}
