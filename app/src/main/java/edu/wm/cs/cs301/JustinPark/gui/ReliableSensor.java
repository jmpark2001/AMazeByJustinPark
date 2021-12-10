package edu.wm.cs.cs301.JustinPark.gui;

import edu.wm.cs.cs301.JustinPark.generation.CardinalDirection;
import edu.wm.cs.cs301.JustinPark.generation.Maze;
import edu.wm.cs.cs301.JustinPark.gui.Robot.Direction;

/**
 * Implements the DistanceSensor interface
 * Collaborators -> Maze, Robot
 *
 * This class gives information about how far the sensor is
 * to a wall given a position and a direction. Also has the
 * responsibilities that the DistanceSensor has.
 * @Author: Justin Park
 */

public class ReliableSensor implements DistanceSensor{

    private Maze myMaze;
    private Direction sensorDirection;

    /**
     * Initial Setup for ReliableSensor
     */
    public ReliableSensor() {
        sensorDirection = null;
    }

    /**
     * Return the distance from the current position
     * to a wall given the direction
     */
    @Override
    public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
            throws Exception {
        /**
         * Get the current position and while the robot isn't in
         * front of a wall, depending on the direction, increment/decrement
         * the appropriate x,y coordinate values in order to "move" the position
         * of the robot
         * If there is a wall in front of the robot return the distance
         */
        int x = currentPosition[0];
        int y = currentPosition[1];
        int distToObstacle = 0;
        while(true) {
            if(x < 0 || y < 0 || x >= myMaze.getWidth() || y >= myMaze.getHeight()) {
                throw new IndexOutOfBoundsException();
            }
            switch(currentDirection) {
                case East:
                    if(myMaze.getFloorplan().hasWall(x, y, CardinalDirection.East) == true) {
                        return distToObstacle;
                    }
                    x++;
                    break;

                case West:
                    if(myMaze.getFloorplan().hasWall(x, y, CardinalDirection.West) == true) {
                        return distToObstacle;
                    }
                    x--;
                    break;

                case North:
                    if(myMaze.getFloorplan().hasWall(x, y, CardinalDirection.South) == true) {
                        return distToObstacle;
                    }
                    y++;
                    break;

                case South:
                    if(myMaze.getFloorplan().hasWall(x, y, CardinalDirection.North) == true) {
                        return distToObstacle;
                    }
                    y--;
                    break;
            }
            distToObstacle++;
            powersupply[0] -= 1.0f;
        }
    }

    /**
     * Setup maze
     */
    @Override
    public void setMaze(Maze maze) {
        myMaze = maze;
    }

    /**
     * Set the sensor direction to the given direction
     */
    @Override
    public void setSensorDirection(Direction mountedDirection) {
        sensorDirection = mountedDirection;
    }

    public Direction getSensorDirection() {
        return sensorDirection;
    }

    /**
     * Return the energy needed for sensing
     */
    @Override
    public float getEnergyConsumptionForSensing() {
        return 1.0f;
    }

    @Override
    public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
            throws UnsupportedOperationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
        // TODO Auto-generated method stub

    }

}
