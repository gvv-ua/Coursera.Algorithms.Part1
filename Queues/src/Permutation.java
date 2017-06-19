import edu.princeton.cs.algs4.StdIn;

import java.util.NoSuchElementException;

/**
 * Created by gvv on 27.05.17.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queque = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queque.enqueue(s);
        }
        if ((k < 0) || (k > queque.size())) throw new NoSuchElementException("Stack underflow");

        while (k-- > 0) {
            System.out.println(queque.dequeue());
        }
    }
}
