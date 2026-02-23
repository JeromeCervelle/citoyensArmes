package fr.limoges.valadon.btssio.citoyensarmes.features.match;

import fr.limoges.valadon.btssio.citoyensarmes.features.round.Round;
import fr.limoges.valadon.btssio.citoyensarmes.features.round.RoundRepository;
import fr.limoges.valadon.btssio.citoyensarmes.features.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;

    public List<MatchDTO> listMatchesForTournament(String tournamentId) {
        List<String> roundIds = roundRepository.findByTournamentId(tournamentId).stream()
                .map(Round::getId)
                .collect(Collectors.toList());
        
        return matchRepository.findByRoundIdIn(roundIds).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public MatchDTO getMatch(String tournamentId, String matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match non trouvé avec l'id : " + matchId));
        
        validateMatchBelongsToTournament(match, tournamentId);
        
        return mapToDTO(match);
    }

    public void updateMatchPoints(String tournamentId, String matchId, UpdateMatchPointsRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match non trouvé avec l'id : " + matchId));
        
        validateMatchBelongsToTournament(match, tournamentId);
        
        if (match.getTeam1Id().equals(request.getTeamId())) {
            match.setTeam1Point(request.getScore());
        } else if (match.getTeam2Id().equals(request.getTeamId())) {
            match.setTeam2Point(request.getScore());
        } else {
            throw new RuntimeException("L'équipe spécifiée ne participe pas à ce match.");
        }
        
        matchRepository.save(match);
    }

    public void registerTeamsToMatch(String tournamentId, String matchId, RegisterTeamsToMatchRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match non trouvé avec l'id : " + matchId));

        validateMatchBelongsToTournament(match, tournamentId);

        if (!teamRepository.existsById(request.getTeam1Id())) {
            throw new RuntimeException("Équipe 1 non trouvée avec l'id : " + request.getTeam1Id());
        }
        if (!teamRepository.existsById(request.getTeam2Id())) {
            throw new RuntimeException("Équipe 2 non trouvée avec l'id : " + request.getTeam2Id());
        }

        match.setTeam1Id(request.getTeam1Id());
        match.setTeam2Id(request.getTeam2Id());
        // Reset points when teams are changed? Usually yes.
        match.setTeam1Point(0);
        match.setTeam2Point(0);

        matchRepository.save(match);
    }

    private void validateMatchBelongsToTournament(Match match, String tournamentId) {
        Round round = roundRepository.findById(match.getRoundId())
                .orElseThrow(() -> new RuntimeException("Manche parente du match non trouvée."));
        
        if (!round.getTournamentId().equals(tournamentId)) {
            throw new RuntimeException("Ce match n'appartient pas au tournoi spécifié.");
        }
    }

    private MatchDTO mapToDTO(Match match) {
        return MatchDTO.builder()
                .id(match.getId())
                .team1Id(match.getTeam1Id())
                .team2Id(match.getTeam2Id())
                .team1Point(match.getTeam1Point())
                .team2Point(match.getTeam2Point())
                .build();
    }
}
