import java.util.*;

public class C4 {
	static int N = Node.N;
	static int EMPTY = 0;
	static int HUMAN = 1;
	static int COMPUTER = 2;
	static Scanner sc = new Scanner(System.in);
	static int winner = 0;
	static boolean finished = false;

	public static void main(String[] args) {

		// initialize the board
		int board[][] = new int[N][N]; // create the variable

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = EMPTY;// 초기보드 만들기 set 0's
			}
		}

		printBoard(board);

		while (true) {

			turnOfPlayer(board);
			printBoard(board);
			if (isFinished(board))
				break;

			turnOfComputer(board);
			printBoard(board);
			if (isFinished(board))
				break;
		}
		System.out.println("The Game is Finished");
		if (winner == 1)
			System.out.println("Winner: Human");
		else if (winner == 2)
			System.out.println("Winner: Computer");

	}

	// TODO: A function that detects that the game ends:
	// -Obligation: detect board is full
	// -Optional: easy: detect connct4 vertically and horizontally
	// difficult: detect diagonals

	static boolean isFinished(int board[][]) {
		if (isBoardFull(board) == true || detectVConnect(board) == true || detectHConnect(board) == true)
			finished = true;
		return finished;
	}

	static boolean isBoardFull(int board[][]) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] != 0)
					continue;
				else {
					return false;
				}
			}
		}
		return true;
	}

	static void printBoard(int board[][]) {

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == EMPTY)
					System.out.print("|   ");
				else if (board[i][j] == HUMAN)
					System.out.print("| X ");
				else if (board[i][j] == COMPUTER)
					System.out.print("| O ");
			}
			System.out.print("|");
			System.out.println();
		}
	}

	static boolean detectVConnect(int board[][]) {
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (board[i][j] != 0)
					if (i + 3 < N) {
						if (board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j]
								&& board[i][j] == board[i + 3][j]) {
							winner = board[i][j];
							return true;
						}
					}
			}

		return false;
	}

	static boolean detectHConnect(int board[][]) {
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (board[i][j] != 0)
					if (j + 3 < N) {
						if (board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2]
								&& board[i][j] == board[i][j + 3]) {
							winner = board[i][j];
							return true;
						}
					}
			}
		return false;
	}

	static void turnOfPlayer(int board[][]) {

		System.out.println();
		System.out.print("Enter a column from 1 to 8 : ");

		int column = sc.nextInt();
		int row = getRowGravity(column, board);

		board[row][column - 1] = HUMAN;
	}

	static void turnOfComputer(int board[][]) {

		System.out.println();
		System.out.print("Enter a column from 1 to 8 : ");

		int column = sc.nextInt();
		int row = getRowGravity(column, board);

		board[row][column - 1] = COMPUTER;
	}

	static int getRowGravity(int column, int board[][]) {
		int i = N - 1;
		for (i = N - 1; i >= 0; i--) {
			if (board[i][column - 1] == EMPTY)
				break;
		}

		return i;
	}
}