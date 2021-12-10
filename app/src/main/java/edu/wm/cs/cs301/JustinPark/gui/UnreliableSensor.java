package edu.wm.cs.cs301.JustinPark.gui;

import edu.wm.cs.cs301.JustinPark.generation.CardinalDirection;

/**
 * UnreliableSensor is a subclass of ReliableSensor that implements Runnable
 *
 * Collaborator -> ReliableSensor, Runnable, UnreliableRobot, WallFollower
 *
 * The UnreliableSensor alternates between times when it is
 * operational or not.
 * @Author: Justin Park
 */

public class UnreliableSensor extends ReliableSensor implements Runnable{

    private boolean working;
    private int timeToRepair, timeBetweenFailures;
    Thread thread = new Thread(this);

    /**
     * set working to true to start
     */
    public UnreliableSensor() {
        working = true;
    }

    public boolean isWorking() {
        return working;
    }

    /**
     * use the distanceToObstacle from ReliableSensor
     */
    public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
            throws Exception{
        if(working) {
            return super.distanceToObstacle(currentPosition, currentDirection, powersupply);
        }
        else {
            throw new Exception();
        }
    }

    /**
     * Begins failure and repair process
     */
    public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
            throws UnsupportedOperationException{
        working = true;
        timeBetweenFailures = meanTimeBetweenFailures;
        timeToRepair = meanTimeToRepair;
        thread.start();
    }

    /**
     * Ends failure and repair process
     */
    public void stopFailureAndRepairProcess() throws UnsupportedOperationException{
        thread = null;
    }

    /**
     * Pause thread appropriately
     */
    public void run() {
        while(thread != null) {
            try {
                working = false;
                Thread.sleep(timeToRepair * 1000);
                working = true;
                Thread.sleep(timeBetweenFailures * 1000);
            }
            catch (InterruptedException e) {

            }
        }
    }
}
