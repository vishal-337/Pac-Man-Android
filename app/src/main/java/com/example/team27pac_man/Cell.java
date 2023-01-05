package com.example.team27pac_man;

public class Cell {

    boolean topWall = false;
    boolean bottomWall = false;
    boolean rightWall = false;
    boolean leftWall = false;
    boolean pellet = true;
    boolean visited = false;
    int col, row;

    public Cell(int col, int row) {
        this.col = col;
        this.row = row;

    }
}
