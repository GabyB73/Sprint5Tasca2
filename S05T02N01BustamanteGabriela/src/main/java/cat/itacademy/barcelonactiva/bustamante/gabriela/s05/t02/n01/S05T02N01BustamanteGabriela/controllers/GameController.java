package cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.controllers;

import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.exceptions.ErrorResponse;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class GameController {
    @Autowired
    private GameService gameService;
    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameDTO> createGame(@PathVariable Long id){
        GameDTO gameDTO = gameService.createGame(id);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);

    }
    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<ErrorResponse> deleteAllPlayerGames(@PathVariable Long id, WebRequest webRequest) {
        boolean gamesDeleted = gameService.deleteAllGamesByPlayer(id);

        if (gamesDeleted) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(),
                    "Player games deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    "No games found for the player"), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/players/{id}/games")
    public ResponseEntity<List<GameDTO>> getPlayerGames(@PathVariable Long id) {
        List<GameDTO> playerGames = gameService.getAllGamesByPlayer(id);
        return new ResponseEntity<>(playerGames, HttpStatus.OK);
    }
}
