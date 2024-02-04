package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.controllers;

import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.Role;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.User;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.dto.AuthResponseDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.dto.LoginDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.dto.UserRegistrationDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.services.AuthService;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO){

        // Crear el usuario con el nombre de usuario y contraseña
        var user = new User(userRegistrationDTO.getUsername(),
                passwordEncoder.encode(userRegistrationDTO.getPassword()));

        // Asignar roles al usuario
        Set<Role> roles = new HashSet<>();
        roles.add(Role.PLAYER);
        user.setRoles(roles);

        // Guardar el usuario en el repositorio
        userRepository.save(user);

        // Generar el token y devolver la respuesta
        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthResponseDTO.builder().token(jwtToken).build());

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody LoginDTO loginDTO ){
        return ResponseEntity.ok(authService.authenticate(loginDTO));
    }
}
