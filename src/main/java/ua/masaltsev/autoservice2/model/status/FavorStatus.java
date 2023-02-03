package ua.masaltsev.autoservice2.model.status;

public enum FavorStatus {
    PAID("Paid"),
    UNPAID("Unpaid");

    private final String description;

    FavorStatus(String description) {
        this.description = description;
    }
}
