package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "player")
public class Player {
    @Id
    private String playerId;

    private String name;
    private LocalDateTime registrationDate;

    //Para indicar la asociación entre juegos y jugador
    @DBRef
    private List<Game> games;

    @DBRef
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


