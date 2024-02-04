package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.services;

import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.Game;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.Player;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService{
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Override
    public GameDTO createGame(String playerId) {
        //Verificar si el jugador con el ID proporcionado existe
        Player player = playerRepository.findById(String.valueOf(playerId))
                .orElseThrow(() -> new PlayerNotFoundException("Player by Id is not found: " + playerId));

        //Lanzamiento de dados
        int dice1Value = rollDice();
        int dice2Value = rollDice();

        //Crear una nueva entidad de juego y asociarla al jugador
        Game game = new Game();
        game.setPlayer(player);
        game.setDice1Result(dice1Value);
        game.setDice2Result(dice2Value);
        game.setWinner(game.calculateWinner());
        game.setGameDate(LocalDateTime.now());

        //Agregar la tirada a la lista de juegos del jugador
        player.addGame(game);

        //Guardar la tirada en la DB
        game = gameRepository.save(game);

        //Convertir la entidad a DTO y devolverla
        return convertToDTO(game);
    }
    //Método para simular la tirada de dados
    private int rollDice() {
        Random random = new Random();
        return random.nextInt(6) + 1;//6 es la cantidad de caras en un dado
    }
    //Obtener todas las tiradas de un jugador
    @Override
    public List<GameDTO> getAllGamesByPlayer(String playerId) {
        Player player = playerRepository.findById(String.valueOf(playerId))
                .orElseThrow(()-> new PlayerNotFoundException("Player is not found by ID " + playerId));
        List<Game> playerGames = gameRepository.findByPlayer_PlayerId(String.valueOf(playerId));

        return playerGames.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public boolean deleteAllGamesByPlayer(String playerId) {
        int deletedGames = gameRepository.deleteByPlayer_PlayerId(String.valueOf(playerId));
        return deletedGames > 0;
    }
    //Métodos de conversión
    @Override
    public GameDTO convertToDTO(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGameId(game.getGameId());
        gameDTO.setDice1Result(game.getDice1Result());
        gameDTO.setDice2Result(game.getDice2Result());
        gameDTO.setWinner(game.isWinner());
        gameDTO.setGameDate(game.getGameDate());
        return gameDTO;
    }

    @Override
    public Game convertToEntity(GameDTO gameDTO) {
        Game game = new Game();
        game.setGameId(gameDTO.getGameId());
        game.setDice1Result(gameDTO.getDice1Result());
        game.setDice2Result(gameDTO.getDice2Result());
        game.setWinner(gameDTO.isWinner());
        game.setGameDate(gameDTO.getGameDate());
        return game;
    }
}
