package almundo.dispatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import almundo.model.Call;
import almundo.model.CallCenterEmployee;

public class Dispatcher {
	private final static Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());
	private ExecutorService executorService;
	private PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue;
	
	
	public Dispatcher(int size, PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue){
		this.executorService = Executors.newFixedThreadPool(size);
		this.employeesPriorityQueue = employeesPriorityQueue;
	}

	public void dispatchCall(Call call) throws InterruptedException{
		// take will wait if there is no employees available, solving the unavailability problem
		// proposed in the exercise questions
		CallCenterEmployee availableEmployee = employeesPriorityQueue.take();
		executorService.submit(() -> attendCall(availableEmployee, call));
	}

	public void shutDown() {
		// gracefully shut down executor; shutdown let threads to finish but block any incoming task
		executorService.shutdown();
			try {
				if (!executorService.awaitTermination(120, TimeUnit.SECONDS)) {
			    	//force it if it's taking too much time
					executorService.shutdownNow();
				}
			} catch (InterruptedException ie) {
				LOGGER.log(Level.SEVERE, "Shutdown has encountered an error", ie);
				executorService.shutdownNow();
			}
	}
	
	private void attendCall(CallCenterEmployee availableEmployee, Call call) {
		availableEmployee.takeCall(call);
		employeesPriorityQueue.add(availableEmployee);
	}
	
}
