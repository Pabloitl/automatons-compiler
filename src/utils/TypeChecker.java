package utils;

import java.util.ArrayList;
import java.util.List;

import tables.ExpressionTable;
import tables.TokenTable;
import trees.ExpressionTree;

public class TypeChecker {
    public static void check() {
        TokenTable tokens = TokenTable.getInstance();
        Token prev = null;
        ExpressionTree expr = null;
        List<Token> expression = new ArrayList<>();

        boolean reading = false;

        for (Token token : tokens.getAsList()) {
            if (token.getLexeme().equals("Leer"))
                reading = true;

            if (token.getLexeme().equals("Escribir")) {
                expr = new ExpressionTree();

                expr.putWrite();
            }

            if (token.toTerminal().equals(";"))
                reading = false;

            if (reading && token.toTerminal().equals("id")) {
                Token action = new Token("Leer", -1);
                expr = new ExpressionTree();

                expr.constructFrom(List.of(action, token));
                ExpressionTable.getInstance().insertExpression(expr.getInPostOrder());

                expr = null;
            }

            if (expr != null && token.toTerminal().equals(";")) {
                expr.constructFrom(expression);
                ExpressionTable.getInstance().insertExpression(expr.getInPostOrder());
                if (expr.checkTypes() == false)
                    return;

                expr = null;
                expression.clear();
            }

            if (prev != null && prev.toTerminal().equals("id"))
                if (token.toTerminal().equals("=")) {
                    expr = new ExpressionTree();

                    expr.putEquals(List.of(prev, token));
                }

            if (expr != null) {
                expression.add(token);
            }

            prev = token;
        }
    }
}
