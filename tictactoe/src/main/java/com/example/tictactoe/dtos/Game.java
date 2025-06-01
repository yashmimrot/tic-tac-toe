package com.example.tictactoe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Game {

  private Board board;
  private List<Player> playerList;
}
