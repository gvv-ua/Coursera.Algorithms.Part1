import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by gvv on 09.06.17.
 */
public class Board {
//    private int[] blocks;
    private char[] blocks;
    private int n;

    /**
     * Construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        this.n = blocks.length;
//        this.blocks = new int[n * n];
        this.blocks = new char[n * n];

        int pos = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
//                this.blocks[pos++] = blocks[i][j];
                this.blocks[pos++] = (char) blocks[i][j];
            }
        }
    }

    /**
     * Board dimension n
     */
    public int dimension() {
        return n;
    }

    /**
     * Number of blocks out of place
     */
    public int hamming() {
        int result = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != 0 &&  blocks[i] != i + 1) {
                result++;
            }
        }
        return result;
    }

    /**
     * Sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != 0 &&  blocks[i] != i + 1) {
                int rCurrent = (blocks[i] - 1) / n;
                int cCurrent = (blocks[i] - 1) % n;
                int rGoal = i / n;
                int cGoal = i % n;

                result += Math.abs(rCurrent - rGoal) + Math.abs(cCurrent - cGoal);
            }
        }
        return result;
    }

    /**
     *
     * Is this board the goal board?
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * A board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        int i, j;
        do {
            i = StdRandom.uniform(n * n);
            j = StdRandom.uniform(n * n);
        } while (i == j || blocks[i] == 0 || blocks[j] == 0);

        return swap(i, j);
    }

    /**
     * Does this board equal y?
     */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        for (int i = 0; i < blocks.length; i++) {
            if (this.blocks[i] != that.blocks[i]) return false;
        }
        return true;
    }


    private Board swap(int i, int j) {
        int[][] swapblocks = new int[n][n];
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < n; l++) {
                if (k * n + l == i) {
                    swapblocks[k][l] = blocks[j];
                } else if (k * n + l == j) {
                    swapblocks[k][l] = blocks[i];
                } else {
                    swapblocks[k][l] = blocks[k * n + l];
                }
            }
        }
        return new Board(swapblocks);
    }

    /**
     *
     * All neighboring boards
     */
    public Iterable<Board> neighbors() {
        Stack<Board> neighboring = new Stack<>();
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                int row = i / n;
                int col = i % n;

                if (row > 0) {
                    neighboring.push(swap(row * n + col, (row - 1) * n + col));
                }
                if (col > 0) {
                    neighboring.push(swap(row * n + col, row  * n + col - 1));
                }
                if (row < n - 1) {
                    neighboring.push(swap(row * n + col, (row + 1) * n + col));
                }
                if (col < n - 1) {
                    neighboring.push(swap(row * n + col, row  * n + col + 1));
                }

                break;
            }
        }
        return neighboring;
    }

    /**
     * String representation of this board (in the output format specified below)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", (int)blocks[i * n + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Unit tests (not graded)
     */
    public static void main(String[] args) {

    }
}
