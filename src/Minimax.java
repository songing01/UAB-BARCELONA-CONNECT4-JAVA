class Node {
	static int N = 8;
	int board[][] = new int[N][N];
	// char board[][]= new char [N][N];
	Node children[]; // 다음 자식노드에 대한 포인터
	double value;

	Node(int board1[][]) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = board1[i][j];
			}
		}
	}

	int calculateNumOfChildren() {
		int numOfFullCol = 0;
		for (int i = 0; i < N; i++)
			if (board[N - 1][i] != 0)
				numOfFullCol++;

		return N - numOfFullCol - 1; // TODO: implement the function
	}

	// I consider that the Node has already created the array of children
	// So this function will create the 'n' children (Nodes)
	void createALevel(int level) {
		for (int i = 0; i < children.length; i++) {
			children[i] = createChild(i, level);
		}
	}

	// Current is the parent one.
	Node createChild(int childIndex, int level) {
		Node p = new Node(board); // we also copy the board of parent
		// throw the piece
		int column = p.transformChildIndex2Column(childIndex); // TODO:
		p.applyThrow(column);// TODO: places the token on the board
		int numOfChildrenOfp = p.calculateNumOfChildren();
		p.value = level * 10 + childIndex;
		if (level < 2)
			p.children = new Node[numOfChildrenOfp];
		else
			p.children = null;
		return p;
	}

	// For instance, in case you only have columns 3 and 5, free
	// You will have only 2 children from the node: childIndex =0 and childIndex=1
	// This function converts a numOfChild to a columns
	int transformChildIndex2Column(int childIndex) {
		int CounterOfFreeCols = 0;
		for (int i = 0; i < N; i++) {
			if (board[N - 1][i] == 0) {
				if (CounterOfFreeCols == childIndex)
					return i;
				else {
					CounterOfFreeCols++;
				}
			}
		}
		return 0;
	}

	// Given a column, sets the token into the appropriate cell of the board

	void applyThrow(int column) {
		int i = N - 1;
		for (i = N - 1; i >= 0; i--) {
			if (board[i][column] == 0)
				break;
		}
		board[i][column] = 2;
	}

}

public class Minimax {
	// The function to be called from C4
	static int PCDecisionColumn(int board[][]) {
		Node root = createTree(board);
		minimaxRecursive(root, 0);
		// call minimaxRecursive to score the nodes
		// detect which node of the level1 has the max value
		// return the column associated to that node

		return 0;
	}

	public static void main(String[] args) {
		int N = C4.N;
		int board[][] = new int[N][N]; // create the variable

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = 0;// 초기보드 만들기 set 0's
			}
		}
		Node root = createTree(board);
		// displayTree(root);
		// displayTreeRecursive(root, 1);
		displayTreeRecursive2(root, 1);

	}

	static void printNode(Node p, int level) {
		for (int k = 1; k <= level; k++)
			System.out.print("  ");
		System.out.println(p.value);

	}

	// TODO: complete the 2nd part of this function set values to all the nodes from
	// the bottom to the top
	static void minimaxRecursive(Node node, int level) {

		if (node.children != null) {
			for (int i = 0; i < node.children.length; i++) {
				minimaxRecursive(node.children[i], level + 1);
			}
		}
		if (node.children == null) {
			node.value = heuristicFunction(node.board);
			// Returns a high value in case the board situation is favourable to PC
			// Returns a low value in case the board situation is favourable to HUMAN
			// 1: Just return a random value
			// 2. Return 1000 in case the PC wins
			// 3. Return -1000 in case HUMAN wins
			// 4. Return a random value (0~10) otherwise
		} else {
			if (level % 2 == 1)
				node.value = minValueOfChildren(node);// odd level >> computer's turn
			if (level % 2 == 0) // even level
				node.value = maxValueOfChildren(node);
		}
	}

	static double maxValueOfChildren(Node node) {
		double max = node.children[0].value;
		for (int i = 0; i < node.children.length; i++) {
			if (node.children[i].value > max)
				max = node.children[i].value;
		}
		return max;
	}

	static double minValueOfChildren(Node node) {
		double min = node.children[0].value;
		for (int i = 0; i < node.children.length; i++) {
			if (node.children[i].value < min)
				min = node.children[i].value;
		}
		return min;
	}

	static int heuristicFunction(int board[][]) {
		int result = 0;
		int i, j;

		// check horizontals
		for (i = 0; i < C4.N; i++)
			for (j = 0; j < C4.N - 3; j++) {
				if (board[i][j] != 2 && board[i][j + 1] != 2 && board[i][j + 2] != 2 && board[i][j + 3] != 2)
					result--;
				if (board[i][j] != 1 && board[i][j + 1] != 1 && board[i][j + 2] != 1 && board[i][j + 3] != 1)
					result++;
			}

		// check verticals
		for (i = 0; i < C4.N - 3; i++)
			for (j = 0; j < C4.N; j++) {
				if (board[i][j] != 2 && board[i + 1][j] != 2 && board[i + 2][j] != 2 && board[i + 3][j] != 2)
					result--;
				if (board[i][j] != 1 && board[i + 1][j] != 1 && board[i + 2][j] != 1 && board[i + 3][j] != 1)
					result++;
			}

		// check ascending diagonals
		for (i = 0; i < C4.N; i++)
			for (j = 0; j < C4.N; j++) {
				if (i + 3 < C4.N && j + 3 < C4.N) {
					if (board[i][j] != 2 && board[i + 1][j + 1] != 2 && board[i + 2][j + 2] != 2
							&& board[i + 3][j + 3] != 2) {
						result--;
					}
					if (board[i][j] != 1 && board[i + 1][j + 1] != 1 && board[i + 2][j + 2] != 1
							&& board[i + 3][j + 3] != 1) {
						result++;
					}
				}
			}
		// check descending diagonals
		for (i = 0; i < C4.N; i++)
			for (j = 0; j < C4.N; j++) {
				if (i + 3 < C4.N && j - 3 >= 0) {
					if (board[i][j] != 2 && board[i + 1][j - 1] != 2 && board[i + 2][j - 2] != 2
							&& board[i + 3][j - 3] != 2) {
						result--;
					}
					if (board[i][j] != 1 && board[i + 1][j - 1] != 1 && board[i + 2][j - 2] != 1
							&& board[i + 3][j - 3] != 1) {
						result++;
					}
				}
			}
		// 2. Return 1000 in case the PC wins
		if (C4.detectVConnect(board) || C4.detectHConnect(board) || C4.detectDConnect(board)
				|| C4.detectDConnect2(board)) {
			if (C4.winner == 2)
				result = 1000;
			else if (C4.winner == 1)
				result = -1000;
		}
		// 3. Return -1000 in case HUMAN wins
		return result;
	}

	// At every node, print and visit children
	// Teacher commment: I think it is easier to build this function from
	// the point of view of the Node and not from a Parent.
	static void displayTreeRecursive2(Node node, int level) {
		printNode(node, level);
		if (node.children != null) {
			for (int i = 0; i < node.children.length; i++) {
				displayTreeRecursive2(node.children[i], level + 1);
			}
		}
	}

	// At every parent, print children and visit children
	static void displayTreeRecursive(Node parent, int level) {
		if (parent.children == null)
			return;
		for (int i = 0; i < parent.children.length; i++) {
			printNode(parent.children[i], level);
			displayTreeRecursive(parent.children[i], level + 1);
		}
	}

	/*
	 * static void displayTree(Node root) { for(int i=0;i<root.children.length;i++)
	 * { System.out.println(root.children[i].value); for(int j=0;j<
	 * root.children[i].children.length ;j++) {
	 * System.out.println("  "+root.children[i].children[j].value); } } }
	 */
	static Node createRootNode(int board[][]) {
		Node p = new Node(board);
		return p;

	}

	static Node createTree(int board[][]) {
		Node root = createRootNode(board);
		int rootChildren = root.calculateNumOfChildren();
		root.children = new Node[rootChildren]; // 자식노드로 이루어진 배열

		// We create a 2 level's tree
		root.createALevel(1);
		for (int i = 0; i < root.children.length; i++) {
			root.children[i].createALevel(2);
		}
		return root;

	}

}