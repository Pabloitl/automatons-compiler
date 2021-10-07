package tables;

import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import utils.Symbol;

public class AbstractSintacticTree {
    private static AbstractSintacticTree instance;

    private ArrayList<Node> nodes;

    private AbstractSintacticTree() {
        nodes = new ArrayList<>();
    }

    public static AbstractSintacticTree getInstance() {
        if (instance == null)
            instance = new AbstractSintacticTree();

        return instance;
    }

    public void createOrUpdate() {
        SymbolTable symbols = SymbolTable.getInstance();

        nodes.clear();
        symbols.stream()
            .map(Node::new)
            .forEach(nodes::add);
    }

    public void writeToFile(String f) {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(f)))) {
            writer.println(this.toString());
        } catch (Exception e) {
            System.err.println("No se pudo escribir el árbol sintáctico abstracto");
        }
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder(
                String.format(
                    "%10s\t%10s\t%14s\t%10s\t%14s\t%10s\n",
                    "Nombre",
                    "Tipo",
                    "Val. ident",
                    "Repeticiones",
                    "Línea",
                    "Val. atrib"));

        nodes
            .forEach(symbol -> repr.append(symbol + "\n"));

        return repr.toString();
    }

    private class Node extends Symbol {
        private float valor;

        public Node(Symbol s) {
            super(s);

            if (s.getKind().matches("Número entero"))
                this.valor = s.intvalue;

            if (s.getKind().equals("Número real"))
                this.valor = s.floatvalue;
        }

        @Override
        public String toString() {
            StringBuffer repr = new StringBuffer();
            repr.append(String.format("%10s", this.getLexeme()) + "\t");

            if (this.getKind().equals("Número entero"))
                repr.append(String.format("%10s\t", "Entero"));
            else if (this.getKind().equals("Número real"))
                repr.append(String.format("%10s\t", "Real"));
            else
                repr.append(String.format("%10s", this.getKind()) + "\t");

            if (this.getKind().equals("Número real"))
                repr.append(String.format("%10s", super.floatvalue) + "\t");
            else
                repr.append(String.format("%10s", super.intvalue) + "\t");

            repr.append(String.format("%12s", this.getLines().length) + "\t");
            repr.append(String.format("%14s\t", Arrays.toString(this.getLines())));

            if (this.getKind().equals("Número entero"))
                repr.append(String.format("%10s", super.intvalue));
            else if (this.getKind().equals("Número real"))
                repr.append(String.format("%10s", super.floatvalue));

            if (this.getKind().equals("Entero"))
                repr.append(String.format("%10s", 0));
            else if (this.getKind().equals("Real"))
                repr.append(String.format("%10s", 0.0));


            return repr.toString();
        }
    }
}
