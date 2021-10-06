package utils;

import java.util.ArrayList;
import java.util.List;

import tables.TokenTable;
import trees.ExpressionTree;

public class TypeChecker {
    public static void check() {
        TokenTable tokens = TokenTable.getInstance();
        Token prev = null;
        ExpressionTree expr = null;
        List<Token> expression = new ArrayList<>();

        for (Token token : tokens.getAsList()) {
            if (expr != null && token.toTerminal().equals(";")) {
                expr.constructFrom(expression);
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
