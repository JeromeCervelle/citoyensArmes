package fr.limoges.valadon.btssio.citoyensarmes.features.round;

import lombok.Data;

@Data
public class CreateRoundRequest {
    private String name;
    private int format;
}
