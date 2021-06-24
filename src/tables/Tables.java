package tables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Tables {
    String terminals[], nonTerminals[];
    String prodRight[][];

    int currentLine = 0;
    int repeatedNonTerminals = 0;

    public Tables(String file) throws IOException {
        fromGrammar(file);
    }

    private void fromGrammar(String file) throws IOException {
        int lineCount = (int) Files.lines(Paths.get(file)).count();

        prodRight = new String[lineCount][];
        nonTerminals = new String[lineCount];

        System.out.println("«Archivo»");
        Files.lines(Paths.get(file))
            .forEach(this::processLine);

        nonTerminals = Arrays.copyOf(nonTerminals, lineCount - repeatedNonTerminals);
        insertTerminals();
    }

    private void processLine(String line) {
        String left  = line.split("->")[0].trim();
        String right = line.split("->")[1].trim();

        System.out.println(line);

        insertProdRight(right);
        insertNonTerminals(left);

        ++currentLine;
    }

    private void insertProdRight(String derivation) {
        if (derivation.equals("ε"))
            return;

        prodRight[currentLine] = derivation.split("\\s");
    }

    private void insertNonTerminals(String symbol) {
        if (Arrays.stream(nonTerminals).filter(sym -> sym != null).anyMatch(s -> s.equals(symbol))) {
            ++repeatedNonTerminals;
            return;
        }

        nonTerminals[currentLine - repeatedNonTerminals] = symbol;
    }

    private void insertTerminals() {
        Set<String> NTSet = Set.of(nonTerminals);

        terminals = Arrays.stream(prodRight)
            .filter(arr -> arr != null)
            .flatMap(row -> Arrays.stream(row))
            .filter(symbol -> !NTSet.contains(symbol))
            .distinct()
            .toArray(String[]::new);
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder();

        repr.append("«Lados derechos de las producciones»\n");
        Arrays.stream(prodRight)
            .forEach(arr -> repr.append(Arrays.toString(arr) + "\n"));
        repr.append("\n");

        repr.append("«Símbolos no terminales»\n");
        Arrays.stream(nonTerminals)
            .forEach(symbol -> repr.append(symbol + "\n"));
        repr.append("\n");

        repr.append("«Símbolos terminales»\n");
        Arrays.stream(terminals)
            .forEach(symbol -> repr.append(symbol + "\n"));

        return repr.toString();
    }

}
