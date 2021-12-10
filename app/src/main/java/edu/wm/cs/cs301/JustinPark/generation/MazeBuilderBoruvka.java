package edu.wm.cs.cs301.JustinPark.generation;

import java.util.*;
import java.util.Random;

public class MazeBuilderBoruvka extends MazeBuilder implements Runnable{

    public MazeBuilderBoruvka() {
        super();
        System.out.println("MazeBuilderBoruvka uses Boruvka's algorithm to generate maze.");
    }

    private int[][] horizontal_weights;
    private int[][] vertical_weights;
    @Override
    protected void generatePathways() {
        /* first assign every wallboard a unique weight
         * then create an arraylist of arraylists
         * each arraylist will have an (x,y) coordinate to start
         * while the array has more than 1 value
         * iterate through the array and go to each (x,y) coordinate and break the lowest
         * weight wall at each coordinate
         * search for the (x,y) coordinate that shares that broken wall and combine
         * the two arraylists
         */
        ArrayList<ArrayList<int[]>> array = new ArrayList<ArrayList<int[]>>();
        StubOrder stub_order = new StubOrder();
        Maze maze = stub_order.getMaze();
        horizontal_weights = new int[maze.getHeight()-2][maze.getWidth()];
        vertical_weights = new int[maze.getHeight()][maze.getWidth()-2];
        ArrayList<Integer> weights = new ArrayList<>();
        for(int x=0; x<horizontal_weights[0].length; x++) {
            for(int y=0; y<horizontal_weights[1].length; y++) {
                Random rnd = new Random();
                while(true) {
                    horizontal_weights[x][y] = rnd.nextInt(10000);
                    if(weights.contains(horizontal_weights[x][y]) == false) {
                        weights.add(horizontal_weights[x][y]);
                        break;
                    }
                }
            }
        }
        for(int x=0; x<vertical_weights[0].length; x++) {
            for(int y=0; y<vertical_weights[1].length; y++) {
                Random rnd = new Random();
                while(true) {
                    vertical_weights[x][y] = rnd.nextInt(10000);
                    if(weights.contains(vertical_weights[x][y]) == false) {
                        weights.add(vertical_weights[x][y]);
                        break;
                    }
                }
            }
        }
        for(int x=0; x<maze.getWidth(); x++) {
            for(int y=0; y<maze.getHeight(); y++) {
                if(x != 0) {
                    Wallboard wallboard = new Wallboard(x, y, CardinalDirection.North);
                }
                if(x != maze.getWidth()-1){
                    Wallboard wallboard = new Wallboard(x, y, CardinalDirection.South);
                }
            }
        }
    }

    public int getEdgeWeight(Wallboard w) {
        if(w.getDirection().compareTo(CardinalDirection.East) == 0 || w.getDirection().compareTo(CardinalDirection.West) == 0) {
            if(w.getDirection().compareTo(CardinalDirection.East) == 0) {
                return vertical_weights[w.getX()][w.getY()-1];
            }
            return vertical_weights[w.getX()][w.getY()];
        }
        else {
            if(w.getDirection().compareTo(CardinalDirection.North) == 0) {
                return horizontal_weights[w.getX()][w.getY()];
            }
            return horizontal_weights[w.getX()+1][w.getY()];
        }
    }

}
