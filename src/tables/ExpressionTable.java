package tables;

import java.util.ArrayList;

import trees.ExpressionTree;

public class ExpressionTable {
    private static ExpressionTable instance;

    private ArrayList<Expression> expressions;

    private ExpressionTable() {
        expressions = new ArrayList<>();
    }

    public static ExpressionTable getInstance() {
        if (instance == null)
            instance = new ExpressionTable();

        return instance;
    }

    public void insertExpression(ArrayList<ExpressionTree.Node> expr) {
        expressions.add(new Expression(expr));
    }

    public ArrayList<Expression> getAsList() {
        return this.expressions;
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("« Tabla de expresiones »\n");

        expressions
            .forEach(expr -> repr.append(expr + "\n"));

        return repr.toString();
    }

    public class Expression {
        ArrayList<ExpressionTree.Node> expression;

        public Expression(ArrayList<ExpressionTree.Node> expression) {
            this.expression = expression;
        }

        public ArrayList<ExpressionTree.Node> getAsList() {
            return expression;
        }

        @Override
        public String toString() {
            StringBuilder repr = new StringBuilder("Expresión → ");

            expression.stream().forEach(node -> repr.append(node.getName() + " "));

            return repr.toString();
        }
    }
}
