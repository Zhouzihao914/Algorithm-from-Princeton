import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode currNode;
    private SearchNode twinNode;
    private Stack<Board> solution;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode parent;
        private final int priority;

        public SearchNode(Board InBoard, SearchNode InParent) {
            this.board = InBoard;
            this.parent = InParent;
            if (InParent == null) {
                this.moves = 0;
            } else {
                this.moves = InParent.moves + 1;
            }
            this.priority = this.moves + board.manhattan();
        }

        public int compareTo(SearchNode Node) {
            return Integer.compare(this.priority, Node.priority);
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("The input should not be null!");
        }

        // as mentioned in the specification, one of the currNode and twinNode must be solvable
        currNode = new SearchNode(initial, null);
        twinNode = new SearchNode(initial.twin(), null);
        MinPQ<SearchNode> currPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
        currPQ.insert(currNode);
        twinPQ.insert(twinNode);
        while (true) {

            currNode = currPQ.delMin();
            if (currNode.board.isGoal()) break;
            for (Board neighbor : currNode.board.neighbors()) {
                // as mentioned in the specification, to avoid enqueue a previous SearchNode repeatedly
                if (currNode.parent == null || !neighbor.equals(currNode.parent.board)) {
                    currPQ.insert(new SearchNode(neighbor, currNode));
                }
            }
            twinNode = twinPQ.delMin();
            if (twinNode.board.isGoal()) break;
            for (Board neighbor : twinNode.board.neighbors()) {
                // as mentioned in the specification, to avoid enqueue a previous SearchNode repeatedly
                if (twinNode.parent == null || !neighbor.equals(twinNode.parent.board)) {
                    twinPQ.insert(new SearchNode(neighbor, twinNode));
                }
            }
        }

    }

    public boolean isSolvable() {
        return currNode.board.isGoal();
    }

    public int moves() {
        if (currNode.board.isGoal()) {
            return currNode.moves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        if (currNode.board.isGoal()) {
            solution = new Stack<Board>();
            SearchNode node = currNode;
            while (node != null) {
                solution.push(node.board);
                node = node.parent;
            }
            return solution;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
