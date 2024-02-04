package cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.services;

import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.Game;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.PlayerDTO;

import java.util.List;

public interface GameService {
// Métodos relacionados con el juego
GameDTO createGame(long playerId);
List<GameDTO> getAllGamesByPlayer(long playerId);
public boolean deleteAllGamesByPlayer(long playerId);
GameDTO convertToDTO(Game game);
Game convertToEntity(GameDTO gameDTO);
}



