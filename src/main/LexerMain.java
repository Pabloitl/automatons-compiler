package main;

import analyzers.Lexer;
import tables.ErrorTable;
import tables.KeywordTable;
import tables.SymbolTable;

public class LexerMain {
    public static void main(String[] args) {
        KeywordTable keywordsTable = KeywordTable.getInstance();
        SymbolTable symbolsTable = SymbolTable.getInstance();
        ErrorTable errorsTable = ErrorTable.getInstance();

        try (Lexer lex = new Lexer("resources/program.txt")) {
            while(lex.hasNext()) {
                System.out.println( lex.next() );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println(keywordsTable);
        System.out.println(symbolsTable);
        System.out.println(errorsTable);
    }
}
