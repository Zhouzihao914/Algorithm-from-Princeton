import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node past;
        Node next;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("there are no more items!");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Deque() {
        // construct an empty deque
        last = null;
        first = null;
        size = 0;
    }

    public boolean isEmpty() {
        // is the deque empty?
        return size == 0;
    }

    public int size() {
        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        validate(item);
        Node newNode = new Node();
        newNode.item = item;
        if (size == 0) {
            first = newNode;
            last = newNode;
            first.past = null;
            first.next = null;
        } else {
            Node oldFirst = first;
            first = newNode;
            first.next = oldFirst;
            oldFirst.past = first;
            first.past = null;
        }
        size++;
    }

    public void addLast(Item item) {
        // add the item to the end
        validate(item);
        if (last == null) {
            Node newNode = new Node();
            first = newNode;
            last = newNode;
            last.item = item;
            last.past = null;
            last.next = null;
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.past = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (size == 0) {
            throw new java.util.NoSuchElementException("there is no item");
        }
        Item item = first.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.past = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (size == 0) {
            throw new java.util.NoSuchElementException("there is no item");
        }
        Item item = first.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = last.past;
            last.next = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();
    }

    private void validate(Item item) {
        if (item == null)
            throw new IllegalArgumentException("the item is null!");
    }

    public static void main(String[] args) {
        // unit testing (optional)
        Deque<String> queue = new Deque<String>();
        StdOut.println(queue.size());
        queue.addFirst("a");
        queue.addLast("z");
        queue.addFirst("b");
        queue.addLast("y");
        StdOut.println(queue.size());
        Iterator<String> iter = queue.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }
}
