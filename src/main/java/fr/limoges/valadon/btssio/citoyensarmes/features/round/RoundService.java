package fr.limoges.valadon.btssio.citoyensarmes.features.round;

import fr.limoges.valadon.btssio.citoyensarmes.features.match.Match;
import fr.limoges.valadon.btssio.citoyensarmes.features.match.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;
    private final MatchRepository matchRepository;
    private final fr.limoges.valadon.btssio.citoyensarmes.features.tournament.TournamentRepository tournamentRepository;

    public List<RoundDTO> listRoundsForTournament(String tournamentId) {
        return roundRepository.findByTournamentId(tournamentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public RoundDTO createRound(String tournamentId, CreateRoundRequest request) {
        if (!tournamentRepository.existsById(tournamentId)) {
            throw new RuntimeException("Tournoi non trouvé avec l'id : " + tournamentId);
        }

        Round round = new Round();
        round.setName(request.getName());
        round.setFormat(request.getFormat());
        round.setTournamentId(tournamentId);
        round.setMatchIds(new ArrayList<>());

        Round savedRound = roundRepository.save(round);

        // Mettre à jour le tournoi avec l'ID de la nouvelle manche
        fr.limoges.valadon.btssio.citoyensarmes.features.tournament.Tournament tournament = tournamentRepository.findById(tournamentId).get();
        if (tournament.getRoundIds() == null) {
            tournament.setRoundIds(new ArrayList<>());
        }
        tournament.getRoundIds().add(savedRound.getId());
        tournamentRepository.save(tournament);

        return mapToDTO(savedRound);
    }

    public RoundDTO getRound(String tournamentId, String roundId) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new RuntimeException("Manche non trouvée avec l'id : " + roundId));
        
        if (!round.getTournamentId().equals(tournamentId)) {
            throw new RuntimeException("Cette manche n'appartient pas au tournoi spécifié.");
        }
        
        return mapToDTO(round);
    }

    public Match createMatchInRound(String tournamentId, String roundId) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new RuntimeException("Manche non trouvée avec l'id : " + roundId));

        if (!round.getTournamentId().equals(tournamentId)) {
            throw new RuntimeException("Cette manche n'appartient pas au tournoi spécifié.");
        }

        Match match = new Match();
        match.setRoundId(roundId);
        match.setTeam1Point(0);
        match.setTeam2Point(0);
        
        Match savedMatch = matchRepository.save(match);

        if (round.getMatchIds() == null) {
            round.setMatchIds(new ArrayList<>());
        }
        round.getMatchIds().add(savedMatch.getId());
        roundRepository.save(round);

        return savedMatch;
    }

    private RoundDTO mapToDTO(Round round) {
        return RoundDTO.builder()
                .id(round.getId())
                .name(round.getName())
                .format(round.getFormat())
                .matchIds(round.getMatchIds())
                .build();
    }
}
