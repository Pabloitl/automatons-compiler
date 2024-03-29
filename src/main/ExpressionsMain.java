package main;

import analyzers.SymbolTableBuilder;
import analyzers.Syntax;
import tables.AbstractSintacticTree;
import tables.ErrorTable;
import tables.ExpressionTable;
import tables.Tables;
import utils.TypeChecker;

public class ExpressionsMain {
    public static void main(String[] args) throws Exception {
        Tables tables = new Tables("resources/grammar.txt");
        ExpressionTable expressions = ExpressionTable.getInstance();
        ErrorTable errors = ErrorTable.getInstance();
        AbstractSintacticTree tree = AbstractSintacticTree.getInstance();

        Syntax.runLLDriver("resources/program.txt", tables);
        SymbolTableBuilder.reviewLines("resources/program.txt");
        TypeChecker.check();

        System.out.println(expressions);

        if (errors.isEmpty() == false)
            System.err.println(errors);
    }
}
