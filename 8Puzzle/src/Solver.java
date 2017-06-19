import edu.princeton.cs.algs4.MinPQ;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by gvv on 09.06.17.
 */
public class Solver {
    private Board initial;
    private Board twin;
    private boolean isSolvable;
    private Board[] solution;
    private int moves;

    /**
     * Find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        this.initial = initial;
        this.twin = initial.twin();


        solve();
    }

    private void solve() {
        MinPQ<GameTreeNode> minPQ = new MinPQ<>();
        minPQ.insert(new GameTreeNode(initial, 0, null));
        minPQ.insert(new GameTreeNode(twin, 0, null));
        GameTreeNode min = minPQ.min();
        Iterable<Board> neighbors;
        Iterator<Board> iterator;
        Board board;

        while (!min.getBoard().isGoal()) {
            min = minPQ.delMin();

            neighbors = min.getBoard().neighbors();
            iterator = neighbors.iterator();
            while (iterator.hasNext()) {
                board = iterator.next();
                if (!isExist(min, board)) {
                    minPQ.insert(new GameTreeNode(board, min.getMoves() + 1, min));
                }
            }
            min = minPQ.min();
        }
        fillSolution(min);
        isSolvable = solution[0].equals(initial);
        moves = (isSolvable) ? min.moves : -1;
    }

    /**
     * Is the initial board solvable?
     */
    public boolean isSolvable() {
        return this.isSolvable;
    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        return moves;
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (this.isSolvable) {
            return Arrays.asList(solution);
        } else {
            return null;
        }
    }

    private void fillSolution(GameTreeNode lastGameTreeNode) {
        int position = lastGameTreeNode.getMoves();
        solution = new Board[position + 1];

        do {
            solution[position--] = lastGameTreeNode.getBoard();
            lastGameTreeNode = lastGameTreeNode.getPrev();

        } while (lastGameTreeNode != null);

    }

    private boolean isExist(GameTreeNode lastGameTreeNode, Board board) {
        do {
            if (lastGameTreeNode.getBoard().equals(board)) {
                return true;
            }
            lastGameTreeNode = lastGameTreeNode.getPrev();
        } while (lastGameTreeNode != null);
        return false;
    }

    /**
     * Solve a slider puzzle (given below)
     */
    public static void main(String[] args) {

    }

    private class GameTreeNode implements Comparable<GameTreeNode> {
        private Board board;
        private int moves;
        private GameTreeNode prev;
        private int priority;

        public GameTreeNode(Board board, int moves, GameTreeNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;

            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(GameTreeNode o) {
            return this.priority - o.priority;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public GameTreeNode getPrev() {
            return prev;
        }
    }
}
