package almundo.model;

public enum EmployeeType {

    OPERATOR(0), SUPERVISOR(1), DIRECTOR(2);

    private Integer value;

    public int getValue() {
        return value;
    }

    EmployeeType(int value) {
        this.value = value;
    }

}
