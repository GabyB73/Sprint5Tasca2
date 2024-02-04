package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.services;

import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.Player;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.User;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {
    // Método para crear un nuevo jugador
    PlayerDTO createPlayer(String playerName, String userId);
    // Método para actualizar el nombre de un jugador
    PlayerDTO updatePlayerName(String playerId, String newName);
    // Método para obtener información de un jugador por ID
    PlayerDTO getPlayerById(String playerId);
    // Método para obtener información de todos los jugadores
    List<PlayerDTO> getAllPlayers(User user);
    List<PlayerDTO> getAllPlayersWithSuccessPercentage();
    // Método para eliminar a un jugador por ID
    void deletePlayerById(String playerId);
    //Métodos de conversión
    PlayerDTO convertToDTO(Player player);
    Player convertToEntity(PlayerDTO playerDTO);
    double getPlayersRanking();
    PlayerDTO getLoserPlayer();
    PlayerDTO getWinnerPlayer();
}
