package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tournaments/{tournamentId}/matches")
@RequiredArgsConstructor
@Tag(name = "Matchs", description = "Gestion des matchs d'un tournoi")
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    @Operation(summary = "Lister les matchs d'un tournoi", description = "Récupère la liste de tous les matchs prévus dans un tournoi.")
    public ResponseEntity<List<MatchDTO>> listMatchesForTournament(@PathVariable String tournamentId) {
        return ResponseEntity.ok(matchService.listMatchesForTournament(tournamentId));
    }

    @GetMapping("/{matchId}")
    @Operation(summary = "Récupérer un match", description = "Affiche les détails précis d'un match spécifique.")
    public ResponseEntity<MatchDTO> getMatch(
            @PathVariable String tournamentId,
            @PathVariable String matchId
    ) {
        return ResponseEntity.ok(matchService.getMatch(tournamentId, matchId));
    }

    @PatchMapping("/{matchId}/points")
    @Operation(summary = "Mettre à jour les points d'un match", description = "Permet d'enregistrer le score pour une équipe dans un match en cours.")
    public ResponseEntity<Map<String, String>> updateMatchPoints(
            @PathVariable String tournamentId,
            @PathVariable String matchId,
            @RequestBody UpdateMatchPointsRequest request
    ) {
        matchService.updateMatchPoints(tournamentId, matchId, request);
        return ResponseEntity.ok(Map.of("message", "Le score a été mis à jour avec succès."));
    }

    @PostMapping("/{matchId}/teams")
    @Operation(summary = "Inscrire des équipes à un match", description = "Permet d'associer deux équipes à un match existant dans un round.")
    public ResponseEntity<Map<String, String>> registerTeamsToMatch(
            @PathVariable String tournamentId,
            @PathVariable String matchId,
            @RequestBody RegisterTeamsToMatchRequest request
    ) {
        matchService.registerTeamsToMatch(tournamentId, matchId, request);
        return ResponseEntity.ok(Map.of("message", "Les équipes ont été inscrites au match avec succès."));
    }
}
