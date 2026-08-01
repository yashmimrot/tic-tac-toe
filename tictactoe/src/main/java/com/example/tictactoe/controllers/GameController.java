package com.example.tictactoe.controllers;

import com.example.tictactoe.dtos.CreateGameRequest;
import com.example.tictactoe.dtos.Game;
import com.example.tictactoe.dtos.GameResponse;
import com.example.tictactoe.dtos.MakeMoveRequest;
import com.example.tictactoe.dtos.Piece;
import com.example.tictactoe.dtos.Player;
import com.example.tictactoe.enums.GameStatus;
import com.example.tictactoe.services.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @PostMapping
  public ResponseEntity<GameResponse> createGame(@RequestBody CreateGameRequest request) {
    Game game = gameService.createGame(request.getPlayerNames(), request.getBoardSize());
    return ResponseEntity.ok(toResponse(game));
  }

  @PostMapping("/{gameId}/moves")
  public ResponseEntity<GameResponse> makeMove(
      @PathVariable String gameId,
      @RequestBody MakeMoveRequest request) {
    Game game = gameService.makeMove(gameId, request.getRow(), request.getCol());
    return ResponseEntity.ok(toResponse(game));
  }

  @GetMapping("/{gameId}")
  public ResponseEntity<GameResponse> getGame(@PathVariable String gameId) {
    Game game = gameService.getGame(gameId);
    return ResponseEntity.ok(toResponse(game));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<String> handleConflict(IllegalStateException e) {
    return ResponseEntity.status(409).body(e.getMessage());
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
    return ResponseEntity.status(404).body(e.getMessage());
  }

  private GameResponse toResponse(Game game) {
    int n = game.getBoard().getSize();
    Piece[][] boardArray = game.getBoard().getBoard();
    String[][] board = new String[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        board[i][j] = boardArray[i][j] != null ? boardArray[i][j].getPieceType().name() : null;
      }
    }

    List<Player> players = game.getPlayerList();
    List<GameResponse.PlayerInfo> playerInfos = players.stream()
        .map(p -> GameResponse.PlayerInfo.builder()
            .name(p.getName())
            .piece(p.getAssignedPieceType().name())
            .build())
        .collect(Collectors.toList());

    GameResponse.PlayerInfo currentPlayerInfo = null;
    if (game.getStatus() == GameStatus.IN_PROGRESS) {
      Player current = players.get(game.getCurrentPlayerIndex());
      currentPlayerInfo = GameResponse.PlayerInfo.builder()
          .name(current.getName())
          .piece(current.getAssignedPieceType().name())
          .build();
    }

    GameResponse.PlayerInfo winnerInfo = null;
    if (game.getWinner() != null) {
      winnerInfo = GameResponse.PlayerInfo.builder()
          .name(game.getWinner().getName())
          .piece(game.getWinner().getAssignedPieceType().name())
          .build();
    }

    return GameResponse.builder()
        .gameId(game.getGameId())
        .board(board)
        .currentPlayer(currentPlayerInfo)
        .status(game.getStatus())
        .winner(winnerInfo)
        .players(playerInfos)
        .build();
  }
}