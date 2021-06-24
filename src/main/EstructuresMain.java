package main;

import java.util.stream.IntStream;

import tables.Tables;
import utils.Stack;

public class EstructuresMain {
    public static void main (String[] args) {
        Tables tablas = null;

        try {
            tablas = new Tables("resources/grammar.txt");
        } catch(Exception e){
            e.printStackTrace();
        }

        System.out.println();
        System.out.println(tablas);

        testStack();
    }

    private static void testStack() {
        Stack<String> stack = new Stack<>();

        System.out.println("«Prueba Pila»");
        System.out.println(stack);
        IntStream.range(1, 3)
            .forEach(n -> {
                System.out.println("Push (" + n + ")");
                stack.push(n + "");
                System.out.println(stack);
            });

        System.out.println("Empty (" + stack.isEmpty() + ")");

        while (stack.isEmpty() == false) {
            System.out.println(stack);
            System.out.println("Top (" + stack.top() + ")");
            System.out.println("Pop (" + stack.pop() + ")");
        }
        System.out.println(stack);
        System.out.println("Empty (" + stack.isEmpty() + ")");
    }
}
