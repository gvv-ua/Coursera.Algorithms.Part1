import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by gvv on 27.05.17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * Is the queue empty?
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Return the number of items on the queue
     */
    public int size() {
        return n;
    }

    /**
     * Add the item
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (n == a.length) resize(2*a.length);    // double size of array if necessary
        a[n++] = item;
    }

    /**
     * Remove and return a random item
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int i = StdRandom.uniform(n);
        Item item = a[i];
        while (i < n - 1) {
            a[i] = a[++i];
        }
        a[i] = null;
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }

    /**
     * Return (but do not remove) a random item
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int i = StdRandom.uniform(n);
        return a[i];
    }

    /**
     * Return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * Unit testing (optional)
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(5);
        queue.enqueue(4);
        queue.dequeue();

    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int procesed;
        private boolean[] items;


        public RandomizedQueueIterator() {
            items = new boolean[n];
            procesed = 0;
        }

        @Override
        public boolean hasNext() {
            return procesed < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            procesed++;
//            int j = StdRandom.uniform(n);
//            int jj = j;
//            while ((j < n) && (items[j])) {
//                j++;
//            }
//            if (j < n) {
//                items[j] = true;
//                return a[j];
//            }
//            while ((jj >= 0) && (items[jj])) {
//                jj--;
//            }
//            items[jj] = true;
//            return a[jj];
            int j = StdRandom.uniform(n);
            while (items[j]) {
                j = StdRandom.uniform(n);
            }
            items[j] = true;
            return a[j];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Resize the underlying array holding the elements
     */
    private void resize(int capacity) {
        assert capacity >= n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
}
