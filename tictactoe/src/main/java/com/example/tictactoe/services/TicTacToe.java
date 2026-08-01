package com.example.tictactoe.services;

import com.example.tictactoe.dtos.Board;
import com.example.tictactoe.dtos.Game;
import com.example.tictactoe.dtos.Piece;
import com.example.tictactoe.dtos.Player;
import org.springframework.stereotype.Component;

@Component
public class TicTacToe {

  public void playMove(Game game, Player player, int row, int col) {
    Board board = game.getBoard();
    if (row < 0 || row >= board.getSize() || col < 0 || col >= board.getSize()) {
      throw new IllegalArgumentException("Position (" + row + "," + col + ") is out of bounds");
    }
    Piece[][] boardArray = board.getBoard();
    if (boardArray[row][col] != null) {
      throw new IllegalArgumentException("Position (" + row + "," + col + ") is already occupied");
    }
    boardArray[row][col] = new Piece(player.getAssignedPieceType(), player);
  }

  public Piece checkForWin(Game game) {
    Piece[][] b = game.getBoard().getBoard();
    int n = b.length;

    // Check rows
    for (int i = 0; i < n; i++) {
      boolean win = true;
      for (int j = 1; j < n; j++) {
        if (!Piece.compare(b[i][j - 1], b[i][j])) { win = false; break; }
      }
      if (win && b[i][0] != null) return b[i][n - 1];
    }

    // Check columns
    for (int j = 0; j < n; j++) {
      boolean win = true;
      for (int i = 1; i < n; i++) {
        if (!Piece.compare(b[i - 1][j], b[i][j])) { win = false; break; }
      }
      if (win && b[0][j] != null) return b[n - 1][j];
    }

    // Check main diagonal (top-left → bottom-right)
    boolean mainDiag = true;
    for (int i = 1; i < n; i++) {
      if (!Piece.compare(b[i - 1][i - 1], b[i][i])) { mainDiag = false; break; }
    }
    if (mainDiag && b[0][0] != null) return b[n - 1][n - 1];

    // Check anti-diagonal (top-right → bottom-left)
    boolean antiDiag = true;
    for (int i = 1; i < n; i++) {
      if (!Piece.compare(b[i - 1][n - i], b[i][n - 1 - i])) { antiDiag = false; break; }
    }
    if (antiDiag && b[0][n - 1] != null) return b[n - 1][0];

    return null;
  }

  public boolean isBoardFull(Game game) {
    for (Piece[] row : game.getBoard().getBoard()) {
      for (Piece cell : row) {
        if (cell == null) return false;
      }
    }
    return true;
  }
}