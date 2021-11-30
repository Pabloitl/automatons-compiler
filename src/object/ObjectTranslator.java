package object;

import java.io.FileWriter;
import java.util.HashMap;

import tables.TripletTable;
import utils.Triplet;

public class ObjectTranslator {
    public static void translate(String file) {
        TripletTable triplets = TripletTable.getInstance();
        HashMap<String, Integer> directions = new HashMap<>();

        try (FileWriter f = new FileWriter(file)) {
            for (Triplet tri : triplets.asList()) {
                String op = tri.getOperation();
                String first = tri.getFirst();
                String second = tri.getSecond();

                f.write(translateOperation(op));
                f.write(" ");

                if (isIO(op) == false) {
                    f.write(translateArg(second, directions));
                    f.write(", ");
                }

                f.write(translateArg(first, directions));
                f.write("\n");

                if (isIO(op)) {
                    f.write("WRCHAR #10\n");
                }

                if (isArithmethic(op)) {
                    f.write(String.format("MOVE .A, %s", translateArg(first, directions)));
                    f.write("\n");
                }
            }

            f.write("HALT\n");
        } catch (Exception e) {
            System.err.println("An error ocurred while building object code");
        }
    }

    private static boolean isArithmethic(String op) {
        switch (op) {
            case "-":
            case "/":
            case "+":
                return true;
        }
        return false;
    }

    private static boolean isIO(String op) {
        switch (op) {
            case "Escribir":
            case "Leer":
                return true;
        }
        return false;
    }

    private static String translateArg(String argument, HashMap<String, Integer> dirs) {
        final int offset = 0;

        if (isParseable(argument)) {
            return "#" + argument;
        }

        if (dirs.containsKey(argument) == false) {
            dirs.put(argument, dirs.size() + offset + 1);
        }

        return "/" + dirs.get(argument);
    }

    private static boolean isParseable(String num) {
        try {
            Double.parseDouble(num);
        } catch(Exception e){
            return false;
        }
        return true;
    }

    private static String translateOperation(String op) {
        switch (op) {
            case "Escribir":
                return "WRINT";
            case "Leer":
                return "ININT";
            case "+":
                return "ADD";
            case "-":
                return "SUB";
            case "/":
                return "DIV";
            case "=":
                return "MOVE";
        }

        return "NOP";
    }
}
