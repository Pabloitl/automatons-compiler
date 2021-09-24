package analyzers;

import tables.SymbolTable;
import tables.TokenTable;
import utils.Token;

public class SymbolTableBuilder {
    public static void reviewLines(String sourceFile) throws Exception {

        TokenTable tokens = TokenTable.getInstance();
        SymbolTable symbols = SymbolTable.getInstance();

        tokens.stream()
            .filter(token -> token.toTerminal().matches("id|realliteral|intliteral"))
            .forEach(token -> symbols.addIdentifier(token));

        try (Lexer lex = new Lexer(sourceFile)) {
            while (lex.hasNext()) {
                Token readToken = lex.next();

                if (readToken.toTerminal().matches("id|realliteral|intliteral") == false)
                    continue;

                symbols.addLinetoToken(lex.getLineNumber(), readToken.getLexeme());
            }
        } catch (Exception e) {
            System.err.println("An error occured while reading source file");
        }
    }
}
