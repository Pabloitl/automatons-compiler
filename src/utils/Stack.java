package utils;

public class Stack<E> {
    Node head;

    public boolean isEmpty() {
        return head == null;
    }

    public E top() {
        if (isEmpty())
            return null;

        return head.element;
    }

    public void push(E element) {
        Node newNode = new Node(element);

        newNode.setNext(head);
        head = newNode;
    }

    public E pop() {
        if (isEmpty())
            throw new RuntimeException("Stack is empty");

        E toReturn = top();
        head = head.next;
        return toReturn;
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("Stack: Top → ");
        Node current = head;

        while (current != null) {
            repr.append(current.element + "|");
            current = current.getNext();
        }

        return repr.toString();
    }

    private class Node {
        public E element;
        private Node next;

        public Node(E element) {
            this.element = element;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return this.next;
        }
    }
}
