package main;

import analyzers.Lexer;
import tables.ErrorTable;
import tables.KeywordTable;
import tables.SymbolTable;

public class LexerMain {
    public static void main(String[] args) {
        try (Lexer lex = new Lexer("resources/program.txt")) {
            while(lex.hasNext()) {
                System.out.println( lex.next() );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println(KeywordTable.getInstance());
        System.out.println(SymbolTable.getInstance());
        System.out.println(ErrorTable.getInstance());
    }
}
