package com.example.tictactoe.dtos;

import lombok.Data;

@Data
public class MakeMoveRequest {

  private int row;
  private int col;
}