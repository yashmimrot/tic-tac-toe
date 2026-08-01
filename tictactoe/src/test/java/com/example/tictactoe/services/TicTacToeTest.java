package com.example.tictactoe.services;

import com.example.tictactoe.dtos.Board;
import com.example.tictactoe.dtos.Game;
import com.example.tictactoe.dtos.Piece;
import com.example.tictactoe.dtos.Player;
import com.example.tictactoe.enums.GameStatus;
import com.example.tictactoe.enums.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {

  private TicTacToe ticTacToe;
  private Player playerX;
  private Player playerO;

  @BeforeEach
  void setUp() {
    ticTacToe = new TicTacToe();
    playerX = Player.builder().name("Alice").assignedPieceType(PieceType.X).build();
    playerO = Player.builder().name("Bob").assignedPieceType(PieceType.O).build();
  }

  private Game newGame(int size) {
    return Game.builder()
        .gameId("test")
        .board(new Board(size, new Piece[size][size]))
        .playerList(Arrays.asList(playerX, playerO))
        .currentPlayerIndex(0)
        .status(GameStatus.IN_PROGRESS)
        .build();
  }

  @Test
  void detectsRowWin() {
    Game game = newGame(3);
    ticTacToe.playMove(game, playerX, 0, 0);
    ticTacToe.playMove(game, playerX, 0, 1);
    ticTacToe.playMove(game, playerX, 0, 2);
    assertNotNull(ticTacToe.checkForWin(game));
    assertEquals(PieceType.X, ticTacToe.checkForWin(game).getPieceType());
  }

  @Test
  void detectsColumnWin() {
    Game game = newGame(3);
    ticTacToe.playMove(game, playerO, 0, 1);
    ticTacToe.playMove(game, playerO, 1, 1);
    ticTacToe.playMove(game, playerO, 2, 1);
    assertNotNull(ticTacToe.checkForWin(game));
    assertEquals(PieceType.O, ticTacToe.checkForWin(game).getPieceType());
  }

  @Test
  void detectsMainDiagonalWin() {
    Game game = newGame(3);
    ticTacToe.playMove(game, playerX, 0, 0);
    ticTacToe.playMove(game, playerX, 1, 1);
    ticTacToe.playMove(game, playerX, 2, 2);
    assertNotNull(ticTacToe.checkForWin(game));
  }

  @Test
  void detectsAntiDiagonalWin() {
    Game game = newGame(3);
    ticTacToe.playMove(game, playerX, 0, 2);
    ticTacToe.playMove(game, playerX, 1, 1);
    ticTacToe.playMove(game, playerX, 2, 0);
    assertNotNull(ticTacToe.checkForWin(game), "Anti-diagonal win should be detected");
  }

  @Test
  void noWinOnEmptyBoard() {
    Game game = newGame(3);
    assertNull(ticTacToe.checkForWin(game));
  }

  @Test
  void detectsFullBoard() {
    Game game = newGame(3);
    // X O X / O X O / O X O  — draw board
    ticTacToe.playMove(game, playerX, 0, 0);
    ticTacToe.playMove(game, playerO, 0, 1);
    ticTacToe.playMove(game, playerX, 0, 2);
    ticTacToe.playMove(game, playerO, 1, 0);
    ticTacToe.playMove(game, playerX, 1, 1);
    ticTacToe.playMove(game, playerO, 1, 2);
    ticTacToe.playMove(game, playerO, 2, 0);
    ticTacToe.playMove(game, playerX, 2, 1);
    ticTacToe.playMove(game, playerO, 2, 2);
    assertTrue(ticTacToe.isBoardFull(game));
    assertNull(ticTacToe.checkForWin(game));
  }

  @Test
  void rejectsOutOfBoundsMove() {
    Game game = newGame(3);
    assertThrows(IllegalArgumentException.class, () -> ticTacToe.playMove(game, playerX, 3, 0));
    assertThrows(IllegalArgumentException.class, () -> ticTacToe.playMove(game, playerX, -1, 0));
  }

  @Test
  void rejectsOccupiedCell() {
    Game game = newGame(3);
    ticTacToe.playMove(game, playerX, 1, 1);
    assertThrows(IllegalArgumentException.class, () -> ticTacToe.playMove(game, playerO, 1, 1));
  }
}