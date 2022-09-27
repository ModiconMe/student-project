package edu.javacourse.student_order.domain.register;

public class CityRegisterResponse {
    private boolean registered;
    private boolean temporal;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean existing) {
        this.registered = existing;
    }

    public boolean getTemporal() {
        return temporal;
    }

    public void setTemporal(boolean temporal) {
        this.temporal = temporal;
    }

    @Override
    public String toString() {
        return "CityRegisterCheckerResponse{" +
                "existing=" + registered +
                ", temporal=" + temporal +
                '}';
    }
}
