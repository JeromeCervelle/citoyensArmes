package fr.limoges.valadon.btssio.citoyensarmes.features.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {

    private final ConnectionLogRepository connectionLogRepository;

    public List<ConnectionLogDTO> getLogsByUser(String userId) {
        return connectionLogRepository.findByUserId(userId).stream()
                .map(log -> ConnectionLogDTO.builder()
                        .id(log.getId())
                        .userId(log.getUserId())
                        .connectionDateTime(log.getConnectionDateTime())
                        .ipAddress(log.getIpAddress())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ConnectionLogDTO> getLogsFromLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return connectionLogRepository.findByConnectionDateTimeAfter(sevenDaysAgo).stream()
                .map(log -> ConnectionLogDTO.builder()
                        .id(log.getId())
                        .userId(log.getUserId())
                        .connectionDateTime(log.getConnectionDateTime())
                        .ipAddress(log.getIpAddress())
                        .build())
                .collect(Collectors.toList());
    }
}
