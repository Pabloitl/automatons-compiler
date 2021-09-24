package main;

import analyzers.SymbolTableBuilder;
import analyzers.Syntax;
import tables.SymbolTable;
import tables.Tables;

public class SymbolTableMain {
    public static void main(String[] args) throws Exception {
        Tables tables = new Tables("resources/grammar.txt");
        SymbolTable symbols = SymbolTable.getInstance();

        Syntax.runLLDriver("resources/program.txt", tables);
        SymbolTableBuilder.reviewLines("resources/program.txt");

        System.out.println(symbols);
    }
}
