package edu.wm.cs.cs301.JustinPark.gui;

import edu.wm.cs.cs301.JustinPark.generation.CardinalDirection;
import edu.wm.cs.cs301.JustinPark.generation.Maze;
import edu.wm.cs.cs301.JustinPark.gui.Robot.Direction;
import edu.wm.cs.cs301.JustinPark.gui.Robot.Turn;

/*
 * Implements the RobotDriver interface
 * Collaborators -> ReliableRobot, Maze, Floorplan, Distance
 *
 * The wizard uses information from the maze and and moves the
 * robot to the exit in the quickest way. Contains the
 * responsibilities of the RobotDriver
 * @Author: Justin Park
 */

public class Wizard implements RobotDriver{

    private Robot robot;
    private Maze myMaze;
    private float[] maxEnergy = new float [1];
    public Wizard() {

    }

    /*
     * Set the current robot to the parameter robot
     */
    @Override
    public void setRobot(Robot r) {
        robot = r;
        maxEnergy[0] = r.getBatteryLevel();
    }

    /*
     * Sets the maze to the driver
     */
    @Override
    public void setMaze(Maze maze) {
        myMaze = maze;
    }

    /*
     * Drives the robot to the exit 1 step at a time
     * Return true if the robot is at the exit facing the exit
     */
    @Override
    public boolean drive2Exit() throws Exception {
        /*
         * While the robot is not at the exit call drive1Step2Exit
         * Once at the exit, check if the robot is facing the exit
         * and turn appropriately
         * Return true if the robot is facing the exit at the exit
         */
        if(robot.isAtExit() != true) {
            if(robot.hasStopped()) {
                throw new Exception();
            }
            drive1Step2Exit();
        }
        if(robot.isAtExit() != true) {
            return false;
        }
        else if(robot.canSeeThroughTheExitIntoEternity(Direction.LEFT)) {
            robot.rotate(Turn.LEFT);
            return true;
        }
        else if(robot.canSeeThroughTheExitIntoEternity(Direction.RIGHT)) {
            robot.rotate(Turn.RIGHT);
            return true;
        }
        else if(robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD)) {
            robot.rotate(Turn.AROUND);
            return true;
        }


        return false;
    }

    /*
     * Get the current position and move one step
     * towards the quickest exit path
     */
    @Override
    public boolean drive1Step2Exit() throws Exception {
        /*
         * Get the current cell position and the direction the
         * robot is facing
         * Depending on the direction you are facing, get the min
         * path from the cell for each direction you could be
         * facing and turn to the direction with the shortest path
         * and move forward 1
         */
        if(robot.hasStopped()) {
            throw new Exception();
        }
        int x = robot.getCurrentPosition()[0];
        int y = robot.getCurrentPosition()[1];
        CardinalDirection newDirection = robot.getCurrentDirection();
        int minDistance = 0;
        int leftDistance = Integer.MAX_VALUE;
        int rightDistance = Integer.MAX_VALUE;
        int frontDistance = Integer.MAX_VALUE;
        int backDistance = Integer.MAX_VALUE;
        switch(newDirection) {
            case East:
                if(x+1 < myMaze.getWidth() && robot.distanceToObstacle(Direction.FORWARD) != 0) {
                    frontDistance = myMaze.getDistanceToExit(x+1, y);
                }
                else if(x-1 >= 0 && robot.distanceToObstacle(Direction.BACKWARD) != 0) {
                    backDistance = myMaze.getDistanceToExit(x-1, y);
                }
                else if(y-1 >= 0 && robot.distanceToObstacle(Direction.RIGHT) != 0) {
                    rightDistance = myMaze.getDistanceToExit(x, y-1);
                }
                else if(y+1 < myMaze.getHeight() && robot.distanceToObstacle(Direction.LEFT) != 0) {
                    leftDistance = myMaze.getDistanceToExit(x, y+1);
                }
                break;

            case West:
                if(x-1 >= 0 && robot.distanceToObstacle(Direction.FORWARD) != 0) {
                    frontDistance = myMaze.getDistanceToExit(x-1, y);
                }
                else if(x+1 < myMaze.getWidth() && robot.distanceToObstacle(Direction.BACKWARD) != 0) {
                    backDistance = myMaze.getDistanceToExit(x+1, y);
                }
                else if(y+1 < myMaze.getHeight() && robot.distanceToObstacle(Direction.RIGHT) != 0) {
                    rightDistance = myMaze.getDistanceToExit(x, y+1);
                }
                else if(y-1 >= 0 && robot.distanceToObstacle(Direction.LEFT) != 0) {
                    leftDistance = myMaze.getDistanceToExit(x, y-1);
                }
                break;

            case North:
                if(y-1 >= 0 && robot.distanceToObstacle(Direction.FORWARD) != 0) {
                    frontDistance = myMaze.getDistanceToExit(x, y-1);
                }
                else if(y+1 < myMaze.getHeight() && robot.distanceToObstacle(Direction.BACKWARD) != 0) {
                    backDistance = myMaze.getDistanceToExit(x, y+1);
                }
                else if(x-1 >= 0 && robot.distanceToObstacle(Direction.RIGHT) != 0) {
                    rightDistance = myMaze.getDistanceToExit(x-1, y);
                }
                else if(x+1 < myMaze.getWidth() && robot.distanceToObstacle(Direction.LEFT) != 0) {
                    leftDistance = myMaze.getDistanceToExit(x+1, y);
                }
                break;

            case South:
                if(x+1 < myMaze.getWidth() && robot.distanceToObstacle(Direction.FORWARD) != 0) {
                    frontDistance = myMaze.getDistanceToExit(x+1, y);
                }
                else if(x-1 >= 0 && robot.distanceToObstacle(Direction.BACKWARD) != 0) {
                    backDistance = myMaze.getDistanceToExit(x-1, y);
                }
                else if(y+1 < myMaze.getHeight() && robot.distanceToObstacle(Direction.RIGHT) != 0) {
                    rightDistance = myMaze.getDistanceToExit(x, y+1);
                }
                else if(y-1 >= 0 && robot.distanceToObstacle(Direction.LEFT) != 0) {
                    leftDistance = myMaze.getDistanceToExit(x, y-1);
                }
                break;
        }

        minDistance = getMinValue(frontDistance, backDistance, rightDistance, leftDistance);
        if(frontDistance == minDistance) {
            robot.move(1);
        }
        else if(backDistance == minDistance) {
            robot.rotate(Turn.AROUND);
            robot.move(1);
        }
        else if(rightDistance == minDistance) {
            robot.rotate(Turn.RIGHT);
            robot.move(1);
        }
        else {
            robot.rotate(Turn.LEFT);
            robot.move(1);

        }
        return true;
    }

    protected int getMinValue(int a, int b, int c, int d) {
        int min = a;
        if(b<min) {
            min = b;
        }
        if(c<min) {
            min = c;
        }
        if(d<min) {
            min = d;
        }
        return min;
    }

    /*
     * Return battery level subtracted from total power
     */
    @Override
    public float getEnergyConsumption() {
        return maxEnergy[0] - robot.getBatteryLevel();
    }

    /*
     * Return the odometer reading
     */
    @Override
    public int getPathLength() {
        return robot.getOdometerReading();
    }

}
