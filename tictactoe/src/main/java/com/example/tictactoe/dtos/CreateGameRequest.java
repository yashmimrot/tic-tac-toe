package com.example.tictactoe.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreateGameRequest {

  private List<String> playerNames;
  private int boardSize;
}