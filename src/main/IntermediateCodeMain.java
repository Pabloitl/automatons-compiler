package main;

import analyzers.SymbolTableBuilder;
import analyzers.Syntax;
import analyzers.TripletsTableBuilder;
import tables.AbstractSintacticTree;
import tables.ErrorTable;
import tables.ExpressionTable;
import tables.Tables;
import tables.TripletTable;
import utils.TypeChecker;

public class IntermediateCodeMain {
    public static void main(String[] args) throws Exception {
        Tables tables = new Tables("resources/grammar.txt");
        ErrorTable errors = ErrorTable.getInstance();
        TripletTable triplets = TripletTable.getInstance();


        Syntax.runLLDriver("resources/program.txt", tables);
        SymbolTableBuilder.reviewLines("resources/program.txt");
        TypeChecker.check();

        TripletsTableBuilder.build();

        System.out.println(triplets);
        triplets.writeToFile("resources/codigo_intermedio.txt");

        if (errors.isEmpty() == false)
            System.err.println(errors);
    }
}
