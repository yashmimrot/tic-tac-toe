package com.example.tictactoe.services;

import com.example.tictactoe.dtos.Board;
import com.example.tictactoe.dtos.Game;
import com.example.tictactoe.dtos.Piece;
import com.example.tictactoe.dtos.Player;
import com.example.tictactoe.enums.GameStatus;
import com.example.tictactoe.enums.PieceType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

  private final ConcurrentHashMap<String, Game> games = new ConcurrentHashMap<>();
  private final TicTacToe ticTacToe;

  public GameService(TicTacToe ticTacToe) {
    this.ticTacToe = ticTacToe;
  }

  public Game createGame(List<String> playerNames, int boardSize) {
    if (playerNames == null || playerNames.size() != 2) {
      throw new IllegalArgumentException("Exactly 2 player names are required");
    }
    for (String name : playerNames) {
      if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Player names must not be blank");
      }
    }
    if (boardSize < 3 || boardSize > 10) {
      throw new IllegalArgumentException("Board size must be between 3 and 10");
    }

    PieceType[] pieces = {PieceType.X, PieceType.O};
    List<Player> players = new ArrayList<>();
    for (int i = 0; i < playerNames.size(); i++) {
      players.add(Player.builder()
          .name(playerNames.get(i))
          .assignedPieceType(pieces[i])
          .build());
    }

    String gameId = UUID.randomUUID().toString();
    Game game = Game.builder()
        .gameId(gameId)
        .board(new Board(boardSize, new Piece[boardSize][boardSize]))
        .playerList(players)
        .currentPlayerIndex(0)
        .status(GameStatus.IN_PROGRESS)
        .build();

    games.put(gameId, game);
    return game;
  }

  public Game makeMove(String gameId, int row, int col) {
    Game game = getGame(gameId);

    if (game.getStatus() != GameStatus.IN_PROGRESS) {
      throw new IllegalStateException("Game is already over");
    }

    Player currentPlayer = game.getPlayerList().get(game.getCurrentPlayerIndex());
    ticTacToe.playMove(game, currentPlayer, row, col);

    Piece winningPiece = ticTacToe.checkForWin(game);
    if (winningPiece != null) {
      game.setStatus(GameStatus.WON);
      game.setWinner(winningPiece.getPlayer());
    } else if (ticTacToe.isBoardFull(game)) {
      game.setStatus(GameStatus.DRAW);
    } else {
      game.setCurrentPlayerIndex(
          (game.getCurrentPlayerIndex() + 1) % game.getPlayerList().size()
      );
    }

    return game;
  }

  public Game getGame(String gameId) {
    Game game = games.get(gameId);
    if (game == null) {
      throw new NoSuchElementException("Game not found: " + gameId);
    }
    return game;
  }
}