package fr.limoges.valadon.btssio.citoyensarmes.features.user;

import lombok.Data;

@Data
public class UpdateUserStatusRequest {
    private UserStatus status;
}
