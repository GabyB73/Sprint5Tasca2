package cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    private String name;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    //Relación uno a muchos con la entidad Game
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Game> games;

    // La relación muchos a uno entre Player y User
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    //Método para añadir un juego al jugador
    public void addGame(Game game){
        if(games == null){
            games = new ArrayList<>();
        }
        games.add(game);
        game.setPlayer(this); //relación bidireccional entre player y game
    }

    //Metodo para calcular el porcentaje de éxito del jugador
        public double calculateSuccessPercentage(){
        if(games == null || games.isEmpty()){
            return 0.0;
        }
        double wins = games.stream().filter(Game::calculateWinner).count(); //cuenta la cantidad de juegos ganandos
        return Math.round(( wins / games.size()) * 100.0);
    }
}
