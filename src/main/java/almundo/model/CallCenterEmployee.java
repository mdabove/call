package almundo.model;

public class CallCenterEmployee {

	private String name;
	private EmployeeType employeeType;
	
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

	public void takeCall(Call call) {
		try {
			Thread.sleep(call.getCallDuration());
			System.out.println("Employee: " +this.getName()+" finished call OK with call number: "+ call.getNumber());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
