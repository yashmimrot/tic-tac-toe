package com.example.tictactoe.dtos;

import com.example.tictactoe.enums.GameStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResponse {

  private String gameId;
  private String[][] board;
  private PlayerInfo currentPlayer;
  private GameStatus status;
  private PlayerInfo winner;
  private List<PlayerInfo> players;

  @Data
  @Builder
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class PlayerInfo {
    private String name;
    private String piece;
  }
}