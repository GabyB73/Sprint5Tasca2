package cat.itacademy.barcelonactiva.bustamante.gabriela.s05.t02.n01.S05T02N01BustamanteGabriela.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    //Relación con el jugador
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "Dice1_result")
    private  int dice1Result;
    @Column(name = "Dice2_result")
    private int dice2Result;
    @Column(name = "is_winner")
    private boolean isWinner;
    @Column(name = "game_date")
    private LocalDateTime gameDate;

    //Método para determinar si la tirada es ganadora
    public boolean calculateWinner(){

        return (dice1Result + dice2Result) == 7;
    }
    // Método setter para el atributo relacionado
    public void setPlayer(Player player) {
        this.player = player;

    }
}
