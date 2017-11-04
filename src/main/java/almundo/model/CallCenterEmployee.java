package almundo.model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CallCenterEmployee {

    private final static Logger LOGGER = Logger.getLogger(CallCenterEmployee.class.getName());
    private String name;
    private EmployeeType employeeType;
    private int callsAnswered;

    public CallCenterEmployee(String name, EmployeeType employeeType) {
        this.name = name;
        setEmployeeType(employeeType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public int getCallsAnswered() {
        return callsAnswered;
    }

    public void setCallsAnswered(int callsAnswered) {
        this.callsAnswered = callsAnswered;
    }
    
    public void takeCall(Call call) {
        this.setCallsAnswered(this.getCallsAnswered() + 1);
        LOGGER.log(Level.INFO,
                "Employee: " + this.getName() + " took call with call ID: " + call.getNumber() +  " and took "+ this.getCallsAnswered() + " call(s)");
        call.makeCall();
    }



}
