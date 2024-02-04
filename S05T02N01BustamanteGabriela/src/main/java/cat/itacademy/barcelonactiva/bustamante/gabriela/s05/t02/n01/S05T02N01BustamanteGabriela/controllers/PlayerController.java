package cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.controllers;

import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.exceptions.DuplicatePlayerNameException;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.exceptions.ErrorResponse;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.User;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.services.AuthService;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private AuthService authService;

    @PostMapping("/players")
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody PlayerDTO playerDTO, Authentication authentication) {
        // Obtener el usuario autenticado
        User userAuth = authService.getUserFromAuthentication(authentication);

        // Validar la existencia del usuario autenticado
        if (userAuth == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Obtener el userId del usuario autenticado
        Long userId = userAuth.getUserId();

        // Llamar a createPlayer con ambos argumentos
        PlayerDTO createdPlayer = playerService.createPlayer(playerDTO.getName(), userId);

        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }
    @PutMapping("/players/{id}")
    public ResponseEntity<PlayerDTO> updatePlayerName(@PathVariable Long id, @RequestBody PlayerDTO updatePlayerDTO) {
        // Validar el cuerpo de la solicitud
        if (updatePlayerDTO == null || updatePlayerDTO.getName() == null || updatePlayerDTO.getName().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Llamar al servicio para actualizar el nombre del jugador
        PlayerDTO updatedPlayer = playerService.updatePlayerName(id, updatePlayerDTO.getName());

        // Devolver la respuesta con el jugador actualizado
        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }
    @GetMapping("/players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayersWithSuccessPercentage() {
        List<PlayerDTO> players = playerService.getAllPlayersWithSuccessPercentage();

        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(players, HttpStatus.OK);
        }
    }
    @GetMapping("/players/ranking")
    public ResponseEntity<Double> getPlayersRanking() {
        double ranking = playerService.getPlayersRanking();

        if (ranking == 0.0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ranking, HttpStatus.OK);
        }
    }
    @GetMapping("/players/ranking/loser")
    public ResponseEntity<PlayerDTO> getLoserPlayer() {
        PlayerDTO loserPlayer = playerService.getLoserPlayer();
        return new ResponseEntity<>(loserPlayer, HttpStatus.OK);
    }
    @GetMapping("/players/ranking/winner")
    public ResponseEntity<PlayerDTO> getWinnerPlayer() {
        PlayerDTO winnerPlayer = playerService.getWinnerPlayer();
        return new ResponseEntity<>(winnerPlayer, HttpStatus.OK);
    }
}
