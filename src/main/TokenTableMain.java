package main;

import analyzers.Syntax;
import tables.Tables;
import tables.TokenTable;

public class TokenTableMain {
    public static void main(String[] args) throws Exception {
        TokenTable tokenTable = TokenTable.getInstance();
        Tables tables = new Tables("resources/grammar.txt");

        Syntax.runLLDriver("resources/program.txt", tables);

        System.out.println(tokenTable);
    }
}
