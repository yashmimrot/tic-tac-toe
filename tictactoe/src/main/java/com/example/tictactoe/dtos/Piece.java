package com.example.tictactoe.dtos;

import com.example.tictactoe.enums.PieceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Piece {

  private PieceType pieceType;
  private Player player;

  public static boolean compare(Piece piece1, Piece piece2) {
    if (piece1 == null || piece2 == null) {
      return false;
    }
    return piece1.pieceType.name().equals(piece2.getPieceType().name());
  }


}
