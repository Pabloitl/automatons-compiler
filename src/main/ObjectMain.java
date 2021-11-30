package main;

import analyzers.SymbolTableBuilder;
import analyzers.Syntax;
import analyzers.TripletsTableBuilder;
import object.ObjectTranslator;
import optimization.Optimizer;
import tables.ErrorTable;
import tables.Tables;
import tables.TripletTable;
import utils.TypeChecker;

public class ObjectMain {

    public static void main(String[] args) throws Exception {
        Tables tables = new Tables("resources/grammar.txt");
        ErrorTable errors = ErrorTable.getInstance();
        TripletTable triplets = TripletTable.getInstance();


        Syntax.runLLDriver("resources/program.txt", tables);
        SymbolTableBuilder.reviewLines("resources/program.txt");
        TypeChecker.check();

        TripletsTableBuilder.build();
        triplets.writeToFile("resources/codigo_intermedio.txt");

        Optimizer.optimize();
        triplets.writeToFile("resources/codigo_optimizado.txt");

        ObjectTranslator.translate("resources/object.ens");

        if (errors.isEmpty() == false)
            System.err.println(errors);
    }

}
