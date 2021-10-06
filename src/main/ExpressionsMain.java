package main;

import analyzers.SymbolTableBuilder;
import analyzers.Syntax;
import tables.ErrorTable;
import tables.SymbolTable;
import tables.Tables;
import utils.TypeChecker;

public class ExpressionsMain {
    public static void main(String[] args) throws Exception {
        Tables tables = new Tables("resources/grammar.txt");
        ErrorTable errors = ErrorTable.getInstance();

        Syntax.runLLDriver("resources/program.txt", tables);
        SymbolTableBuilder.reviewLines("resources/program.txt");

        TypeChecker.check();

        if (errors.isEmpty() == false)
            System.err.println(errors);
    }
}
