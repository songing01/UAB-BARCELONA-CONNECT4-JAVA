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
		return N; // TODO: implement the function
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

	// This function converts a numOfChild to a columns
	int transformChildIndex2Column(int childIndex) {
		// TODO: now contains dummy code
		return childIndex;
	}

	// Given a column, sets the token into the appropriate cell of the board

	void applyThrow(int column) {
		// TODO:
	}

}

public class Minimax {
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
		displayTreeRecursive(root, 1);

	}

	static void printNode(Node p, int level) {
		for (int k = 1; k <= level; k++)
			System.out.print("  ");
		System.out.println(p.value);

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