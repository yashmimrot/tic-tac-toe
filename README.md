# Tic Tac Toe

A Tic Tac Toe game with a REST API backend (Java + Spring Boot) and a browser-based frontend (`index.html`). Supports configurable board sizes and two players.

## Technology Stack

- **Java 17** — core language
- **Spring Boot 3.5.0** — REST API framework
- **Maven** — build tool
- **Lombok** — reduces boilerplate
- **Vanilla HTML/CSS/JS** — frontend (no build step)

## Project Structure

```
tic-tac-toe/
├── index.html                          # Frontend — open in browser to play
└── tictactoe/                          # Spring Boot backend
    └── src/main/java/com/example/tictactoe/
        ├── TictactoeApplication.java
        ├── config/
        │   └── CorsConfig.java         # Allows browser requests from file://
        ├── controllers/
        │   └── GameController.java     # REST endpoints
        ├── dtos/
        │   ├── Board.java
        │   ├── Game.java
        │   ├── GameResponse.java
        │   ├── CreateGameRequest.java
        │   ├── MakeMoveRequest.java
        │   ├── Piece.java
        │   └── Player.java
        ├── enums/
        │   ├── GameStatus.java
        │   └── PieceType.java
        └── services/
            ├── GameService.java
            └── TicTacToe.java          # Core game logic
```

## How to Run

### 1. Start the backend

```bash
cd tictactoe
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### 2. Open the frontend

Just open `index.html` in your browser:

```bash
open index.html
```

Enter two player names and a board size (3–10), then click **Start Game**. Click cells to take turns.

## REST API

Base path: `/api/games`

### Create a game

```
POST /api/games
```

```json
{ "playerNames": ["Alice", "Bob"], "boardSize": 3 }
```

### Make a move

```
POST /api/games/{gameId}/moves
```

```json
{ "row": 0, "col": 0 }
```

Row and column are 0-indexed.

### Get game state

```
GET /api/games/{gameId}
```

### Example response

```json
{
  "gameId": "abc-123",
  "board": [["X", null, null], [null, "O", null], [null, null, null]],
  "currentPlayer": { "name": "Alice", "piece": "X" },
  "status": "IN_PROGRESS",
  "players": [
    { "name": "Alice", "piece": "X" },
    { "name": "Bob",   "piece": "O" }
  ]
}
```

`status` is one of `IN_PROGRESS`, `WON`, or `DRAW`. When the game is won, a `winner` field is included.

## Design Patterns

- **Builder Pattern** — `Player`, `Board`, `Game`, `GameResponse` use Lombok `@Builder` for clean object construction.
- **Service Layer** — `GameService` orchestrates game lifecycle; `TicTacToe` encapsulates win-condition logic.
- **DTO Pattern** — request/response objects are separate from domain models, keeping the API contract stable.

## License

MIT
