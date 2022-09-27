package edu.javacourse.student_order.domain.entities;

public enum StudentOrderStatus {
    START, CHECKED;

    public static StudentOrderStatus fromValue(int value) {
        for (StudentOrderStatus studentOrderStatus : StudentOrderStatus.values()) {
            if (value == studentOrderStatus.ordinal()) {
                return studentOrderStatus;
            }
        }
        throw new RuntimeException("Unknown value: " + value);
    }
}
