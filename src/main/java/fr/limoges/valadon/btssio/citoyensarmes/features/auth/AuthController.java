package fr.limoges.valadon.btssio.citoyensarmes.features.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "API pour la gestion de l'authentification")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "S'inscrire", description = "Permet de créer un nouveau compte utilisateur")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Se connecter", description = "Permet de s'authentifier et d'obtenir un jeton d'accès")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Jeton OAuth2", description = "Permet d'obtenir un jeton d'accès via le protocole OAuth2")
    @PostMapping("/oauth2/token")
    public ResponseEntity<AuthResponse> oauth2Token(@RequestBody LoginRequest request) {
        // Selon le contrat d'API: email dans username
        return ResponseEntity.ok(authService.login(request));
    }
}
