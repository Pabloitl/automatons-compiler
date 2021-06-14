package analyzers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import tables.ErrorTable;
import tables.KeywordTable;
import tables.SymbolTable;

public class Lexer implements Iterator<Token>, AutoCloseable {
    KeywordTable keywordsTable = KeywordTable.getInstance();
    SymbolTable symbolsTable = SymbolTable.getInstance();
    ErrorTable errorsTable = ErrorTable.getInstance();

    Scanner source;

    int state = 0;
    int line = 0;
    String currentLine;

    int begin = 0, end = 0;

    public Lexer(String file) throws FileNotFoundException {
        source = new Scanner(new File(file));

        if (source.hasNextLine()) {
            currentLine = source.nextLine();
            ++line;
        }
        skipSpaces();
    }

	@Override
	public void close() throws Exception {
        source.close();
	}

	@Override
	public boolean hasNext() {
		return currentLine != null && end != currentLine.length();
	}

	@Override
	public Token next() {
        while (true) {
            char c = (end == currentLine.length()) ? '\n' : currentLine.charAt(end);
            switch (state) {
                case 0:
                    if (isAlphaLower(c)) {
                        state = 1;
                        ++end;
                    } else {
                        state = 4;
                    }
                    break;
                case 1:
                    if (isAlphaLower(c) || isNum(c)) {
                        state = 2;
                        ++end;
                    } else if (isDelim(c)) {
                        state = 17;
                    } else {
                        state = 16;
                        ++end;
                    }
                    break;
                case 2:
                    if (isAlphaLower(c) || isNum(c)) {
                        state = 2;
                        ++end;
                    } else if (c == '_') {
                        state = 1;
                        ++end;
                    } else if (isDelim(c)) {
                        state = 3;
                    } else {
                        state = 16;
                        ++end;
                    }
                    break;
                case 3:
                    String identifier = currentLine.substring(begin, end);

                    symbolsTable.addIdentifier(identifier);
                    return generateToken(300);
                case 4:
                    if ('1' <= c && c <= '9') {
                        state = 5;
                        ++end;
                    } else if (c == '0') {
                        state = 6;
                        ++end;
                    } else {
                        state = 7;
                    }
                    break;
                case 5:
                    if (isNum(c)) {
                        state = 5;
                        ++end;
                    } else if (c == '.') {
                        state = 13;
                        ++end;
                    } else if (isDelim(c)) {
                        state = 12;
                    } else {
                        state = 16;
                        ++end;
                    }
                    break;
                case 6:
                    if (c == '.') {
                        state = 13;
                        ++end;
                    } else if (isDelim(c)) {
                        state = 12;
                    } else {
                        state = 16;
                        ++end;
                    }
                    break;
                case 7:
                    if (isAlphaUpper(c)) {
                        state = 9;
                        ++end;
                    } else {
                        state = 8;
                    }
                    break;
                case 8:
                    if (isChar(c)) {
                        state = 10;
                        ++end;
                    } else {
                        ++end;
                        state = 16;
                    }
                    break;
                case 9:
                    if (isAlphaLower(c)) {
                        state = 9;
                        ++end;
                    } else if (isDelim(c)) {
                        state = 11;
                    } else {
                        state = 16;
                        ++end;
                    }
                    break;
                case 10:
                    return generateToken((int) currentLine.charAt(begin));
                case 11:
                    String lexeme = currentLine.substring(begin, end);

                    if (keywordsTable.getAttr(lexeme) < 600)
                        errorsTable.addError(line, lexeme);

                    return generateToken(keywordsTable.getAttr(lexeme));
                case 12:
                    return generateToken(400);
                case 13:
                    if (isNum(c)) {
                        state = 14;
                        ++end;
                    } else if (isDelim(c)) {
                        state = 17;
                    } else {
                        state = 16;
                        ++end;
                    }
                    break;
                case 14:
                    if (isNum(c)) {
                        state = 14;
                        ++end;
                    } else if (isDelim(c)) {
                        state = 15;
                    } else {
                        state = 16;
                        ++end;
                    }
                    break;
                case 15:
                    return generateToken(500);
                case 16:
                    if (isDelim(c)) {
                        state = 17;
                    } else {
                        state = 16;
                        ++end;
                    }
                    break;
                case 17:
                    String error = currentLine.substring(begin, end);

                    errorsTable.addError(line, error);
                    return generateToken(999);
            }
        }
	}

    private void skipSpaces() {
        if (currentLine == null) return;

        while (end < currentLine.length() && currentLine.charAt(end) == ' ') {
            ++begin; ++end;
        }

        if (end == currentLine.length()) {
            if (source.hasNextLine()) {
                ++line;
                currentLine = source.nextLine();
            } else currentLine = null;

            begin = end = 0;
            skipSpaces();
        }
    }

    private Token generateToken(int attr) {
        String lexeme = currentLine.substring(begin, end);

        --end;
        begin = end;
        ++begin; ++end;
        state = 0;
        skipSpaces();
        return new Token(lexeme, attr);
    }

    private static boolean isAlphaNum(char c) {
        return isAlpha(c) || isNum(c);
    }

    private static boolean isAlpha(char c) {
        return isAlphaLower(c) || isAlphaUpper(c);
    }

    private static boolean isAlphaLower(char c) {
        return 'a' <= c && c <= 'z';
    }

    private static boolean isAlphaUpper(char c) {
        return 'A' <= c && c <= 'Z';
    }

    private static boolean isNum(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isDelim(char c) {
        return isChar(c)
            || c == ' '
            || c == '\t'
            || c == '\n';
    }

    private static boolean isChar(char c) {
        return c == ';'
            || c == '='
            || c == '+'
            || c == '/'
            || c == '('
            || c == ')'
            || c == ','
            || c == '-';
    }
}
