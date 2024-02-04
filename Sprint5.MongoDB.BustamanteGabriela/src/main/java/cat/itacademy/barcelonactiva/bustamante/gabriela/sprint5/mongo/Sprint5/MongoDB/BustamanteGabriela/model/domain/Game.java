package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "game")
public class Game {

    @Id
    private String gameId;

    @DBRef
    private Player player;

    private int dice1Result;
    private int dice2Result;
    private boolean isWinner;
    private LocalDateTime gameDate;

    // Método para determinar si la tirada es ganadora
    public boolean calculateWinner() {

        return (dice1Result + dice2Result) == 7;
    }
}
