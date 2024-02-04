package cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.repository;

import cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    //Para obtener todas las tiradas de un jugador
    List<Game> findByPlayer_PlayerId(Long playerId);

    //Para eliminar todas las tiradas de un jugador
    int deleteByPlayer_PlayerId(Long playerId);
}
