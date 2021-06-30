package tables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tables {
    private static int lookaheadMatrix[][] = {
        /*
           P  I  F   ;   i   =  L   (   )  E   ,  il  rl   E   R   +   -   /
         */
         { 1, 0, 0,  0,  0,  0, 0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0 },
         { 0, 2, 0,  0,  0,  0, 0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0 },
         { 1, 0, 0,  0,  3,  0, 3,  0,  0, 3,  0,  0,  0,  3,  3,  0,  0,  0 },
         { 1, 0, 5,  0,  4,  0, 4,  0,  0, 4,  0,  0,  0,  4,  4,  0,  0,  0 },
         { 1, 0, 0,  0,  7,  0, 8,  0,  0, 9,  0,  0,  0,  6,  6,  0,  0,  0 },
         { 1, 0, 0,  0, 10,  0, 0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0 },
         { 1, 0, 0, 12,  0,  0, 0,  0, 12, 0, 11,  0,  0,  0,  0,  0,  0,  0 },
         { 1, 0, 0,  0, 13,  0, 0,  0,  0, 0,  0, 13, 13,  0,  0,  0,  0,  0 },
         { 1, 0, 0, 15,  0,  0, 0,  0, 15, 0,  0,  0,  0,  0,  0, 14, 14, 14 },
         { 1, 0, 0, 15, 17,  0, 0, 16, 15, 0,  0, 18, 19,  0,  0,  0,  0,  0 },
         { 1, 0, 0,  0,  0,  0, 0,  0,  0, 0,  0,  0,  0, 20, 21,  0,  0,  0 },
         { 1, 0, 0,  0,  0,  0, 0,  0,  0, 0,  0,  0,  0,  0,  0, 22, 23, 24 }
    };

    private String terminals[], nonTerminals[];
    private String prodRight[][];

    private int currentLine = 0;
    private int repeatedNonTerminals = 0;

    private String initialSymbol;
    private HashMap<String, Integer> possibleS;

    public Tables(String file) throws IOException {
        possibleS = new HashMap<>();

        fromFile(file);
    }

    public boolean isTerminal(String symbol) {
        return Arrays.stream(terminals).anyMatch(s -> s.equals(symbol));
    }

    public boolean isNonTerminal(String symbol) {
        return Arrays.stream(nonTerminals).anyMatch(s -> s.equals(symbol));
    }

    public String[] getProdNum(int num) {
        return prodRight[num - 1];
    }

    public int predict(String nonTerminal, String terminal) {
        int column = getIdxOfNonTerminal(nonTerminal);
        int row = getIdxOfTerminal(terminal);

        if (column < 0 || row < 0)
            return 0;

        return lookaheadMatrix[column][row];
    }

    private int getIdxOfTerminal(String terminal) {
        return IntStream.range(0, terminals.length)
            .filter(i -> terminal.equals(terminals[i]))
            .findFirst()
            .orElse(-1);
    }

    private int getIdxOfNonTerminal(String nonTerminal) {
        return IntStream.range(0, nonTerminals.length)
            .filter(i -> nonTerminal.equals(nonTerminals[i]))
            .findFirst()
            .orElse(-1);
    }

    private void fromFile(String file) throws IOException {
        int lineCount = (int) Files.lines(Paths.get(file)).count();

        prodRight = new String[lineCount][];
        nonTerminals = new String[lineCount];

        // System.out.println("«Archivo»");
        Files.lines(Paths.get(file))
            .forEach(this::processLine);

        nonTerminals = Arrays.copyOf(nonTerminals, lineCount - repeatedNonTerminals);
        insertTerminals();
        selectInitialSymbol();
    }

	private void processLine(String line) {
        String left  = line.split("->")[0].trim();
        String right = line.split("->")[1].trim();

        // System.out.println(line);

        searchInitialState(left, right);
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

    private void searchInitialState(String left, String right) {
        if (initialSymbol == null)
            initialSymbol = left;

        if (Arrays.stream(right.split("\\s")).anyMatch(s -> left.equals(s)))
            possibleS.put(left, 2);

        possibleS.put(left, possibleS.getOrDefault(left, 0) + 1);
    }

    private void selectInitialSymbol() {
        if (possibleS.entrySet().stream().filter(set -> set.getValue() == 1).count() != 1) {
            return;
        }

        initialSymbol = possibleS.entrySet().stream()
            .filter(set -> set.getValue() == 1)
            .map(set -> set.getKey())
            .findFirst().get();
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

    public String[] getTerminals() {
        return terminals;
    }

    public String[] getNonTerminals() {
        return nonTerminals;
    }

    public String getInitialSymbol() {
        return initialSymbol;
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
