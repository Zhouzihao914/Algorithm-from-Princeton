import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int size;

    private class RandomIterator implements Iterator<Item> {
        private int iterSize;
        private Item[] iterarray;

        public RandomIterator() {
            iterSize = size;
            iterarray = (Item[]) new Object[iterSize];
            for (int i = 0; i < iterSize; i++) {
                iterarray[i] = a[i];
            }
        }

        public boolean hasNext() {
            return (iterSize > 0);
        }

        public Item next() {
            if (iterSize == 0) {
                throw new java.lang.UnsupportedOperationException();
            }
            int randnum = StdRandom.uniform(0, iterSize);
            iterSize--;
            Item item = iterarray[randnum];
            iterarray[randnum] = iterarray[iterSize];
            iterarray[iterSize] = item;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public RandomizedQueue() {
        a = (Item[]) new Object[1];
        size = 0;
    }

    private void validate(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    public boolean isEmpty() {
        // is the queue empty?
        return (size == 0);
    }

    public int size() {
        // return the number of items on the queue
        return size;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public void enqueue(Item item) {
        // add the item
        validate(item);
        a[size++] = item;
        if (size == a.length) {
            resize(2 * a.length);
        }
    }

    public Item dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("There is no item");
        }
        int randnum = StdRandom.uniform(0, size);
        size--;
        Item item = a[randnum];
        a[randnum] = a[size];
        a[size] = null;
        if (size > 0 && size == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    public Item sample() {
        // return (but do not remove) a random item
        if (size == 0) {
            throw new java.util.NoSuchElementException("There is no item");
        }
        int randnum = StdRandom.uniform(0, size);
        return a[randnum];
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomIterator();
    }

    public static void main(String[] args) {
        // unit testing (optional)
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.dequeue();
        Iterator<String> iter1 = queue.iterator();
        Iterator<String> iter2 = queue.iterator();
        while (iter1.hasNext()) {
            StdOut.print(iter1.next() + "，");
        }
        StdOut.println();
        while (iter2.hasNext()) {
            StdOut.print(iter2.next() + "，");
        }
        StdOut.println();

    }
}
