package analyzers;

import java.util.Comparator;
import java.util.HashMap;

import tables.Tables;
import tables.TokenTable;
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
        HashMap<String, Token> idCounts = new HashMap<>();
        TokenTable tokenTable = TokenTable.getInstance();

        stack.push(tables.getInitialSymbol());
        String x = stack.top();
        Token currentToken = lex.next();
        String a = currentToken.toTerminal();
        String modifier = "";

        while (!stack.isEmpty()) {
            if (tables.isNonTerminal(x)) {
                if (tables.predict(x, a) != 0) {
                    String[] rightProd = tables.getProdNum(tables.predict(x, a));

                    stack.pop();
                    if (rightProd != null)
                        for (int i = rightProd.length - 1; i >= 0; --i)
                            stack.push(rightProd[i]);

                    x = stack.top();
                } else {
                    handleError();
                }
            } else {
                if (x.equals(a)) {
                    stack.pop();
                    x = stack.top();

                    if (a.equals("EOF")) continue;

                    if (currentToken.getLexeme().equals("Entero") || currentToken.getLexeme().equals("Real"))
                        modifier = currentToken.getLexeme();

                    if (currentToken.getLexeme().equals(";"))
                        modifier = "none";

                    if (a.equals("id")) {
                        if (idCounts.containsKey(currentToken.getLexeme())) {
                            tokenTable.addToken(idCounts.get(currentToken.getLexeme()));
                        } else {
                            int count;
                            if (idCounts.isEmpty()) {
                                count = 300;
                            }
                            else {
                                count = 1 + idCounts.values().stream().mapToInt(Token::getAttribute).max().getAsInt();
                            }

                            Token t = new Token(currentToken.getLexeme(), count, modifier);
                            idCounts.put(t.getLexeme(), t);
                            tokenTable.addToken(t);
                        }
                    } else {
                        tokenTable.addToken(new Token(currentToken.getLexeme(), currentToken.getAttribute()));
                    }

                    currentToken = lex.next();
                    a = currentToken.toTerminal();
                } else {
                    handleError();
                }
            }
        }

        if (a != "EOF") handleError();
    }

    private static void handleError() {
        System.out.println("Error Sintáctico");
        System.exit(0);
    }
}
