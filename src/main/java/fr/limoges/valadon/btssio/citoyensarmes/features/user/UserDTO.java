package fr.limoges.valadon.btssio.citoyensarmes.features.user;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private String id;
    private String email;
    private String name;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
