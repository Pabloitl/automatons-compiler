package main;

import analyzers.Syntax;
import tables.Tables;

public class SyntaxMain {
    public static void main (String[] args) throws Exception {
        Tables tables = new Tables("resources/grammar.txt");

        System.out.println(tables);

        System.out.println("«Análisis Sintáctico»");
        Syntax.runLLDriver("resources/program.txt", tables);
    }
}
