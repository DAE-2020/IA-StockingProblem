package utils;

public enum Recombinations {
    PMX("PMX"),
    CSEX("CSEX"),
    RECOMBINATION_OX2("Recombination OX2");

    private final String text;

    Recombinations(String text) {
        this.text = text;
    }

    public static Recombinations getByText(String text) {
        return Recombinations.valueOf(text);
    }

    public String getText() {
        return text;
    }
}
