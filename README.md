# Tic Tac Toe Game

A console-based implementation of the classic Tic Tac Toe game built with Java and Spring Boot.

## Project Overview

This project implements a command-line Tic Tac Toe game where two players can play against each other. The game follows the standard rules of Tic Tac Toe, where players take turns marking spaces on a 3x3 grid, and the first player to get three of their marks in a row (horizontally, vertically, or diagonally) wins.

## Technology Stack

- **Java 17**: Core programming language
- **Spring Boot 3.5.0**: Application framework
- **Maven**: Dependency management and build tool
- **Lombok**: Reduces boilerplate code through annotations

## Project Structure

```
src/main/java/com/example/tictactoe/
├── TictactoeApplication.java    # Main application entry point
├── dtos/                        # Data Transfer Objects
│   ├── Board.java               # Represents the game board
│   ├── Game.java                # Represents a game session
│   ├── Piece.java               # Represents a piece on the board
│   └── Player.java              # Represents a player
├── enums/
│   └── PieceType.java           # Enum for piece types (X, O)
└── services/
    └── TicTacToe.java           # Contains game logic
```

## Design Patterns and Principles

### Design Patterns

1. **Builder Pattern**
   - Implemented in the `Player` and `Board` classes using Lombok's `@Builder` annotation
   - Allows for flexible object creation with optional parameters
   - Example: `Player.builder().name("YASH").assignedPieceType(PieceType.X).build()`

2. **Data Transfer Objects (DTO) Pattern**
   - Used for transferring data between different layers of the application
   - Classes like `Board`, `Game`, `Piece`, and `Player` serve as DTOs
   - Helps in maintaining a clean separation between data and behavior

3. **Service Layer Pattern**
   - The `TicTacToe` class acts as a service that contains the core game logic
   - Separates business logic from data representation

### Design Principles

1. **Single Responsibility Principle (SRP)**
   - Each class has a well-defined responsibility:
     - `Board`: Manages the game board state and rendering
     - `Player`: Represents player information
     - `Piece`: Represents a game piece with comparison logic
     - `TicTacToe`: Handles game flow and win conditions

2. **Encapsulation**
   - Private fields with getters and setters (via Lombok's `@Data` annotation)
   - Prevents direct access to class fields, ensuring data integrity

3. **Separation of Concerns**
   - Clear separation between:
     - Data representation (DTOs)
     - Game logic (TicTacToe service)
     - Application entry point (TictactoeApplication)

4. **Immutability**
   - Some objects are designed to be immutable after creation
   - Helps in maintaining thread safety and predictable behavior

5. **Code Reusability**
   - Common functionality is extracted into reusable methods
   - The `Piece.compare()` static method provides reusable comparison logic

## How the Game Works

1. The game initializes with a 3x3 board and two players, each assigned either 'X' or 'O'
2. Players take turns entering coordinates to place their pieces on the board
3. After each move, the game checks for a win condition (three in a row)
4. The game continues until a player wins or the board is full (draw)

## How to Run the Game

1. Ensure you have Java 17 and Maven installed
2. Clone the repository
3. Navigate to the project directory
4. Run the following command:

```bash
mvn spring-boot:run
```

5. Follow the on-screen instructions to play the game

## Game Flow

1. The board is displayed as a 3x3 grid
2. Players are prompted to enter coordinates (row and column) to place their piece
3. Invalid moves (out of bounds or already occupied spaces) are rejected
4. The game alternates between players until a win condition is met
5. When a player wins, their name is displayed as the winner

## Future Enhancements

Potential improvements for the project:

1. Add a graphical user interface (GUI)
2. Implement an AI opponent with different difficulty levels
3. Add support for larger board sizes
4. Include game statistics and player profiles
5. Add network play capability for remote opponents

## License

This project is open source and available under the [MIT License](LICENSE).
