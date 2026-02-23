package fr.limoges.valadon.btssio.citoyensarmes.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return mapToDTO(user);
    }

    public UserDTO updateUser(String id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public void updatePassword(String id, UpdatePasswordRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        if (request.getNewPassword() == null || request.getNewPassword().length() < 8 || request.getNewPassword().length() > 48) {
            throw new RuntimeException("Le mot de passe doit contenir entre 8 et 48 caractères");
        }
        
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setRoles(user.getRoles());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
