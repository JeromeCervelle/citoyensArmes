package fr.limoges.valadon.btssio.citoyensarmes.features.tournament;

import fr.limoges.valadon.btssio.citoyensarmes.features.match.MatchRepository;
import fr.limoges.valadon.btssio.citoyensarmes.features.round.Round;
import fr.limoges.valadon.btssio.citoyensarmes.features.round.RoundRepository;
import fr.limoges.valadon.btssio.citoyensarmes.features.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final RoundRepository roundRepository;
    private final MatchRepository matchRepository;
    private final TeamService teamService;

    public List<TournamentDTO> listTournaments() {
        return tournamentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TournamentDTO createTournament(CreateTournamentRequest request, String organizerId) {
        if (request.getNumberOfTeams() == null || request.getNumberOfTeams() < 2) {
            throw new RuntimeException("Le nombre d'équipes doit être d'au moins 2.");
        }

        Tournament tournament = new Tournament();
        tournament.setName(request.getName());
        tournament.setGame(request.getGame());
        tournament.setStatus(TournamentStatus.DRAFT);
        tournament.setOrganizerId(organizerId);

        return mapToDTO(tournamentRepository.save(tournament));
    }

    public TournamentDTO getTournament(String id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé avec l'id : " + id));
        return mapToDTO(tournament);
    }

    public TournamentDTO updateTournament(String id, UpdateTournamentRequest request) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé avec l'id : " + id));

        if (request.getName() != null) {
            tournament.setName(request.getName());
        }
        if (request.getGame() != null) {
            tournament.setGame(request.getGame());
        }
        if (request.getStatus() != null) {
            tournament.setStatus(request.getStatus());
        }

        return mapToDTO(tournamentRepository.save(tournament));
    }

    public void deleteTournament(String id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé avec l'id : " + id));

        // 1. Supprimer les matchs associés aux rounds du tournoi
        List<Round> rounds = roundRepository.findByTournamentId(id);
        List<String> roundIds = rounds.stream().map(Round::getId).collect(Collectors.toList());
        matchRepository.deleteByRoundIdIn(roundIds);

        // 2. Supprimer les rounds
        roundRepository.deleteAll(rounds);

        // 3. Gérer les équipes (les supprimer si elles n'appartiennent qu'à ce tournoi)
        if (tournament.getTeamIds() != null) {
            for (String teamId : List.copyOf(tournament.getTeamIds())) {
                teamService.deleteTeam(id, teamId);
            }
        }

        // 4. Supprimer le tournoi
        tournamentRepository.delete(tournament);
    }

    private TournamentDTO mapToDTO(Tournament tournament) {
        return TournamentDTO.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .game(tournament.getGame())
                .status(tournament.getStatus())
                .build();
    }
}
