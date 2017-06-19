import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;          // size of the deque
    private Node first, last;     // top of stack

    /**
     * construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    /**
     * Is the deque empty?
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Return the number of items on the deque
     */
    public int size() {
        return n;
    }

    /**
     * Add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (oldfirst != null) {
            oldfirst.prev = first;
        } else {
            last = first;
        }
        n++;
    }

    /**
     * Add the item to the end
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        last.next = null;
        if (oldlast != null) {
            oldlast.next = last;
        } else {
            first = last;
        }
        n++;
    }

    /**
     * Remove and return the item from the front
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = first;
        }
        n--;
        return item;
    }

    /**
     * Remove and return the item from the end
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = last;
        }
        n--;
        return item;
    }

    /**
     * Return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * Unit testing (optional)
     */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(5);
//        deque.addFirst(4);
//        deque.addLast(3);
//        deque.addLast(7);
//        deque.addFirst(8);
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        assert (deque.size() == 5);
        assert (deque.removeLast() == 7);
        assert (deque.removeFirst() == 6);
        assert (deque.size() == 3);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Helper linked list class
     */
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }


}
