package fr.limoges.valadon.btssio.citoyensarmes.features.team;

import fr.limoges.valadon.btssio.citoyensarmes.features.match.Match;
import fr.limoges.valadon.btssio.citoyensarmes.features.match.MatchRepository;
import fr.limoges.valadon.btssio.citoyensarmes.features.tournament.Tournament;
import fr.limoges.valadon.btssio.citoyensarmes.features.tournament.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final MatchRepository matchRepository;

    public List<TeamDTO> listTeamsForTournament(String tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé avec l'id : " + tournamentId));

        if (tournament.getTeamIds() == null || tournament.getTeamIds().isEmpty()) {
            return new ArrayList<>();
        }

        return teamRepository.findAllById(tournament.getTeamIds()).stream()
                .map(team -> mapToDTO(team, tournamentId))
                .collect(Collectors.toList());
    }

    public TeamDTO createTeam(String tournamentId, CreateTeamRequest request) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé avec l'id : " + tournamentId));

        Team team = new Team();
        team.setName(request.getName());
        team.setImageUrl(request.getImageUrl());
        team.setPoints(0);

        List<String> tournamentIds = new ArrayList<>();
        tournamentIds.add(tournamentId);
        team.setTournamentIds(tournamentIds);

        Team savedTeam = teamRepository.save(team);

        if (tournament.getTeamIds() == null) {
            tournament.setTeamIds(new ArrayList<>());
        }
        tournament.getTeamIds().add(savedTeam.getId());
        tournamentRepository.save(tournament);

        return mapToDTO(savedTeam, tournamentId);
    }

    public TeamDTO getTeam(String tournamentId, String teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé avec l'id : " + tournamentId));

        if (tournament.getTeamIds() == null || !tournament.getTeamIds().contains(teamId)) {
            throw new RuntimeException("L'équipe avec l'id " + teamId + " n'appartient pas au tournoi " + tournamentId);
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée avec l'id : " + teamId));

        return mapToDTO(team, tournamentId);
    }

    public TeamDTO updateTeam(String tournamentId, String teamId, UpdateTeamRequest request) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé avec l'id : " + tournamentId));

        if (tournament.getTeamIds() == null || !tournament.getTeamIds().contains(teamId)) {
            throw new RuntimeException("L'équipe avec l'id " + teamId + " n'appartient pas au tournoi " + tournamentId);
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée avec l'id : " + teamId));

        if (request.getName() != null) {
            team.setName(request.getName());
        }
        if (request.getImageUrl() != null) {
            team.setImageUrl(request.getImageUrl());
        }

        return mapToDTO(teamRepository.save(team), tournamentId);
    }

    public void deleteTeam(String tournamentId, String teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé avec l'id : " + tournamentId));

        if (tournament.getTeamIds() != null && tournament.getTeamIds().contains(teamId)) {
            tournament.getTeamIds().remove(teamId);
            tournamentRepository.save(tournament);
        }

        Team team = teamRepository.findById(teamId).orElse(null);
        if (team != null) {
            if (team.getTournamentIds() != null) {
                team.getTournamentIds().remove(tournamentId);
                if (team.getTournamentIds().isEmpty()) {
                    teamRepository.delete(team);
                } else {
                    teamRepository.save(team);
                }
            } else {
                teamRepository.delete(team);
            }
        }
    }

    private TeamDTO mapToDTO(Team team, String tournamentId) {
        int calculatedPoints = calculatePointsForTournament(team.getId(), tournamentId);
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .points(calculatedPoints)
                .imageUrl(team.getImageUrl())
                .build();
    }

    private int calculatePointsForTournament(String teamId, String tournamentId) {
        // Points calculés dynamiquement sur la base des matchs joués dans le tournoi
        List<Match> matches = matchRepository.findAll().stream()
                .filter(m -> (teamId.equals(m.getTeam1Id()) || teamId.equals(m.getTeam2Id())))
                .collect(Collectors.toList());

        // On peut affiner en filtrant par tournamentId si Match avait un tournamentId,
        // mais Match est lié à Round qui est lié à Tournament.
        // Comme MatchService n'a pas RoundRepository ici, on pourrait l'ajouter
        // ou filtrer par rapport aux rounds du tournoi.
        // Simplification ici : on compte tous les points du match où l'équipe
        // participe.

        int totalPoints = 0;
        for (Match match : matches) {
            if (teamId.equals(match.getTeam1Id())) {
                totalPoints += match.getTeam1Point();
            } else if (teamId.equals(match.getTeam2Id())) {
                totalPoints += match.getTeam2Point();
            }
        }
        return totalPoints;
    }
}
