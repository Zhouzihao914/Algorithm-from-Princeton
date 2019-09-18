import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());

        }
        while (k > 0) {
            StdOut.println(rq.dequeue());
            k--;
        }
    }
}
