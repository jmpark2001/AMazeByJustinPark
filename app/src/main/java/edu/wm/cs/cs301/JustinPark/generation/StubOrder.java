package edu.wm.cs.cs301.JustinPark.generation;

public class StubOrder implements Order{
    private int skill_level;
    private Builder builder;
    private boolean perfect;
    private Maze maze;
    private int seed;
    private int percent_done;

    public StubOrder() {
        this.skill_level = 1;
        this.builder = Order.Builder.DFS;
        this.perfect = true;
        this.seed = 13;
    }

    public StubOrder(int skill_level, Builder builder, boolean perfect, int seed) {
        this.skill_level = skill_level;
        this.builder = builder;
        this.perfect = perfect;
        this.seed = seed;
    }

    @Override
    public int getSkillLevel() {
        return skill_level;
    }

    @Override
    public Builder getBuilder() {
        return builder;
    }

    @Override
    public boolean isPerfect() {
        return perfect;
    }

    @Override
    public int getSeed() {
        return seed;
    }

    public Maze getMaze() {
        return maze;
    }

    public int getPercent() {
        return percent_done;
    }

    @Override
    public void deliver(Maze mazeConfig) {
        maze = mazeConfig;
    }

    @Override
    public void updateProgress(int percentage) {
        percent_done = percentage;
    }


}
