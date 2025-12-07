
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

// Strategy
// Pattern:======================================================================
interface PlayerStrategy {
    int getMove(Board board, char symbol);

    String getPlayerName();
}

class HumanPlayer implements PlayerStrategy {
    private Scanner scanner = new Scanner(System.in);
    private String name;

    public HumanPlayer(String name) {
        this.name = name;
    }

    @Override
    public int getMove(Board board, char symbol) {
        // Reference board aur actual game board side-by-side dikhayenge
        // Taaki user ko dono sath me dikhein

        int move;
        while (true) {
            System.out.print(name + " (" + symbol + "), enter your move (1-9): ");
            // User se 1-9 ke beech number input liya
            move = scanner.nextInt();
            if (move >= 1 && move <= 9) {
                // Valid input hai toh 0-8 index me convert kiya (array 0 se start hota hai)
                return move - 1;
            }
            System.out.println("Invalid input! Please enter a number between 1-9.");
            // Agar galat number diya toh dobara poocho
        }
    }

    @Override
    public String getPlayerName() {
        return name;
    }
}

class AIPlayer implements PlayerStrategy {
    @Override
    public int getMove(Board board, char symbol) {
        // AI ka smart move logic - pehle jeetne ki koshish, phir opponent ko block
        // karo, phir best position lo

        char opponent = (symbol == 'X') ? 'O' : 'X';

        // STEP 1: Agar AI jeet sakta hai toh jeet jao
        // Check if AI can win in the next move
        for (int i = 0; i < 9; i++) {
            if (board.isCellEmpty(i)) {
                board.makeMove(i, symbol, false); // Temporary move bina display ke
                if (board.checkWin(symbol)) {
                    board.undoMove(i); // Undo the test move
                    return i; // Winning move mila!
                }
                board.undoMove(i);
            }
        }

        // STEP 2: Agar opponent jeet sakta hai toh use block karo
        // Check if opponent can win, block that move
        for (int i = 0; i < 9; i++) {
            if (board.isCellEmpty(i)) {
                board.makeMove(i, opponent, false);
                if (board.checkWin(opponent)) {
                    board.undoMove(i);
                    return i; // Block opponent's winning move
                }
                board.undoMove(i);
            }
        }

        // STEP 3: Center (position 4) best hai, agar khali hai toh wo lo
        // Take center if available (best strategic position)
        if (board.isCellEmpty(4)) {
            return 4;
        }

        // STEP 4: Corners ache hote hain (0,2,6,8)
        // Take corners if available
        int[] corners = {  2, 6, 8 , 0 };
        for (int corner : corners) {
            if (board.isCellEmpty(corner)) {
                return corner;
            }
        }

        // STEP 5: Agar sab kuch occupied hai toh koi bhi khali cell lo
        // Take any available cell
        for (int i = 0; i < 9; i++) {
            if (board.isCellEmpty(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String getPlayerName() {
        return "Computer";
    }
}
// ================================================================================================

// Factory
// Pattern:================================================================================

class PlayerFactory {
    public static PlayerStrategy createPlayer(String type, String name) {
        if (type.equalsIgnoreCase("human")) {
            return new HumanPlayer(name);
        } else if (type.equalsIgnoreCase("ai")) {
            return new AIPlayer();
        }
        throw new IllegalArgumentException("Unknown player type");
    }
}
// ================================================================================================

// Observer Pattern:
// ==============================================================================

interface GameObserver {
    void update(Board board);
}

class ConsoleDisplay implements GameObserver {
    @Override
    public void update(Board board) {
        board.printBoard();
    }
}

// Subject: Board
class Board {
    private char[] cells = new char[9];
    private GameObserver observer;

    public Board() {
        // Board ko initialize kiya - sabhi cells me '1' to '9' numbers rakhe
        for (int i = 0; i < 9; i++) {
            // cells[i] = (char) ('1' + i); // '1', '2', '3', ... '9'
            cells[i] = ' ';
        }
    }

    public void setObserver(GameObserver observer) {
        this.observer = observer;
    }

    public boolean isCellEmpty(int index) {
        // Check kiya ki cell me X ya O nahi hai (matlab number hai toh empty hai)
        return cells[index] != 'X' && cells[index] != 'O';
    }

    public boolean makeMove(int index, char symbol) {
        return makeMove(index, symbol, true);
    }

    public boolean makeMove(int index, char symbol, boolean notify) {
        // Cell me move kiya - agar cell khali hai toh symbol (X ya O) rakho
        if (isCellEmpty(index)) {
            cells[index] = symbol;
            if (notify && observer != null) {
                observer.update(this); // Observer ko notify kiya taaki board display ho
            }
            return true;
        }
        return false;
    }

    public void undoMove(int index) {
        // Temporary test move ko undo karne ke liye - wapas number rakh do
        cells[index] = (char) ('1' + index);
    }

    public boolean checkWin(char symbol) {
        // Win conditions check kiye - 3 rows, 3 columns, 2 diagonals
        int[][] winConditions = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // Rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // Columns
                { 0, 4, 8 }, { 2, 4, 6 } // Diagonals
        };
        for (int[] condition : winConditions) {
            if (cells[condition[0]] == symbol && cells[condition[1]] == symbol && cells[condition[2]] == symbol) {
                return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        // Check kiya ki board completely full hai ya nahi
        for (char cell : cells) {
            if (cell != 'X' && cell != 'O') {
                return false; // Agar koi cell me abhi bhi number hai toh board full nahi
            }
        }
        return true;
    }

    public void printBoard() {
        // Dono boards side-by-side print karenge
        // Left side: Reference board (hamesha 1-9 numbers dikhayega for reference)
        // Right side: Game board (completely empty spaces with only X and O moves)

        System.out.println("\n╔════════════════════════╦════════════════════════╗");
        System.out.println("║   REFERENCE BOARD      ║     GAME BOARD         ║");
        System.out.println("║  (Cell Numbers 1-9)    ║  (Your Moves: X, O)    ║");
        System.out.println("╠════════════════════════╬════════════════════════╣");

        // Row 1 - Left side me numbers (1,2,3), Right side me sirf moves (X/O) ya khali
        // space
        System.out.println(
                "║       1 | 2 | 3        ║       " + getCellDisplay(0) + " | " + getCellDisplay(1) + " | "
                        + getCellDisplay(2) + "        ║");
        System.out.println("║      ---+---+---       ║      ---+---+---       ║");

        // Row 2 - Left side me numbers (4,5,6), Right side me sirf moves ya khali
        System.out.println(
                "║       4 | 5 | 6        ║       " + getCellDisplay(3) + " | " + getCellDisplay(4) + " | "
                        + getCellDisplay(5) + "        ║");
        System.out.println("║      ---+---+---       ║      ---+---+---       ║");

        // Row 3 - Left side me numbers (7,8,9), Right side me sirf moves ya khali
        System.out.println(
                "║       7 | 8 | 9        ║       " + getCellDisplay(6) + " | " + getCellDisplay(7) + " | "
                        + getCellDisplay(8) + "        ║");

        System.out.println("╚════════════════════════╩════════════════════════╝");
        System.out.println();
    }

    private char getCellDisplay(int index) {
        // Game board me sirf X, O ya empty space dikhayega
        // Reference numbers nahi dikhenge right side me
        if (cells[index] == 'X' || cells[index] == 'O') {
            return cells[index]; // Agar move ho gayi hai toh X ya O dikhao
        } else {
            return ' '; // Agar empty hai toh khali space dikhao (number nahi)
        }
    }
}

// ================================================================================================
class Game {
    private Board board;
    private PlayerStrategy player1;
    private PlayerStrategy player2;
    private char currentSymbol;
    private Scanner scanner = new Scanner(System.in);

    public Game() {
        board = new Board();
        board.setObserver(new ConsoleDisplay());
        currentSymbol = 'X';
    }

    public void start() {
        System.out.println("====================================");
        System.out.println("  Welcome to Tic Tac Toe Game!");
        System.out.println("====================================");

        // User se poocho ki wo kisse khelna chahta hai
        System.out.println("\nChoose game mode:");
        System.out.println("1. Play against Computer (AI)");
        System.out.println("2. Play with another Human player");
        System.out.print("Enter your choice (1 or 2): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("\nEnter name for Player 1 (X): ");
        String player1Name = scanner.nextLine();

        if (choice == 1) {
            // Player vs Computer mode
            player1 = PlayerFactory.createPlayer("human", player1Name);
            player2 = PlayerFactory.createPlayer("ai", "Computer");
            System.out.println("\n" + player1Name + " (X) vs Computer (O)");
        } else {
            // Player vs Player mode
            System.out.print("Enter name for Player 2 (O): ");
            String player2Name = scanner.nextLine();
            player1 = PlayerFactory.createPlayer("human", player1Name);
            player2 = PlayerFactory.createPlayer("human", player2Name);
            System.out.println("\n" + player1Name + " (X) vs " + player2Name + " (O)");
        }

        System.out.println("\nGame Instructions:");
        System.out.println("- Enter numbers 1-9 to place your mark");
        System.out.println("- Numbers represent positions on the board");
        System.out.println("- First player is X, second player is O");
        System.out.println("\nLet's start!\n");

        board.printBoard();

        while (true) {
            // Current player ko select kiya based on symbol
            PlayerStrategy currentPlayer = (currentSymbol == 'X') ? player1 : player2;

            System.out.println("-----------------------------------");
            System.out.println(currentPlayer.getPlayerName() + "'s turn (" + currentSymbol + ")");

            int move = currentPlayer.getMove(board, currentSymbol);

            if (board.makeMove(move, currentSymbol)) {
                // Valid move tha, ab check karo win/draw

                if (board.checkWin(currentSymbol)) {
                    System.out.println(
                            "\n🎉🎉🎉 " + currentPlayer.getPlayerName() + " (" + currentSymbol + ") WINS! 🎉🎉🎉");
                    break;
                } else if (board.isFull()) {
                    System.out.println("\n🤝 It's a DRAW! Well played both! 🤝");
                    break;
                }

                // Switch player - X se O, O se X
                currentSymbol = (currentSymbol == 'X') ? 'O' : 'X';
            } else {
                System.out.println("❌ Invalid move! That cell is already occupied. Try again.");
            }
        }

        System.out.println("\n====================================");
        System.out.println("       Game Over! Thanks for playing!");
        System.out.println("====================================");
    }
}
