package fr.limoges.valadon.btssio.citoyensarmes.features.user;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String newPassword;
}
