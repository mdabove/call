package almundo.almundo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import almundo.dispatcher.Dispatcher;
import almundo.model.Call;
import almundo.model.CallCenterEmployee;
import almundo.model.Director;
import almundo.model.Operator;
import almundo.model.Supervisor;

/**
 * @author mdabove
 *
 */
public class App {
	
	private final static Logger LOGGER = Logger.getLogger(App.class.getName());
	private static final int MIN_CALL_DURATION = 5000;
	private static final int MAX_CALL_DURATION  = 10000;
	private static final int MAX_CALLCENTER = 5;
	
    public static void main( String[] args ) throws InterruptedException {
    	LOGGER.log(Level.INFO, "Starting");
    	// PriorityBlockingQueue ensures a priority queue by passing a comparator or making the object implements comparable)
    	// also, it will be wait till the queue is non-empty to poll an object
    	PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue = createEmployeeQueue();
    	List<Call> callList = createCallList();
    	Dispatcher dispatcher = new Dispatcher(MAX_CALLCENTER, employeesPriorityQueue);

    	for (Call call : callList) {
    		dispatcher.dispatchCall(call);
    	}

    	dispatcher.shutDown();
	}
    
     
	private static List<Call> createCallList() {
		List<Call> callList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			callList.add(new Call(MIN_CALL_DURATION, MAX_CALL_DURATION, i));
    	}
		return callList;
	}

	private static PriorityBlockingQueue<CallCenterEmployee> createEmployeeQueue() {
		PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue = new PriorityBlockingQueue<>(5, 
			new Comparator<CallCenterEmployee>(){	
				@Override
				public int compare(CallCenterEmployee c1, CallCenterEmployee c2) {
	            	return (int) (c1.getEmployeeType().getValue() - c2.getEmployeeType().getValue());
	        	}
			}
		);
		employeesPriorityQueue.add(new Operator("Jake LaMotta OPERATOR"));
		employeesPriorityQueue.add(new Operator("Ringo Starr OPERATOR"));
		employeesPriorityQueue.add(new Operator("Mick Jagger OPERATOR"));
		employeesPriorityQueue.add(new Operator("Indio Solari OPERATOR"));
		employeesPriorityQueue.add(new Operator("Axl Rose OPERATOR"));
		employeesPriorityQueue.add(new Supervisor("Dave Gahan SUPERVISOR"));
		employeesPriorityQueue.add(new Director("Jimi Hendrix DIRECTOR"));
		return employeesPriorityQueue;
	}
}
