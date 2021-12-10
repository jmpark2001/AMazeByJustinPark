package edu.wm.cs.cs301.JustinPark.gui;

import edu.wm.cs.cs301.JustinPark.generation.Maze;
import edu.wm.cs.cs301.JustinPark.gui.Robot.Direction;
import edu.wm.cs.cs301.JustinPark.gui.Robot.Turn;

/**
 * Implements the RobotDriver interface
 * Collaborators -> ReliableRobot, Maze, Floorplan, Distance
 *
 * The WallFollower moves the robot to the exit by hugging a wall
 * and following it all the way to the exit.
 * @Author: Justin Park
 */

public class WallFollower implements RobotDriver{

    private Robot robot;
    private Maze myMaze;
    private float[] maxEnergy = new float [1];

    /**
     * Set the current robot to the parameter robot
     */
    @Override
    public void setRobot(Robot r) {
        robot = r;
        maxEnergy[0] = r.getBatteryLevel();
    }

    /**
     * Sets the maze to the driver
     */
    @Override
    public void setMaze(Maze maze) {
        myMaze = maze;
    }

    /**
     * Calls drive1Step2Exit to move the robot
     * to the exit
     * Return true if the robot reaches the exit
     * Return false if the loop executes and the
     * robot is not at the exit
     * Throws exception if it runs out of power
     */
    @Override
    public boolean drive2Exit() throws Exception {
        while(robot.isAtExit() != true) {
            if(robot.hasStopped()) {
                throw new Exception();
            }
            drive1Step2Exit();
        }
        if(robot.isAtExit() != true) {
            return false;
        }
        if(robot.canSeeThroughTheExitIntoEternity(Direction.RIGHT)) {
            robot.rotate(Turn.RIGHT);
            return true;
        }
        else if(robot.canSeeThroughTheExitIntoEternity(Direction.LEFT)) {
            robot.rotate(Turn.LEFT);
            return true;
        }
        return false;
    }

    /**
     * Follow the left wall until you reach the exit
     * Go left if possible
     * If not possible go forward
     * If both aren't possible the robot is in a corner
     * Turn right and continue
     */
    @Override
    public boolean drive1Step2Exit() throws Exception {
        if(robot.hasStopped()) {
            throw new Exception();
        }
        int leftDist = robot.distanceToObstacle(Direction.LEFT);
        int frontDist = robot.distanceToObstacle(Direction.FORWARD);
        if(leftDist != 0) {
            robot.rotate(Turn.LEFT);
            robot.move(1);
            return true;
        }
        if(frontDist != 0) {
            robot.move(1);
            return true;
        }
        else {
            robot.rotate(Turn.RIGHT);
        }
        if(robot.isAtExit()) {
            return false;
        }
        return true;
    }

    /**
     * Return battery level subtracted from total power
     */
    @Override
    public float getEnergyConsumption() {
        return maxEnergy[0] - robot.getBatteryLevel();
    }

    /**
     * Return odometer reading
     */
    @Override
    public int getPathLength() {
        return robot.getOdometerReading();
    }

}
