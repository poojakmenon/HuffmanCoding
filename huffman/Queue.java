package huffman;

/**
 * This class contains a generic queue class 
 * uses queue class as a circular linked list.
 * @author Pooja Menon
 */
public class Queue<T> {
    private Node<T> back;
    private int size;

    private class Node<T> {
        T data;
        Node<T> next;

        public Node(T d, Node<T> n) {
            data = d;
            next = n;
        }
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    public void enqueue(T item) {
        size++; // size increases with enqueue
        
        // node that points to itself
        if (size == 1) {
            back = new Node<>(item, null);
            back.next = back;
            return;
        }
        
        // add a node to back and update back
        back.next = new Node<>(item, back.next);
        back = back.next;
    }

    public T dequeue() {
        T item = back.next.data;
        size--; // Dequeue

        // size decreases with dequeue
        if (size == 0) {
            back = null;
            return item;
        }
        
        // remove front of the list (back.next)
        back.next = back.next.next;
        return item;
    }
    
    public T peek() {
        // Just return the data of the front of the queue
        return back.next.data;
    }
}
