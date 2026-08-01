package com.example.tictactoe.dtos;

import com.example.tictactoe.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Game {

  private String gameId;
  private Board board;
  private List<Player> playerList;
  private int currentPlayerIndex;
  private GameStatus status;
  private Player winner;
}