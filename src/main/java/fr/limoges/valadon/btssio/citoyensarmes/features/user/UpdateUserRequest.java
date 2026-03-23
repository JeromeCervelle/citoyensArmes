
package fr.limoges.valadon.btssio.citoyensarmes.features.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String email;
    private String name;
    private UserRole role;
}
