package almundo.model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Call {

    private final static Logger LOGGER = Logger.getLogger(Call.class.getName());

    private int number;
    private static final int MIN = 5000;
    private static final int MAX = 10000;

    public Call(int number) {
        this.number = number;
    }

    public int getCallDuration() {
        return (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void makeCall() {
        int callDuration = this.getCallDuration();
        try {
            Thread.sleep(callDuration);
        } catch (InterruptedException ie) {
            LOGGER.log(Level.SEVERE, "Call failed", ie);
        }

    }

}
