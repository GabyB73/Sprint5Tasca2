package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.repository;

import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, String> {
    //Para obtener todas las tiradas de un jugador
    List<Game> findByPlayer_PlayerId(String playerId);

    //Para eliminar todas las tiradas de un jugador
    int deleteByPlayer_PlayerId(String playerId);
}
