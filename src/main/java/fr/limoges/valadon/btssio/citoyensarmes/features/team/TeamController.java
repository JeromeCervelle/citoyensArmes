package fr.limoges.valadon.btssio.citoyensarmes.features.team;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments/{tournamentId}/teams")
@RequiredArgsConstructor
@Tag(name = "Équipes", description = "Gestion des équipes dans un tournoi")
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    @Operation(summary = "Lister les équipes d'un tournoi", description = "Donne la liste des équipes inscrites à un tournoi donné.")
    public ResponseEntity<List<TeamDTO>> listTeamsForTournament(@PathVariable String tournamentId) {
        return ResponseEntity.ok(teamService.listTeamsForTournament(tournamentId));
    }

    @PostMapping
    @Operation(summary = "Créer une équipe dans un tournoi", description = "Permet d'inscrire une nouvelle équipe dans un tournoi.")
    public ResponseEntity<TeamDTO> createTeam(
            @PathVariable String tournamentId,
            @RequestBody CreateTeamRequest request
    ) {
        return ResponseEntity.ok(teamService.createTeam(tournamentId, request));
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "Récupérer une équipe dans un tournoi", description = "Affiche les détails précis d'une équipe dans un tournoi.")
    public ResponseEntity<TeamDTO> getTeam(
            @PathVariable String tournamentId,
            @PathVariable String teamId
    ) {
        return ResponseEntity.ok(teamService.getTeam(tournamentId, teamId));
    }

    @PutMapping("/{teamId}")
    @Operation(summary = "Modifier une équipe dans un tournoi", description = "Permet de modifier les informations d'une équipe dans un tournoi.")
    public ResponseEntity<TeamDTO> updateTeam(
            @PathVariable String tournamentId,
            @PathVariable String teamId,
            @RequestBody UpdateTeamRequest request
    ) {
        return ResponseEntity.ok(teamService.updateTeam(tournamentId, teamId, request));
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "Supprimer une équipe dans un tournoi", description = "Permet de retirer une équipe d'un tournoi.")
    public ResponseEntity<String> deleteTeam(
            @PathVariable String tournamentId,
            @PathVariable String teamId
    ) {
        teamService.deleteTeam(tournamentId, teamId);
        return ResponseEntity.ok("Équipe supprimée avec succès du tournoi.");
    }
}
