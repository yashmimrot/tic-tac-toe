package com.example.tictactoe.services;

import com.example.tictactoe.dtos.Board;
import com.example.tictactoe.dtos.Game;
import com.example.tictactoe.dtos.Piece;
import com.example.tictactoe.dtos.Player;

import java.util.Scanner;

public class TicTacToe {

  public void startGame(Game game) {

    Player currentPLayer = game.getPlayerList().get(0);
    int c = 0;
    while (checkForWin(game) == null) {
      c++;
      try {
        game.getBoard().printBoard();
        System.out.println(currentPLayer.getName() + " It's your chance, enter the coordinates");
        Scanner scanner = new Scanner(System.in);
        String x = scanner.nextLine();
        String y = scanner.nextLine();
        playMove(game, currentPLayer, Integer.parseInt(x), Integer.parseInt(y));
      } catch (Exception e) {
        c--;
      }
      currentPLayer = game.getPlayerList().get(c % game.getPlayerList().size());
    }
    Piece winner = checkForWin(game);
    System.out.println(winner.getPlayer().getName() + " Wins");
  }

  public void playMove(Game game, Player player, int m, int n) {
    Board board = game.getBoard();
    if (m < 0 || m >= board.getSize() || n >= board.getSize() || n < 0) {
      System.out.println("Not allowed");
      throw new RuntimeException("Not allowed");
    }
    Piece[][] boardArray = board.getBoard();
    if (boardArray[m][n] != null) {
      System.out.println("Not allowed");
      throw new RuntimeException("Not allowed");
    }
    boardArray[m][n] = new Piece(player.getAssignedPieceType(), player);

  }

  private Piece checkForWin(Game game) {
    Piece[][] boardArray = game.getBoard().getBoard();

    for (int i = 0; i < boardArray.length; i++) {
      for (int j = 1; j < boardArray.length; j++) {
        if (!Piece.compare(boardArray[i][j - 1], boardArray[i][j])) {
          break;
        }
        if (j == boardArray.length - 1) {
          return boardArray[i][j];
        }
      }
    }

    for (int i = 0; i < boardArray.length; i++) {
      for (int j = 1; j < boardArray.length; j++) {
        if (!Piece.compare(boardArray[j - 1][i], boardArray[j][i])) {
          break;
        }
        if (j == boardArray.length - 1) {
          return boardArray[j][i];
        }
      }
    }

    for (int i = 1; i < boardArray.length; i++) {
      if (!Piece.compare(boardArray[i - 1][i - 1], boardArray[i][i])) {
        break;
      }
      if (i == boardArray.length - 1) {
        return boardArray[i][i];
      }
    }

    for (int i = 1; i < boardArray.length; i++) {
      if (!Piece.compare(boardArray[i - 1][i - 1], boardArray[i][i])) {
        break;
      }
      if (i == boardArray.length - 1) {
        return boardArray[i][i];
      }
    }

    for (int i = boardArray.length - 1; i > 0; i--) {
      if (!Piece.compare(boardArray[i - 1][i - 1], boardArray[i][i])) {
        break;
      }
      if (i == 1) {
        return boardArray[i][i];
      }
    }

    return null;
  }


}
