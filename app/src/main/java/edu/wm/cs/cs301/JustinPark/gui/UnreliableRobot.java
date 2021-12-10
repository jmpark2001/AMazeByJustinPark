package edu.wm.cs.cs301.JustinPark.gui;

/**
 * UnreliableRobot extends the ReliableRobot
 * Collaborators -> UnreliableSensor, Wall Follower
 *
 * The driver uses the methods in the robot class to move the robot to the exit.
 * Each move takes energy from the robot which has a limited amount of energy
 * before it depletes and the game ends. The UnreliableRobot has all the
 * responsibilities of the Robot interface. It differs from the ReliableRobot
 * because the distance sensors in this class may randomly malfunction and have
 * to be repaired
 * @Author: Justin Park
 */

public class UnreliableRobot extends ReliableRobot {

    DistanceSensor frontSensor, backSensor, leftSensor, rightSensor;

    /**
     * Given a string flrb that represents which sensors are
     * unreliable and reliable, set the sensor (front, left, right, back)
     * to the correct sensor
     */
    public UnreliableRobot(String flrb) {
        String[] sensors = new String[4];
        if(flrb == null) {
            for(int x=0; x<=sensors.length; x++) {
                sensors[x] = "1";
            }
        }
        else {
            sensors = flrb.split("");
        }

        if(sensors[0].equals("0")) {
            frontSensor = new UnreliableSensor();
        }
        else if(sensors[0].equals("1")) {
            frontSensor = new ReliableSensor();
        }

        if(sensors[1].equals("0")) {
            leftSensor = new UnreliableSensor();
        }
        else if(sensors[1].equals("1")) {
            leftSensor = new ReliableSensor();
        }

        if(sensors[2].equals("0")) {
            rightSensor = new UnreliableSensor();
        }
        else if(sensors[2].equals("1")) {
            rightSensor = new ReliableSensor();
        }

        if(sensors[3].equals("0")) {
            backSensor = new UnreliableSensor();
        }
        else if(sensors[3].equals("1")) {
            backSensor = new ReliableSensor();
        }
    }

    /**
     * For the specified direction, call the startFailureAndRepairProcess
     * from the UnrealiableSensor class for that sensor
     */
    @Override
    public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair) {
        switch(direction) {
            case FORWARD:
                frontSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
                break;

            case LEFT:
                leftSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
                break;

            case RIGHT:
                rightSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
                break;

            case BACKWARD:
                backSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
                break;
        }
    }

    /**
     * For whatever direction is specified, call the stopFailureAndRepairProcess
     * method from the UnreliableSensor class for that sensor
     */
    @Override
    public void stopFailureAndRepairProcess(Direction direction)
            throws UnsupportedOperationException{
        switch(direction) {
            case FORWARD:
                frontSensor.stopFailureAndRepairProcess();
                break;

            case LEFT:
                leftSensor.stopFailureAndRepairProcess();
                break;

            case RIGHT:
                rightSensor.stopFailureAndRepairProcess();
                break;

            case BACKWARD:
                backSensor.stopFailureAndRepairProcess();
                break;
        }
    }
}
