package optimization;

import java.util.ArrayList;

import tables.TripletTable;
import utils.Triplet;

public class Optimizer {
    private static final TripletTable triplets = TripletTable.getInstance();

    public static void optimize() {
        reduce();
        removeUnused();
    }

    // Regla 1 y Regla 2
    private static void removeUnused() {
        ArrayList<Triplet> trs = triplets.asList();
        ArrayList<Integer> seen = new ArrayList<>();
        String analyzing = "";

        boolean changed = false;
        do {
            changed = false;
            for (int i = 0; i < trs.size(); ++i) {
                Triplet triplet = trs.get(i);
                seen.clear();

                if (!triplet.getOperation().equals("="))
                    continue;

                analyzing = triplet.getFirst();
                seen.add(i);

                for (int j = i + 1; j  < trs.size(); ++j) {
                    triplet = trs.get(j);

                    if (triplet.getOperation().matches("Leer|Escribir") && (triplet.getFirst().equals(analyzing) || triplet.getSecond().equals(analyzing))) {
                        seen.clear();
                        break;
                    }

                    if (triplet.getOperation().equals("=") && triplet.getFirst().equals(analyzing)) {
                        break;
                    }

                    if (triplet.getSecond().equals(analyzing)) {
                        seen.clear();
                        break;
                    }

                    if (!triplet.getOperation().matches("Leer|Escribir|=") && triplet.getFirst().equals(analyzing)) {
                        seen.add(j);
                        continue;
                    }
                }

                if (seen.size() > 0) changed = true;
                triplets.deleteTriplets(seen);
                seen.clear();
            }
        } while (changed);

        triplets.deleteTriplets(seen);
    }

    // Regla 4
    private static void reduce() {
        ArrayList<Triplet> trs = triplets.asList();
        ArrayList<Integer> toRemove = new ArrayList<>();

        for (int i = 1; i < trs.size() - 1; ++i) {
            String op = trs.get(i).getOperation();
            String second = trs.get(i).getSecond();

            if (!isParseable(second)) {
                continue;
            }

            double arg = Double.parseDouble(second);

            switch (op) {
                case "/":
                    if (arg == 1) {
                        toRemove.add(i);
                    }
                    break;
                case "+":
                    if (arg == 0) {
                        toRemove.add(i);
                    }
                    break;
                case "-":
                    if (arg == 0) {
                        toRemove.add(i);
                    }
                    break;
            }
        }

        triplets.deleteTriplets(toRemove);
    }

    private static boolean isParseable(String num) {
        try {
            Double.parseDouble(num);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
