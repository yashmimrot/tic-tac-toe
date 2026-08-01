package com.example.tictactoe.services;

import com.example.tictactoe.dtos.Game;
import com.example.tictactoe.enums.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

  private GameService gameService;

  @BeforeEach
  void setUp() {
    gameService = new GameService(new TicTacToe());
  }

  private Game startFreshGame() {
    return gameService.createGame(Arrays.asList("Alice", "Bob"), 3);
  }

  @Test
  void createGameReturnsInProgress() {
    Game game = startFreshGame();
    assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    assertNotNull(game.getGameId());
    assertEquals(2, game.getPlayerList().size());
  }

  @Test
  void createGameRejectsWrongPlayerCount() {
    assertThrows(IllegalArgumentException.class,
        () -> gameService.createGame(List.of("OnlyOne"), 3));
  }

  @Test
  void createGameRejectsInvalidBoardSize() {
    assertThrows(IllegalArgumentException.class,
        () -> gameService.createGame(Arrays.asList("Alice", "Bob"), 2));
    assertThrows(IllegalArgumentException.class,
        () -> gameService.createGame(Arrays.asList("Alice", "Bob"), 11));
  }

  @Test
  void alternatesPlayers() {
    Game game = startFreshGame();
    String firstPlayer = game.getPlayerList().get(0).getName();
    String secondPlayer = game.getPlayerList().get(1).getName();

    assertEquals(firstPlayer, game.getPlayerList().get(game.getCurrentPlayerIndex()).getName());
    gameService.makeMove(game.getGameId(), 0, 0);
    assertEquals(secondPlayer, game.getPlayerList().get(game.getCurrentPlayerIndex()).getName());
    gameService.makeMove(game.getGameId(), 1, 1);
    assertEquals(firstPlayer, game.getPlayerList().get(game.getCurrentPlayerIndex()).getName());
  }

  @Test
  void detectsWin() {
    Game game = startFreshGame();
    String id = game.getGameId();
    // Alice wins top row
    gameService.makeMove(id, 0, 0); // Alice X
    gameService.makeMove(id, 1, 0); // Bob O
    gameService.makeMove(id, 0, 1); // Alice X
    gameService.makeMove(id, 1, 1); // Bob O
    gameService.makeMove(id, 0, 2); // Alice X — wins

    assertEquals(GameStatus.WON, game.getStatus());
    assertEquals("Alice", game.getWinner().getName());
  }

  @Test
  void detectsDraw() {
    Game game = startFreshGame();
    String id = game.getGameId();
    // Final board: X X O / O O X / X X O — verified no winner
    gameService.makeMove(id, 0, 0); // X
    gameService.makeMove(id, 0, 2); // O
    gameService.makeMove(id, 0, 1); // X
    gameService.makeMove(id, 1, 0); // O
    gameService.makeMove(id, 1, 2); // X
    gameService.makeMove(id, 1, 1); // O
    gameService.makeMove(id, 2, 0); // X
    gameService.makeMove(id, 2, 2); // O
    gameService.makeMove(id, 2, 1); // X

    assertEquals(GameStatus.DRAW, game.getStatus());
    assertNull(game.getWinner());
  }

  @Test
  void rejectsMoveOnFinishedGame() {
    Game game = startFreshGame();
    String id = game.getGameId();
    gameService.makeMove(id, 0, 0);
    gameService.makeMove(id, 1, 0);
    gameService.makeMove(id, 0, 1);
    gameService.makeMove(id, 1, 1);
    gameService.makeMove(id, 0, 2); // Alice wins

    assertThrows(IllegalStateException.class, () -> gameService.makeMove(id, 2, 2));
  }

  @Test
  void rejectsInvalidGameId() {
    assertThrows(NoSuchElementException.class, () -> gameService.getGame("nonexistent"));
  }
}