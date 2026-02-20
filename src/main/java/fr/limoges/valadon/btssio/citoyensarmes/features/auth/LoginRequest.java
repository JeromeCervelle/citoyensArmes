package fr.limoges.valadon.btssio.citoyensarmes.features.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
