package analyzers;

import java.util.ArrayList;

import tables.ExpressionTable;
import tables.TripletTable;
import trees.ExpressionTree;
import trees.ExpressionTree.Node;
import utils.Token;

public class TripletsTableBuilder {
    private static ExpressionTable expressions = ExpressionTable.getInstance();
    private static TripletTable triplets = TripletTable.getInstance();

    public static void build() {
        expressions.getAsList().stream()
            .forEach(TripletsTableBuilder::toTriplet);
    }

    private static void toTriplet(ExpressionTable.Expression expr) {
        ArrayList<ExpressionTree.Node> expression = expr.getAsList();

        int i = 0;
        while (expression.isEmpty() == false && i < expression.size() - 2) {
            String t1, t2, t3;
            t1 = expression.get(i).getName();
            t2 = expression.get(i + 1).getName();
            t3 = expression.get(i + 2).getName();

            if (isOperator(t1) || isOperator(t2) || !isOperator(t3)) {
                ++i; continue;
            }

            String tempVariable = triplets.addOperation(t3, t1, t2);

            expression.remove(i);
            expression.remove(i);
            expression.remove(i);
            expression.add(i, new Node(tempVariable));
            i = 0;
        }
    }

    private static boolean isOperator(String str) {
        switch (str) {
            case "+":
            case "-":
            case "/":
            case "=":
                return true;
        }

        return false;
    }
}
