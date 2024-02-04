package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.services;

import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.Game;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.dto.GameDTO;

import java.util.List;

public interface GameService {
    // Métodos relacionados con el juego
    GameDTO createGame(String playerId);
    List<GameDTO> getAllGamesByPlayer(String playerId);
    //void deleteAllGamesByPlayer(long playerId);
    public boolean deleteAllGamesByPlayer(String playerId);
    GameDTO convertToDTO(Game game);
    Game convertToEntity(GameDTO gameDTO);
}
