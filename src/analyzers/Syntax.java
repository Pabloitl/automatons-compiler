package analyzers;

import tables.Tables;
import utils.Stack;

public class Syntax {
    public static void runLLDriver(String sourceFile, Tables tables) {
        try (Lexer lex = new Lexer(sourceFile)) {
            runLLDriver(lex, tables);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runLLDriver(Lexer lex, Tables tables) {
        Stack<String> stack = new Stack<>();

        stack.push(tables.getInitialSymbol());
        String x = stack.top();
        String a = lex.next().toTerminal();
        printStatus(x, a, stack);

        while (!stack.isEmpty()) {
            if (tables.isNonTerminal(x)) {
                if (tables.predict(x, a) != 0) {
                    String[] rightProd = tables.getProdNum(tables.predict(x, a));

                    stack.pop();
                    if (rightProd != null)
                        for (int i = rightProd.length - 1; i >= 0; --i)
                            stack.push(rightProd[i]);

                    x = stack.top();

                    printStatus(x, a, stack);
                } else {
                    handleError();
                }
            } else {
                if (x.equals(a)) {
                    stack.pop();
                    x = stack.top();
                    a = lex.next().toTerminal();

                    printStatus(x, a, stack);
                } else {
                    handleError();
                }
            }
        }

        if (a != "EOF") handleError();
    }

    private static void printStatus(String x, String a, Stack<String> stack) {
        System.out.println("a := " + a);
        System.out.println("x := " + x);
        System.out.println(stack);
    }

    private static void handleError() {
        System.out.println("Error Sintáctico");
        System.exit(0);
    }
}
