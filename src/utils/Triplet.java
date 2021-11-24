package utils;

public class Triplet {
    private final String operation, firstArgument, secondArgument;

    public Triplet(String operation, String arg1, String arg2) {
        this.operation = operation;
        this.firstArgument = arg1;
        this.secondArgument = arg2;
    }

    public String getOperation() {
        return this.operation;
    }

    public String getFirst() {
        return this.firstArgument;
    }

    public String getSecond() {
        return this.secondArgument;
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder();

        repr.append(operation);
        repr.append(' ');
        repr.append(firstArgument);
        repr.append(' ');
        repr.append(secondArgument);
        repr.append(' ');

        return repr.toString();
    }
}
