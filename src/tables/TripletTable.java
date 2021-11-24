package tables;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import utils.Triplet;

public class TripletTable {
    private static TripletTable instance;

    private ArrayList<Triplet> instructions;

    private int variablesCount = 0;

    private TripletTable() {
        instructions = new ArrayList<>();
    }

    public static TripletTable getInstance() {
        if (instance == null)
            instance = new TripletTable();

        return instance;
    }

    public ArrayList<Triplet> asList() {
        return instructions;
    }

    public void deleteTriplets(List<Integer> toRemove) {
        int remove[] = toRemove.stream()
            .mapToInt(i -> i)
            .sorted()
            .distinct()
            .toArray();

        for (int i = remove.length - 1; i >= 0; --i) {
            int index = remove[i];

            if (index >= instructions.size()) continue;

            instructions.remove(index);
        }
    }

    public String addOperation(String op, String t1, String t2) {
        if (op.equals("Leer")) {
            instructions.add(new Triplet(op, t1, ""));
            return "";
        }

        if (op.equals("Escribir")) {
            instructions.add(new Triplet(op, t2, ""));
            return "";
        }

        if (!op.equals("=")) {
            instructions.add(new Triplet("=", "t" + variablesCount, t1));
            instructions.add(new Triplet(op, "t" + variablesCount, t2));
            ++variablesCount; return "t" + (variablesCount - 1);
        }
        instructions.add(new Triplet(op, t1, t2));
        return "";
    }

    public void writeToFile(String file) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(this.toString());
        } catch (Exception e) {
            System.err.println("An error ocurred while writing object code");
        }
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder();

        instructions.stream().forEach(t -> repr.append(t + "\n"));

        return repr.toString();
    }
}
