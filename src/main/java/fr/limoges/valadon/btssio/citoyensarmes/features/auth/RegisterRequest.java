package fr.limoges.valadon.btssio.citoyensarmes.features.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
}
