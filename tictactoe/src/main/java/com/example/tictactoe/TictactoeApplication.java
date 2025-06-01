package com.example.tictactoe;

import com.example.tictactoe.dtos.Board;
import com.example.tictactoe.dtos.Game;
import com.example.tictactoe.dtos.Piece;
import com.example.tictactoe.dtos.Player;
import com.example.tictactoe.enums.PieceType;
import com.example.tictactoe.services.TicTacToe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

public class TictactoeApplication {

	public static void main(String[] args) {

		System.out.println("Hello");

		Board board = new Board(3, new Piece[3][3]);

		Player yash = Player.builder().name("YASH").assignedPieceType(PieceType.X).build();
		Player kirti = Player.builder().name("KIRTI").assignedPieceType(PieceType.O).build();

		Game game = new Game(board, Arrays.asList(yash, kirti));
		TicTacToe ticTacToe = new TicTacToe();
		ticTacToe.startGame(game);


	}

}
