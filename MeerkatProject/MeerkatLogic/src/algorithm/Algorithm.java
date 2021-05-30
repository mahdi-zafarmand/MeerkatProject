package algorithm;

import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author aabnar
 */
public abstract class Algorithm extends Thread{

    /**
     *
     */
    protected volatile boolean blnDone;

    /**
     *
     */
    protected Date dtCallTime;

    /**
     *
     */
    protected Date dtRunTime = null;
    /**
     * TODO : remove either one of running and blnDone flags and re factor, they serve the same purpose here
     * 
     */
    protected volatile boolean running = true;
    
    protected BooleanProperty isThreadRunningProperty = new SimpleBooleanProperty(true);

    /**
     *
     */
    protected Algorithm() {
        blnDone = false;
        dtCallTime = new Date();
        this.setDaemon(true);
    }

    /**
     *
     * @return
     */
    public boolean isTerminated() {
        return (blnDone || Thread.interrupted());
    }

    /**
     *
     * @return
     */
    public synchronized boolean updateDataStructure() {
        boolean blnUpdated = true ;
        // TODO 
        return blnUpdated;
    }

    /**
     *
     * @return
     */
    public boolean isDone () {
        return blnDone;
    }
    
    /**
     *
     * @return
     */
    public Date getLastCallTime() {
        return dtCallTime;
    }
    
    /**
     *
     */
    protected void updateCallTime() {
        dtCallTime = new Date();
    }
    
    /**
     *
     */
    protected void updateRunTime() {
        dtRunTime = new Date();
    }
    
    /**
     *
     * @return
     */
    public Date getLastRunTime() {
        return dtRunTime;
    }
    
    public synchronized void stopThread() {
        this.isThreadRunningProperty.setValue(false);
        this.running = false;
        this.blnDone = true;

    }
    
}
