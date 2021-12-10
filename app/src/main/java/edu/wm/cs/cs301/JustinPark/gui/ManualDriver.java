package edu.wm.cs.cs301.JustinPark.gui;

import edu.wm.cs.cs301.JustinPark.generation.Maze;

public class ManualDriver implements RobotDriver{
    private Robot robot;
    private float startEnergy;

    @Override
    public void setRobot(Robot r) {
        robot = r;
        startEnergy = r.getBatteryLevel();
    }

    @Override
    public void setMaze(Maze maze) {

    }

    @Override
    public boolean drive2Exit() throws Exception {
        throw new RuntimeException("Bad ManualDriver");
    }

    @Override
    public boolean drive1Step2Exit() throws Exception {
        return false;
    }

    @Override
    public float getEnergyConsumption() {
        return startEnergy - robot.getBatteryLevel();
    }

    @Override
    public int getPathLength() {
        return robot.getOdometerReading();
    }
}
