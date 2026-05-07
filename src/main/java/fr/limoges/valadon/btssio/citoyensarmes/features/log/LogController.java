package fr.limoges.valadon.btssio.citoyensarmes.features.log;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@Tag(name = "Logs", description = "Gestion des logs de connexion")
public class LogController {

    private final LogService logService;

    @Operation(summary = "Obtenir les logs par utilisateur", description = "Récupère les logs de connexion d'un utilisateur spécifique")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<List<ConnectionLogDTO>> getLogsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(logService.getLogsByUser(userId));
    }

    @Operation(summary = "Obtenir les logs récents", description = "Récupère tous les logs de connexion des 7 derniers jours")
    @GetMapping("/recent")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<List<ConnectionLogDTO>> getLogsFromLast7Days() {
        return ResponseEntity.ok(logService.getLogsFromLast7Days());
    }
}
