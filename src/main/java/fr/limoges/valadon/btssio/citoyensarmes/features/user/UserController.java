package fr.limoges.valadon.btssio.citoyensarmes.features.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "Gestion des comptes utilisateurs")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Obtenir tous les utilisateurs", description = "Récupère la liste de tous les comptes utilisateurs")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Obtenir l'utilisateur courant", description = "Récupère les informations de l'utilisateur actuellement authentifié")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.mapToDTO(user));
    }

    @Operation(summary = "Obtenir un utilisateur par son ID", description = "Récupère les informations d'un utilisateur spécifique")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Modifier les informations d'un utilisateur", description = "Met à jour les informations (nom, email) d'un utilisateur")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        if (!id.equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @Operation(summary = "Modifier le mot de passe", description = "Met à jour le mot de passe d'un utilisateur")
    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(
            @PathVariable String id,
            @RequestBody UpdatePasswordRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        if (!id.equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }
        userService.updatePassword(id, request);
        return ResponseEntity.ok("Mise à jour effectuée avec succès");
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Mettre à jour le statut d'un utilisateur")
    public ResponseEntity<UserDTO> updateUserStatus(
            @PathVariable String id,
            @RequestBody UpdateUserStatusRequest request) {
        return ResponseEntity.ok(userService.updateUserStatus(id, request));
    }
}
