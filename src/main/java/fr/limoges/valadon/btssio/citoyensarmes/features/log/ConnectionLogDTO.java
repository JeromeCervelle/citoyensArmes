package fr.limoges.valadon.btssio.citoyensarmes.features.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionLogDTO {
    private String id;
    private String userId;
    private LocalDateTime connectionDateTime;
    private String ipAddress;
}
