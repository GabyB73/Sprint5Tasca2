package cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.services;

import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.User;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.AuthResponseDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.LoginDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.UserRegistrationDTO;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponseDTO register(UserRegistrationDTO userRegistrationDTO);
    AuthResponseDTO authenticate(LoginDTO loginDTO);
    User getUserFromAuthentication(Authentication authentication);
}
