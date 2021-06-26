package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import tables.Tables;

public class Firsts {
    HashMap<String, HashSet<Entry>> firsts;

    public Firsts(Tables tables) {
        firsts = new HashMap<>();

        calculateFirsts(tables);
    }

    private void calculateFirsts(Tables tables) {
        Arrays.stream(tables.getNonTerminals())
            .forEach(nt -> getFirst(nt, tables));
    }

    public HashSet<Entry> getFirst(String nt) {
        return getFirst(nt, null);
    }

    private HashSet<Entry> getFirst(String nt, Tables tables) {
        if (firsts.containsKey(nt)) return firsts.get(nt);

        String[][] prods = tables.getProdRgiht(nt);
        HashSet<Entry> result = new HashSet<>();

        for (String[] prod : prods) {
            int prodNum = tables.getProdNum(nt, prod);

            if (prod == null) {
                result.add(new Entry(null, prodNum));
            } else if (tables.isTerminal(prod[0])) {
                result.add(new Entry(prod[0], prodNum));
            } else {
                HashSet<Entry> firstsProd = getFirst(prod[0], tables);

                firstsProd.stream()
                    .filter(o -> !result.contains(o))
                    .forEach(entry -> result.add(new Entry(entry.getSymbol(), prodNum)));
            }
        }

        firsts.put(nt, result);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder();

        firsts.entrySet().stream()
            .forEach(entry -> {
                repr.append(entry.getKey())
                    .append(" → ");

                entry.getValue().stream()
                    .forEach(e -> repr.append(e).append(" "));

                repr.append("\n");
            });

        return repr.toString();
    }

    public static class Entry {
        String symbol;
        int prodNum;

        public Entry(String symbol, int prodNum) {
            this.symbol = symbol;
            this.prodNum = prodNum;
        }

        public String getSymbol() {
            return symbol;
        }

        public int getProdNum() {
            return prodNum;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Firsts.Entry) {
                if (symbol == ((Firsts.Entry) obj).symbol) return true;

                return symbol.equals(((Firsts.Entry) obj).getSymbol());
            }

            return false;
        }

        @Override
        public int hashCode() {
            return (symbol == null) ? 0 : symbol.hashCode();
        }

        @Override
        public String toString() {
            return "(" + symbol + ", " + prodNum + ")";
        }
    }
}
