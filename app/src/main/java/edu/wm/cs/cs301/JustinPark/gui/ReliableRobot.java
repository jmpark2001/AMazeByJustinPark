package edu.wm.cs.cs301.JustinPark.gui;

import edu.wm.cs.cs301.JustinPark.generation.CardinalDirection;
import edu.wm.cs.cs301.JustinPark.generation.Singleton;
import edu.wm.cs.cs301.JustinPark.gui.Constants.UserInput;

/*
 * Implements the Robot interface
 * Collaborators -> ReliableSensor, CardinalDirection, Controller
 *
 * The driver uses the methods in the robot class to move the robot to the exit.
 * Each move takes energy from the robot which has a limited amount of energy
 * before it depletes and the game ends. The ReliableRobot has all the
 * responsibilities of the Robot interface.
 * @Author: Justin Park
 */


public class ReliableRobot implements Robot {

    /*
     * Instantiate variables
     */
    private CardinalDirection curDirection;
    private int[] curPosition;
    private boolean hasStopped;
    private ReliableSensor leftSensor;
    private ReliableSensor rightSensor;
    private ReliableSensor frontSensor;
    private ReliableSensor backSensor;
    private ReliableSensor roomSensor;
    private ReliableSensor exitSensor;
    private float[] energy;
    private int odometer;

    /*
     * Set up initial values for robot object
     */
    public ReliableRobot() {
        curDirection = CardinalDirection.East;
        curPosition = new int[] {0, 0};
        hasStopped = false;
        leftSensor = null;
        rightSensor = null;
        frontSensor = null;
        backSensor = null;
        roomSensor = null;
        exitSensor = null;
        energy = new float []{3500.0f};
    }

    /*
     * Adds distance sensors when the ReliableRobot is instantiated
     */
    @Override
    public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
        /*
         * If the direction is left, create a sensor and set the left
         * sensor to the direction left
         * Do the same with the other directions
         */
        if(mountedDirection == Direction.BACKWARD) {
            backSensor = (ReliableSensor) sensor;
            backSensor.setSensorDirection(mountedDirection);
        }
        if(mountedDirection == Direction.FORWARD) {
            frontSensor = (ReliableSensor) sensor;
            frontSensor.setSensorDirection(mountedDirection);
        }
        if(mountedDirection == Direction.LEFT) {
            leftSensor = (ReliableSensor) sensor;
            leftSensor.setSensorDirection(mountedDirection);
        }
        if(mountedDirection == Direction.RIGHT) {
            rightSensor = (ReliableSensor) sensor;
            rightSensor.setSensorDirection(mountedDirection);
        }
        exitSensor = (ReliableSensor) sensor;
        roomSensor = (ReliableSensor) sensor;
    }

    public boolean hasSensor(Direction direction) {
        if(direction == Direction.BACKWARD) {
            if(backSensor != null) {
                return true;
            }
        }
        if(direction == Direction.FORWARD) {
            if(frontSensor != null) {
                return true;
            }
        }
        if(direction == Direction.LEFT) {
            if(leftSensor != null) {
                return true;
            }
        }
        if(direction == Direction.RIGHT) {
            return rightSensor != null;
        }
        return false;
    }
    /*
     * Returns the current position from the controller
     */
    @Override
    public int[] getCurrentPosition() throws Exception {
        return Singleton.state.getCurrentPosition();
    }

    /*
     * Returns the direction from the controller
     */
    @Override
    public CardinalDirection getCurrentDirection() {
        return Singleton.state.getCurrentDirection();
    }

    /*
     * Returns the battery level
     */
    @Override
    public float getBatteryLevel() {
        return energy[0];
    }

    /*
     * Sets the starting battery power
     */
    @Override
    public void setBatteryLevel(float level) {
        energy[0] = level;

    }

    /*
     * Returns the amount of energy needed for a full rotation
     */
    @Override
    public float getEnergyForFullRotation() {
        return 12.0f;
    }

    /*
     * Returns the amount of energy needed for a step forward
     */
    @Override
    public float getEnergyForStepForward() {
        return 6.0f;
    }

    /*
     * Returns the current path length
     */
    @Override
    public int getOdometerReading() {
        // TODO Auto-generated method stub
        return odometer;
    }

    /*
     * Changes path length to 0
     */
    @Override
    public void resetOdometer() {
        odometer = 0;
    }

    /*
     * Rotates the robot in the direction specified
     */
    @Override
    public void rotate(Turn turn) {
        /*
         * If the direction is left or right
         * use the rotateClockwise function to turn
         * the robot appropriately
         * If the direction is around use the getOpposite
         * function
         * For each direction, check the battery power to
         * make sure the robot has enough energy to make
         * the turns and if it runs out change hasStopped to true
         */
        switch(turn) {
            case RIGHT:
                if(energy[0] >= 3.0f) {
                    curDirection = curDirection.rotateClockwise();
                    Singleton.state.keyDown(UserInput.RIGHT, 0);
                    energy[0] -= 3.0f;
                }
                else {
                    hasStopped = true;
                }
                break;

            case LEFT:
                if(energy[0] >= 3.0f) {
                    curDirection = curDirection.rotateClockwise();
                    curDirection = curDirection.rotateClockwise();
                    curDirection = curDirection.rotateClockwise();
                    Singleton.state.keyDown(UserInput.LEFT, 0);
                    energy[0] -= 3.0f;
                }
                else {
                    hasStopped = true;
                }
                break;

            case AROUND:
                if(energy[0] >= 6.0f) {
                    curDirection = curDirection.oppositeDirection();
                    Singleton.state.keyDown(UserInput.RIGHT, 0);
                    Singleton.state.keyDown(UserInput.RIGHT, 0);
                }
                else {
                    hasStopped = true;
                }
                break;
        }
    }

    /*
     * Moves the robot forward
     */
    @Override
    public void move(int distance) {
        /*
         * If there is enough power for a distance sensing check the
         * distance to the obstacle
         * While the distance is greater than 0, if the robot has enough
         * fuel to move forward, move forward
         * If not, set hasStopped to true
         */
        if(distance < 0) {
            throw new IllegalArgumentException();
        }
        int newDistance = 0;
        if(energy[0] >= 1.0f) {
            newDistance = distanceToObstacle(Direction.FORWARD);
            while(distance >= 1 && !hasStopped) {
                try {
                    curPosition = getCurrentPosition();
                }
                catch (Exception e) {
                    System.out.println("Error in move");
                }
                if(energy[0] >= 6.0f) {
                    if(newDistance > 0) {
                        odometer += 1;
                        newDistance -= 1;
                        distance -= 1;
                        energy[0] -= 6.0f;
                        Singleton.state.keyDown(UserInput.UP, 0);
                    }
                    else {
                        break;
                    }
                }
                else {
                    hasStopped = true;
                    break;
                }
            }
        }
        else {
            hasStopped = true;
        }
    }

    /*
     * Moves the robot to an adjacent cell regardless of whether
     * or not a wall is in between
     */
    @Override
    public void jump() {
        Singleton.state.keyDown(UserInput.JUMP, 0);
    }

    /*
     * Checks if the distance to the exit is 1
     */
    @Override
    public boolean isAtExit() {
        /*
         * Get the current position and check its distance from the exit
         * Return true if the distance is 1
         */
        int distance = 0;
        int x = 0;
        int y = 0;
        try {
            x = getCurrentPosition()[0];
            y = getCurrentPosition()[1];
            distance = Singleton.state.mazeConfig.getDistanceToExit(x, y);
        }
        catch (Exception e){
            System.out.println("Error in isAtExit");
            return false;
        }
        return distance == 1;
    }

    /*
     * Use controller's method to check if the cell is in a room
     */
    @Override
    public boolean isInsideRoom() {
        /*
         * Get the current position and use controller's method to
         * check if it's in a room
         * Return true if it is
         */
        int x = 0;
        int y = 0;
        try {
            x = getCurrentPosition()[0];
            y = getCurrentPosition()[1];
        }
        catch (Exception e) {
            System.out.println("Error in isInsideRoom");
            hasStopped = true;
        }
        return Singleton.state.getMazeConfiguration().getFloorplan().isInRoom(x, y);
    }

    /*
     * Returns true if the robot is stopped
     */
    @Override
    public boolean hasStopped() {
        /*
         * Returns the boolean assigned to this method
         * The boolean may be changed in other methods
         */
        return hasStopped;
    }

    /*
     * Returns the distance from the current cell to the obstacle
     * given a direction
     */
    @Override
    public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
        /*
         * If the current cell is not at the exit
         * check if the sensor exists for the given direction
         * For each cardinal direction, create a variable that keeps
         * track of the new direction based on its relativity to the
         * given direction
         * Call and return the reliable sensor's method to find the
         * distance to obstacle
         */
        if(this.hasSensor(direction)) {
            CardinalDirection newDirection = curDirection;
            int distToObstacle = 0;
            switch (direction) {
                case FORWARD:
                    newDirection = curDirection;
                    try {
                        distToObstacle = frontSensor.distanceToObstacle(this.getCurrentPosition(), newDirection, energy);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    break;

                case RIGHT:
                    newDirection = curDirection.rotateClockwise();
                    try {
                        distToObstacle = rightSensor.distanceToObstacle(this.getCurrentPosition(), newDirection, energy);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    break;

                case BACKWARD:
                    newDirection = curDirection.oppositeDirection();
                    try {
                        distToObstacle = backSensor.distanceToObstacle(this.getCurrentPosition(), newDirection, energy);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    break;

                case LEFT:
                    if (newDirection == CardinalDirection.North) {
                        newDirection = CardinalDirection.West;
                    } else if (newDirection == CardinalDirection.South) {
                        newDirection = CardinalDirection.East;
                    } else if (newDirection == CardinalDirection.East) {
                        newDirection = CardinalDirection.North;
                    } else if (newDirection == CardinalDirection.West) {
                        newDirection = CardinalDirection.South;
                    }
                    try {
                        distToObstacle = leftSensor.distanceToObstacle(this.getCurrentPosition(), newDirection, energy);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    break;
            }
            return distToObstacle;
        }
        else{
            throw new UnsupportedOperationException();
        }
    }

    /*
     * Returns true if the current position and direction is facing the exit
     */
    @Override
    public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
        /*
         * Check if a sensor exists with the given direction
         * and that the distance to an obstacle for the position
         * and direction is infinity
         * Return true if it is
         */
        if(hasSensor(direction) && distanceToObstacle(direction) == Integer.MAX_VALUE) {
            return true;
        }
        if(!hasSensor(direction)) {
            throw new UnsupportedOperationException();
        }
        return false;
    }

    @Override
    public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
            throws UnsupportedOperationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
        // TODO Auto-generated method stub

    }

}
