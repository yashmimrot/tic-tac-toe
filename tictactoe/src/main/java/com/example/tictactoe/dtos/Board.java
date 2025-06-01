package com.example.tictactoe.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class Board {
  private int size;
  private Piece[][] board;

  public void printBoard() {
    for(int i=0;i<board.length;i++) {
      for(int j=0;j<board.length;j++) {
        if(board[i][j] == null) {
          System.out.print("_" + " ");
        } else {
          System.out.print(board[i][j].getPieceType().name() + " ");
        }
      }
      System.out.println("");
    }
  }
}
