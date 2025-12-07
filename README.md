# 🎮 Tic Tac Toe Game

A feature-rich **Tic Tac Toe** game built in Java with multiple game modes, smart AI opponent, and clean console interface.

## ✨ Features

### 🎯 Game Modes

- **Player vs Computer (AI)**: Challenge an intelligent AI opponent
- **Player vs Player**: Play with a friend on the same computer

### 🤖 Smart AI

The AI uses strategic decision-making:

1. **Win Priority**: Takes winning move if available
2. **Block Strategy**: Blocks opponent's winning moves
3. **Center Control**: Prefers center position for strategic advantage
4. **Corner Strategy**: Takes corners when center is occupied
5. **Tactical Play**: Makes optimal moves to maximize winning chances

### 🎨 User-Friendly Interface

- **Dual Board Display**:
  - Left side shows reference numbers (1-9) for easy cell selection
  - Right side shows the actual game state with X and O moves
- **Clear Visual Layout**: Box-drawing characters for professional appearance
- **Input Validation**: Prevents invalid moves and provides helpful feedback
- **Player Customization**: Custom names for both players

## 📋 How to Play

### Running the Game

```bash
javac TicTacToe.java
java TicTacToe
```

### Game Instructions

1. Choose your game mode:
   - Press `1` for Player vs Computer
   - Press `2` for Player vs Player
2. Enter player name(s)
3. Enter numbers **1-9** to place your mark on the board
4. First player uses **X**, second player uses **O**
5. Get three in a row (horizontal, vertical, or diagonal) to win!

### Board Layout

```
    REFERENCE BOARD           GAME BOARD
   (Cell Numbers 1-9)      (Your Moves: X, O)

       1 | 2 | 3               X |   | O
      ---+---+---             ---+---+---
       4 | 5 | 6                 | X |
      ---+---+---             ---+---+---
       7 | 8 | 9               O |   | X
```

## 🏗️ Design Patterns Used

This project demonstrates professional software design using **Gang of Four** design patterns:

### 1. **Strategy Pattern**

- Defines player behavior (`HumanPlayer`, `AIPlayer`)
- Allows easy addition of new player types without modifying existing code

### 2. **Factory Pattern**

- `PlayerFactory` creates player instances based on type
- Encapsulates object creation logic

### 3. **Observer Pattern**

- `Board` notifies `ConsoleDisplay` when state changes
- Automatic UI updates when moves are made

## 🛠️ Technical Details

- **Language**: Java
- **Design**: Object-Oriented Programming with SOLID principles
- **Architecture**: Modular design with clear separation of concerns
- **AI Algorithm**: Minimax-inspired strategy with priority-based decision making

## 📁 Project Structure

```
TicTacToe.java
├── PlayerStrategy (Interface)
│   ├── HumanPlayer
│   └── AIPlayer
├── PlayerFactory
├── GameObserver (Interface)
│   └── ConsoleDisplay
├── Board
└── Game
```

## 🎓 Learning Outcomes

This project demonstrates:

- ✅ Design Patterns implementation
- ✅ Clean code principles
- ✅ User input handling and validation
- ✅ Game logic and win condition checking
- ✅ AI decision-making algorithms
- ✅ Object-oriented design

## 🚀 Future Enhancements

Potential improvements:

- [ ] Difficulty levels for AI (Easy, Medium, Hard)
- [ ] Score tracking across multiple games
- [ ] Undo/Redo functionality
- [ ] Save/Load game state
- [ ] GUI version with JavaFX or Swing
- [ ] Multiplayer over network

## 👨‍💻 Author

**Sourabh Singh**

- GitHub: [@Sourabh12345singh](https://github.com/Sourabh12345singh)

## 📝 License

This project is open source and available for educational purposes.

---

### 🎯 Try it out!

Clone this repository and challenge the AI or play with a friend. Can you beat the computer? 🤔

```bash
git clone https://github.com/Sourabh12345singh/tictactoe.git
cd tictactoe
javac TicTacToe.java
java TicTacToe
```

**Enjoy the game! 🎮**
