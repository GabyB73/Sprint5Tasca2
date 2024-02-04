package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.services;

import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.User;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.dto.AuthResponseDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.dto.LoginDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.dto.UserRegistrationDTO;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponseDTO register(UserRegistrationDTO userRegistrationDTO);
    AuthResponseDTO authenticate(LoginDTO loginDTO);
    User getUserFromAuthentication(Authentication authentication);
}
