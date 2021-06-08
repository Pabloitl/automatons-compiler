package utils;

import java.util.function.Consumer;

public class List<E> {
    Node head;

    public void add(E element) {
        if (head == null) {
            head = new Node(element);
            return;
        }

        Node current = head;

        while (current.getNext() != null) {
            current = current.getNext();
        }

        current.setNext(new Node(element));
    }

    public boolean contains(E element) {
        Node current = head;

        while (current != null) {
            if (current.element.equals(element))
                return true;
            current = current.getNext();
        }

        return false;
    }

    public void forEach(Consumer<E> fun) {
        Node current = head;

        while (current != null) {
            fun.accept(current.element);
            current = current.getNext();
        }
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
