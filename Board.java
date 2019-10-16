import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private char[] blocks;
    private int L;

    public Board(int[][] tiles) {
        //use char[] rather than int[] to save memory
        L = tiles.length;
        int size = L * L;
        blocks = new char[size];
        for (int i = 0; i < size; i++) {
            blocks[i] = (char) tiles[i / L][i % L];
        }
    }

    public String toString() {
        //SPEED: StringBuilder > String,because String represent string constant
        // when concat it needs to create and recycle a object
        StringBuilder output = new StringBuilder();
        output.append(L);
        output.append("\n");
        for (int i = 0; i < L * L; i++) {
            output.append((int) blocks[i]);
            output.append(" ");
            if (i % L == L - 1) {
                output.append("\n");
            }
        }
        return output.toString();
    }

    public int dimension() {
        return L;
    }

    public int hamming() {
        int WrongNum = 0;
        for (int i = 0; i < L * L; i++) {
            if (blocks[i] != (char) (i + 1) && blocks[i] != (char) 0) {
                WrongNum++;
            }
        }
        return WrongNum;
    }

    public int manhattan() {
        int step = 0;
        for (int i = 0; i < L * L; i++) {
            if (blocks[i] != (char) (i + 1) && blocks[i] != (char) 0) {
                int row = i / L;
                int col = i % L;
                step += Math.abs(row - (blocks[i] - 1) / L) + Math.abs(col - (blocks[i] - 1) % L);
            }
        }
        return step;
    }

    public boolean isGoal() {
        return this.hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        //getClass return the real-time class of Object
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int i = 0; i < L * L; i++) {
            if (this.blocks[i] != that.blocks[i]) {
                return false;
            }

        }
        return true;
    }

    private Board swap(int i, int j) {
        int[][] newBlocks = new int[L][L];
        for (int k = 0; k < L * L; k++) {
            newBlocks[k / L][k % L] = (int) this.blocks[k];
        }
        int temp = newBlocks[i / L][i % L];
        newBlocks[i / L][i % L] = newBlocks[j / L][j % L];
        newBlocks[j / L][j % L] = temp;
        return new Board(newBlocks);
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborQueue = new ArrayList<Board>();
        int blankIndex = 0;
        //locate the blank
        for (int i = 0; i < L * L; i++) {
            if (this.blocks[i] == (char) 0) {
                blankIndex = i;
                break;
            }
        }
        if (blankIndex / L != 0) {
            neighborQueue.add(swap(blankIndex, blankIndex - L));
        }
        if (blankIndex / L != L - 1) {
            neighborQueue.add(swap(blankIndex, blankIndex + L));
        }
        if (blankIndex % L != 0) {
            neighborQueue.add(swap(blankIndex, blankIndex - 1));
        }
        if (blankIndex % L != L - 1) {
            neighborQueue.add(swap(blankIndex, blankIndex + 1));
        }
        return neighborQueue;
    }

    public Board twin() {
        if (blocks[0] == (char) 0 || blocks[1] == (char) 0) {
            return swap(2, 3);
        } else {
            return swap(0, 1);
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
        Board sample1 = new Board(tiles);
        int[][] test = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board sample2 = new Board(test);
        StdOut.println(sample1);
        StdOut.println(sample1.swap(1, 2));
        StdOut.println(sample1.dimension());
        StdOut.println(sample1.hamming());
        StdOut.println(sample1.manhattan());
        StdOut.println(sample1.twin());
        StdOut.println(sample1.equals(sample2));

    }

}

