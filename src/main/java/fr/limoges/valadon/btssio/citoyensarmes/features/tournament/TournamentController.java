package fr.limoges.valadon.btssio.citoyensarmes.features.tournament;

import fr.limoges.valadon.btssio.citoyensarmes.features.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
@Tag(name = "Tournament", description = "Gestion des tournois")
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    @Operation(summary = "Lister tous les tournois", description = "Récupère la liste complète des tournois disponibles.")
    public ResponseEntity<List<TournamentDTO>> listTournaments() {
        return ResponseEntity.ok(tournamentService.listTournaments());
    }

    @PostMapping
    @Operation(summary = "Créer un tournoi", description = "Crée un nouveau tournoi avec au moins 2 équipes.")
    public ResponseEntity<TournamentDTO> createTournament(
            @RequestBody CreateTournamentRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(tournamentService.createTournament(request, currentUser.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un tournoi", description = "Affiche les détails précis d'un tournoi par son ID.")
    public ResponseEntity<TournamentDTO> getTournament(@PathVariable String id) {
        return ResponseEntity.ok(tournamentService.getTournament(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un tournoi", description = "Met à jour les informations d'un tournoi.")
    public ResponseEntity<TournamentDTO> updateTournament(
            @PathVariable String id,
            @RequestBody UpdateTournamentRequest request
    ) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un tournoi", description = "Supprime définitivement un tournoi du système.")
    public ResponseEntity<Map<String, String>> deleteTournament(@PathVariable String id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.ok(Map.of("message", "Le tournoi a été supprimé avec succès."));
    }
}
