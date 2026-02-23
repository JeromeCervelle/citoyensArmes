package fr.limoges.valadon.btssio.citoyensarmes.features.round;

import fr.limoges.valadon.btssio.citoyensarmes.features.match.MatchDTO;
import fr.limoges.valadon.btssio.citoyensarmes.features.match.Match;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments/{tournamentId}/rounds")
@RequiredArgsConstructor
@Tag(name = "Manches", description = "Gestion des manches (rounds) d'un tournoi")
public class RoundController {

    private final RoundService roundService;

    @GetMapping
    @Operation(summary = "Lister les manches d'un tournoi", description = "Récupère la liste de toutes les manches associées à un tournoi donné.")
    public ResponseEntity<List<RoundDTO>> listRoundsForTournament(@PathVariable String tournamentId) {
        return ResponseEntity.ok(roundService.listRoundsForTournament(tournamentId));
    }

    @PostMapping
    @Operation(summary = "Créer une manche dans un tournoi", description = "Ajoute une nouvelle manche (round) à un tournoi existant.")
    public ResponseEntity<RoundDTO> createRound(
            @PathVariable String tournamentId,
            @RequestBody CreateRoundRequest request
    ) {
        return ResponseEntity.ok(roundService.createRound(tournamentId, request));
    }

    @GetMapping("/{roundId}")
    @Operation(summary = "Récupérer une manche d'un tournoi", description = "Récupère les détails d'une manche spécifique au sein d'un tournoi.")
    public ResponseEntity<RoundDTO> getRound(
            @PathVariable String tournamentId,
            @PathVariable String roundId
    ) {
        return ResponseEntity.ok(roundService.getRound(tournamentId, roundId));
    }

    @PostMapping("/{roundId}/matches")
    @Operation(summary = "Créer un match dans une manche", description = "Ajoute un nouveau match vide à une manche existante d'un tournoi.")
    public ResponseEntity<MatchDTO> createMatch(
            @PathVariable String tournamentId,
            @PathVariable String roundId
    ) {
        Match match = roundService.createMatchInRound(tournamentId, roundId);
        MatchDTO matchDTO = MatchDTO.builder()
                .id(match.getId())
                .team1Id(match.getTeam1Id())
                .team2Id(match.getTeam2Id())
                .team1Point(match.getTeam1Point())
                .team2Point(match.getTeam2Point())
                .build();
        return ResponseEntity.ok(matchDTO);
    }
}
