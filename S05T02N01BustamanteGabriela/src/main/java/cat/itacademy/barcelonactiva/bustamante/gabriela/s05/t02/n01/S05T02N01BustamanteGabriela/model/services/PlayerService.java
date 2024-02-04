package cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.services;

import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.Game;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.Player;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.User;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {
    // Método para crear un nuevo jugador
    PlayerDTO createPlayer(String playerName, Long userId);

    // Método para actualizar el nombre de un jugador
    PlayerDTO updatePlayerName(long playerId, String newName);

    // Método para obtener información de un jugador por ID
    PlayerDTO getPlayerById(long playerId);

    // Método para obtener información de todos los jugadores
    List<PlayerDTO> getAllPlayers(User user);
    List<PlayerDTO> getAllPlayersWithSuccessPercentage();

    // Método para eliminar a un jugador por ID
    void deletePlayerById(long playerId);
    //Métodos de conversión
    PlayerDTO convertToDTO(Player player);
    Player convertToEntity(PlayerDTO playerDTO);

    double getPlayersRanking();

    PlayerDTO getLoserPlayer();

    PlayerDTO getWinnerPlayer();
}
