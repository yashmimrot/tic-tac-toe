package com.example.tictactoe.dtos;

import com.example.tictactoe.enums.PieceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Player {
  private String name;
  private PieceType assignedPieceType;
}
