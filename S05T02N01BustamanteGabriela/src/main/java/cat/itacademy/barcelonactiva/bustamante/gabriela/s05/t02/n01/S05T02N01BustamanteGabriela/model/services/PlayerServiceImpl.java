package cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.services;

import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.exceptions.DuplicatePlayerNameException;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.Game;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.Player;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.Role;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.User;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Override
    public PlayerDTO createPlayer(String playerName, Long userId) {
        // Validar y asignar un nombre predeterminado si es necesario
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "ANÓNIMO";
        }
        // Verificar si ya existe un jugador con el mismo nombre
        if (!playerName.equals("ANÓNIMO") && playerRepository.existsByNameIgnoreCase(playerName)) {
            throw new DuplicatePlayerNameException("Player name already exists: " + playerName);
        }
        // Obtener el usuario de la base de datos
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Crear una nueva instancia de jugador
        Player player = new Player();
        player.setName(playerName);
        player.setRegistrationDate(LocalDateTime.now());
        player.setUser(user);  // Asignar el usuario al jugador

        // Guardar el jugador en la BD
        player = playerRepository.save(player);

        // Convertir el jugador a un DTO y devolverlo
        return convertToDTO(player);
    }

    @Override
    public PlayerDTO updatePlayerName(long playerId, String newName) {
        // Verificar si el jugador con el ID proporcionado existe
        Player existingPlayer = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player is not found by Id: " + playerId));

        // Actualizar el nombre del jugador
        if (newName != null && !newName.trim().isEmpty()) {
            // Verificar el nuevo nombre en la BD
            if (!existingPlayer.getName().equalsIgnoreCase(newName) &&
                    playerRepository.existsByNameIgnoreCase(newName)) {
                throw new DuplicatePlayerNameException("The new name is repeated");
            }
            // Actualizar el nombre del jugador en la entidad
            existingPlayer.setName(newName);

            // Guardar la actualización en la BD
            existingPlayer = playerRepository.save(existingPlayer);
        } else {
            throw new IllegalArgumentException("New name cannot be null or empty");
        }
        // Convertir la entidad actual a DTO
        PlayerDTO updatedPlayerDTO = convertToDTO(existingPlayer);

        return updatedPlayerDTO;
    }

    @Override
    public PlayerDTO getPlayerById(long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with ID: " + playerId));

        return convertToDTO(player);
    }
    @Override
    public List<PlayerDTO> getAllPlayers(User user) {
        if (user.getRoles().contains(Role.ADMIN)) {
            return getAllPlayersWithSuccessPercentage();
        } else if (user.getRoles().contains(Role.PLAYER)) {
            Player player = findPlayerByUser(user);
            return player != null ? List.of(convertToDTO(player)) : Collections.emptyList();
        } else {
            throw new AccessDeniedException("Unauthorized role for this operation");
        }
    }
    private Player findPlayerByUser(User user) {
        return playerRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new PlayerNotFoundException("Player is not found"
                        + user.getUsername()));
    }
    @Transactional(readOnly = true)
    public List<PlayerDTO> getAllPlayersWithSuccessPercentage() {
        // Obtener todos los jugadores
        List<Player> players = playerRepository.findAll();

        // Convertir las entidades a DTO y calcular el porcentaje de éxito para cada jugador
        return players.stream()
                .map(player -> {
                    PlayerDTO playerDTO = convertToDTO(player);
                    playerDTO.setSuccessPercentage(player.calculateSuccessPercentage());
                    return playerDTO;
                })
                .collect(Collectors.toList());
    }
    // Obtener el ranking promedio de todos los jugadores
    public double getPlayersRanking() {
        // Obtener todos los jugadores
        List<Player> players = playerRepository.findAll();

        // Verificar si hay jugadores
        if (players == null || players.isEmpty()) {
            return 0.0;
        }
        // Calcular el porcentaje promedio de éxitos para todos los jugadores
        return players.stream()
                .mapToDouble(Player::calculateSuccessPercentage)
                .average()
                .orElse(0.0);
    }

    // Obtener el jugador con peor porcentaje de éxito
    public PlayerDTO getLoserPlayer() {
        // Obtener todos los jugadores
        List<Player> players = playerRepository.findAll();

        // Verificar si hay jugadores
        if (players.isEmpty()) {
            throw new PlayerNotFoundException("No players found");
        }
        // Encontrar al jugador con el peor porcentaje de éxito
        Player loserPlayer = players.stream()
                .min(Comparator.comparingDouble(Player::calculateSuccessPercentage))
                .orElseThrow(() -> new PlayerNotFoundException("No players found"));

        // Convertir la entidad a DTO y devolverla
        return convertToDTO(loserPlayer);
    }

    // Obtener el jugador con mejor porcentaje de éxito
    public PlayerDTO getWinnerPlayer() {
        // Obtener todos los jugadores
        List<Player> players = playerRepository.findAll();

        // Verificar si hay jugadores
        if (players.isEmpty()) {
            throw new PlayerNotFoundException("No players found");
        }
        // Encontrar al jugador con el mejor porcentaje de éxito
        Player winnerPlayer = players.stream()
                .max(Comparator.comparingDouble(Player::calculateSuccessPercentage))
                .orElseThrow(() -> new PlayerNotFoundException("No players found"));

        // Convertir la entidad a DTO y devolverla
        return convertToDTO(winnerPlayer);
    }
    @Override
    public void deletePlayerById(long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with ID: " + playerId));

        playerRepository.delete(player);
    }
    //Métodos de conversión
    @Override
    public PlayerDTO convertToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayerId(player.getPlayerId());
        playerDTO.setName(player.getName());
        playerDTO.setRegistrationDate(player.getRegistrationDate());
        playerDTO.setSuccessPercentage(player.calculateSuccessPercentage());

        return playerDTO;
    }
    @Override
    public Player convertToEntity(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setPlayerId(playerDTO.getPlayerId());
        player.setName(playerDTO.getName());
        player.setRegistrationDate(playerDTO.getRegistrationDate());

        return player;
    }
}
