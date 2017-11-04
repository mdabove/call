package almundo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.junit.Test;

import almundo.dispatcher.Dispatcher;
import almundo.model.Call;
import almundo.model.CallCenterEmployee;
import almundo.model.Director;
import almundo.model.EmployeeType;
import almundo.model.Operator;
import almundo.model.Supervisor;

/**
 * Unit test for almundo Dispatcher
 */
public class DispatcherAlmundoTest {

    private final static Logger LOGGER = Logger.getLogger(DispatcherAlmundoTest.class.getName());
    private static final int MAX_CALLCENTER = 10;
    private Dispatcher dispatcher;
    private static Comparator<CallCenterEmployee> employeesComparator = new Comparator<CallCenterEmployee>() {
        @Override
        public int compare(CallCenterEmployee c1, CallCenterEmployee c2) {
            return (int) (c1.getEmployeeType().getValue() - c2.getEmployeeType().getValue());
        }
    };

    /**
     * 
     * Test that ten calls are attended by employees
     * 
     */
    @Test
    public void testDispatcherAttendTenCalls() {
        PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue = createEmployeeQueue();
        dispatcher = new Dispatcher(MAX_CALLCENTER, employeesPriorityQueue);
        List<Call> callList = createCallList(10);

        doCall(callList);

        dispatcher.shutDown();

        assertEquals(10, getCallsAnswered(employeesPriorityQueue));
    }

    /**
     * 
     * Test that more than ten calls are attended by the queue of employees
     * 
     */
    @Test
    public void testDispatcherAttendMoreThanTenCalls() {
        PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue = createEmployeeQueue();
        dispatcher = new Dispatcher(MAX_CALLCENTER, employeesPriorityQueue);
        List<Call> callList = createCallList(20);

        doCall(callList);

        dispatcher.shutDown();

        assertEquals(20, getCallsAnswered(employeesPriorityQueue));
    }

    /**
     * 
     * Tests the priority when taking an object from the queue 1st OP, 2nd SUP,
     * 3d DIR
     * 
     * @throws InterruptedException
     */
    @Test
    public void testPriority() throws InterruptedException {
        PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue = new PriorityBlockingQueue<>(5,
                employeesComparator);
        employeesPriorityQueue.add(new Supervisor("Luis Alberto Spinetta SUPERVISOR"));
        employeesPriorityQueue.add(new Director("Jimi Hendrix DIRECTOR"));
        employeesPriorityQueue.add(new Operator("Jake LaMotta OPERATOR"));
        employeesPriorityQueue.add(new Operator("Jerry Garcia OPERATOR"));
        assertEquals(employeesPriorityQueue.take().getEmployeeType().getValue(), EmployeeType.OPERATOR.getValue());
        assertEquals(employeesPriorityQueue.take().getEmployeeType().getValue(), EmployeeType.OPERATOR.getValue());
        assertEquals(employeesPriorityQueue.take().getEmployeeType().getValue(), EmployeeType.SUPERVISOR.getValue());
        assertEquals(employeesPriorityQueue.take().getEmployeeType().getValue(), EmployeeType.DIRECTOR.getValue());
    }

    private List<Call> createCallList(int callsQuantity) {
        List<Call> callList = new ArrayList<>();
        IntStream.range(0, callsQuantity).forEach(number -> callList.add(new Call(number)));
        return callList;
    }

    private int getCallsAnswered(PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        employeesPriorityQueue.forEach(employee -> atomicInteger.addAndGet(employee.getCallsAnswered()));
        return atomicInteger.get();

    }

    private void doCall(List<Call> callList) {
        callList.forEach(call -> {
            try {
                dispatcher.dispatchCall(call);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Dispatch Call failed", e);
            }
        });
    }

    private PriorityBlockingQueue<CallCenterEmployee> createEmployeeQueue() {
        PriorityBlockingQueue<CallCenterEmployee> employeesPriorityQueue = new PriorityBlockingQueue<>(5,
                employeesComparator);
        employeesPriorityQueue.add(new Supervisor("Dave Gahan SUPERVISOR"));
        employeesPriorityQueue.add(new Director("Jimi Hendrix DIRECTOR"));
        employeesPriorityQueue.add(new Operator("Jake LaMotta OPERATOR"));
        employeesPriorityQueue.add(new Operator("Ringo Starr OPERATOR"));
        employeesPriorityQueue.add(new Operator("Mick Jagger OPERATOR"));
        employeesPriorityQueue.add(new Operator("Indio Solari OPERATOR"));
        employeesPriorityQueue.add(new Operator("Axl Rose OPERATOR"));
        return employeesPriorityQueue;
    }

}
