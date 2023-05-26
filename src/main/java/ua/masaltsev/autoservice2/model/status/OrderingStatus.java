package ua.masaltsev.autoservice2.model.status;

public enum OrderingStatus {
    RECEIVED("Received"),
    PROCEEDING("Proceeding"),
    COMPLETED_SUCCESSFULLY("Completed_successfully"),
    COMPLETED_UNSUCCESSFULLY("Completed_unsuccessfully"),
    PAID("Paid");

    private final String description;

    OrderingStatus(String description) {
        this.description = description;
    }
}
