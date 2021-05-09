package utils;

public enum Mutations {
    INSERT("Insert"),
    INVERSION("Inversion"),
    SWAP("Swap");

    private final String text;

    Mutations(String text) {
        this.text = text;
    }

    public static Mutations getByText(String text) {
        return Mutations.valueOf(text);
    }

    public String getText() {
        return text;
    }
}
