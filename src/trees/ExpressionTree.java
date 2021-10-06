package trees;

import java.util.List;
import java.util.Stack;

import tables.ErrorTable;
import tables.SymbolTable;
import utils.Symbol;
import utils.Token;

public class ExpressionTree {
    Node root;

    public ExpressionTree() {
        root = null;
    }

    public void putEquals(List<Token> tokens) {
        if (tokens.size() != 2) return;

        root = new Node(tokens.get(1));
        root.setLeft(new Node(tokens.get(0)));
    }

    public void constructFrom(List<Token> tokens) {
        Stack<Node> stN = new Stack<>();
        Stack<Token> stC = new Stack<>();

        ExpressionTree.Node t, t1, t2;

        tokens.add(0, new Token("(", -1));
        tokens.add(new Token(")", -1));

        for (Token token : tokens) {
            if (token.getLexeme().equals("("))
                stC.push(token);
            else if (token.toTerminal().matches("id|realliteral|intliteral")) {
                t = new ExpressionTree.Node(token);
                stN.push(t);
            }
            else if (getPriorityOf(token.getLexeme()) > 0) {
                while (stC.empty() == false && stC.peek().getLexeme().equals("(") == false
                        && getPriorityOf(stC.peek().getLexeme()) >= getPriorityOf(token.getLexeme())) {
                    t = new Node(stC.peek());
                    stC.pop();

                    t1 = stN.peek();
                    stN.pop();

                    t2 = stN.peek();
                    stN.pop();

                    t.setLeft(t2);
                    t.setRight(t1);

                    stN.push(t);
                }

                stC.push(token);
            }
            else if (token.getLexeme().equals(")")) {
                while (stC.empty() == false && stC.peek().getLexeme().equals("(") == false) {
                    t = new Node(stC.peek());
                    stC.pop();
                    t1 = stN.peek();
                    stN.pop();
                    t2 = stN.peek();
                    stN.pop();
                    t.setLeft(t2);
                    t.setRight(t1);
                    stN.push(t);
                }

                stC.pop();
            }
        }

        if (root != null)
            root.setRight(stN.peek());
        else
            root = stN.peek();
    }

    private int getPriorityOf(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "/":
                return 2;
            case "(":
            case ")":
                return 0;
        }

        return -1;
    }

    public boolean checkTypes() {
        return checkTypes(root).matches("Entero|Real");
    }

    public String checkTypes(Node root) {
        if (root.getLeft() == null && root.getRight() == null)
            return root.tipo;

        String leftType = "", rightType = "";

        if (root.getLeft() != null) leftType = checkTypes(root.getLeft());
        if (root.getRight()!= null) rightType= checkTypes(root.getRight());

        if ("NonCompatible".matches(leftType + "|" + rightType))
            return "NonCompatible";

        if ("none".matches(leftType + "|" + rightType)) {
            ErrorTable.getInstance().addError(root.getLine(), "Variable no declarada");
            return "NonCompatible";
        }

        if (typesCompatible(leftType, rightType, root.getLine())) {
            root.tipo = leftType;
            return leftType;
        } else
            ErrorTable.getInstance().addError(root.getLine(), "Tipos no compatibles");

        return "NonCompatible";
    }

    private boolean typesCompatible(String t1, String t2, int line) {
        if (t1.equals("none") || t2.equals("none")) {
            ErrorTable.getInstance().addError(line, "Variable no declarada");
            return false;
        }

        return t1.equals(t2);
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("Tree: ");

        inorderTraversal(root, repr);

        return repr.toString();
    }

    private void inorderTraversal(Node root, StringBuilder repr) {
        if (root == null) return;

        inorderTraversal(root.getLeft(), repr);
        repr.append(root.getName() + " ");
        inorderTraversal(root.getRight(), repr);
    }

    private void preorderTraversal(Node root, StringBuilder repr) {
        if (root == null) return;

        repr.append(root.getName() + " ");
        preorderTraversal(root.getLeft(), repr);
        preorderTraversal(root.getRight(), repr);
    }

    private void postorderTraversal(Node root, StringBuilder repr) {
        if (root == null) return;

        postorderTraversal(root.getLeft(), repr);
        postorderTraversal(root.getRight(), repr);
        repr.append(root.getName() + " ");
    }

    private class Node {
        String nombre;
        String tipo;
        double valorIdentificacion;
        int repeticion;
        int linea;
        float valor;

        Node left, right;

        public Node(Token token) {
            SymbolTable symbols = SymbolTable.getInstance();

            this.nombre = token.getLexeme();
            this.linea = token.getLine();

            if (symbols.contains(token) == false) return;
            Symbol s = symbols.search(token.getLexeme());

            if (s.getKind().equals("Número entero"))
                this.tipo = "Entero";
            else if (s.getKind().equals("Número real"))
                this.tipo = "Real";
            else
                this.tipo = s.getKind();

            this.valorIdentificacion = s.getValue();
            this.repeticion = s.getRepetitions();
        }

        public void setLeft(Node n) {
            this.left = n;
        }

        public void setRight(Node n) {
            this.right = n;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public String getName() {
            return nombre;
        }

        public int getLine() {
            return linea;
        }
    }
}
