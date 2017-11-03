package almundo.dispatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import almundo.model.Call;
import almundo.model.CallCenterEmployee;

public class Dispatcher {

	private ExecutorService executor;
	private PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue;
	
	
	public Dispatcher(int size, PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue){
		this.executor = Executors.newFixedThreadPool(size);
		this.employeesPriorityQueue = employeesPriorityQueue;
	}

	public void dispatchCall(Call call) throws InterruptedException{
		CallCenterEmployee availableEmployee = employeesPriorityQueue.take();
		executor.submit(() -> attendCall(availableEmployee, call));
	}

	public void shutDown() {
		//gracefully shut down executor; shutdown let threads to finish but block any incoming task
		executor.shutdown();
		   try {
		     if (!executor.awaitTermination(120, TimeUnit.SECONDS)) {
		    	 //force it if it's taking too much time
		    	 executor.shutdownNow();
		     }
		   } catch (InterruptedException ie) {
			  executor.shutdownNow();
		   }
	}
	
	private void attendCall(CallCenterEmployee availableEmployee, Call call) {
		availableEmployee.takeCall(call);
		employeesPriorityQueue.add(availableEmployee);
	}
	
}
