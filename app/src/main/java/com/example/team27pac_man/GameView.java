package com.example.team27pac_man;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {
    Context context;
    private static int lives;
    private static int pelletscount=0;

    public static int getLives() {
        return lives;
    }

    public static void setLives(int lives) {
        GameView.lives = lives;
    }

    private static int score=0;
    private enum Direction{UP,DOWN,LEFT,RIGHT}
    //TODO
    //generate 3 to 4 different layoutIds for each difficulty level
    //assign "layout" to a specific layoutIds later depending on the level
    // make separate methods scoreBoost(), and incrementScore()
    public static int [][] layout;
    private Canvas gameCanvas;
    private String pacPostion = "right";

    Queue<int[]> redq = new LinkedList<>(Arrays.asList(new int[][]{{2,5},{3,5},{3,4},{3,3}}));
    Queue<int[]> yellowq = new LinkedList<>(Arrays.asList(new int[][]{{4,5},{3,5},{3,4},{3,3}}));
    Queue<int[]> blueq = new LinkedList<>(Arrays.asList(new int[][]{{4,4},{3,4},{3,3}}));
    Queue<int[]> pinkq = new LinkedList<>(Arrays.asList(new int[][]{{2,4},{3,4},{3,3}}));
    int redcounter=0;
    int yellowcounter=0;
    int bluecounter=0;
    int pinkcounter=0;

    private cell[][] cells;
    private cell food; // for food sprite- have only added the sprite- work on functionality
    private cell player;
    private cell blue_ghost;
    private cell pink_ghost;
    private cell red_ghost;
    private cell yellow_ghost;
    private static int kills=0;
    public static int getkills(){return kills;}

    private boolean redVulnerable = false;
    private boolean blueVulnerable = false;
    private boolean pinkVulnerable = false;
    private boolean yellowVulnerable = false;
    private static final int COLS=7,ROWS=10;
    private float cellSize,hMar,vMar;
    private final float thick = 15;
    private Paint thinwall;
    private Paint wall;
    private Paint pellets;
    private  Paint power;
    Bitmap foodSprite;
    Bitmap pac;
    Bitmap blue;
    Bitmap red;
    Bitmap yellow;
    Bitmap pink;

    Timer t = new Timer();


    public static int getScore(){
        return score;
    }
    public static long setScore(int score) {
        GameView.score = score;
        return 0;
    }
    public GameView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
        wall= new Paint();
        wall.setColor(Color.BLUE);
        wall.setStrokeWidth(thick);
        thinwall= new Paint();
        thinwall.setColor(Color.WHITE);
        thinwall.setStrokeWidth(5);
        pellets = new Paint();
        pellets.setColor(Color.YELLOW);
        selectLayout();
        createMaze();

        TimerTask redstart =new TimerTask() {
            @Override
            public void run() {
                int[] pos= redq.poll();
                red_ghost=cells[pos[0]][pos[1]];
                invalidate();
                redcounter+=1;
            }

        };
        TimerTask redmove = new TimerTask() {

            @Override
            public void run() {
                redchase();
                if(player==red_ghost && !redVulnerable){
                    player=cells[3][9];
                    if(lives == 1){
                        checkLives();
                    } else {
                        lives = lives - 1;
                        TextView live = (TextView) ((Maze) context).findViewById(R.id.livesTextView);
                        live.setText("Lives:" + lives);
                    }
                }  else if (player==red_ghost && redVulnerable) {
                    kills+=1;
                    red_ghost=cells[2][5];
                    redVulnerable = false;
                    score += 25;
                    updateScore();
                    //scoreText.invalidate();
                }
                invalidate();
            }
        };
        TimerTask bluestart =new TimerTask() {
            @Override
            public void run() {
                int[] pos= blueq.poll();
                blue_ghost=cells[pos[0]][pos[1]];
                invalidate();
                bluecounter+=1;

            }
        };
        TimerTask bluemove = new TimerTask() {

            @Override
            public void run() {

                bluechase();
                if(player==blue_ghost && !blueVulnerable){
                    player=cells[3][9];
                    if(lives == 1){
                        checkLives();
                    } else {
                        lives = lives - 1;
                        TextView live = (TextView) ((Maze) context).findViewById(R.id.livesTextView);
                        live.setText("Lives:" + lives);
                    }
                }  else if (player==blue_ghost && blueVulnerable) {
                    kills+=1;
                    blueVulnerable = false;
                    score += 25;
                    updateScore();
                    //scoreText.invalidate();
                    blue_ghost=cells[4][4];
                }
                invalidate();
            }
        };
        TimerTask yellowstart =new TimerTask() {
            @Override
            public void run() {
                yellow_ghost=cells[0][1];
                invalidate();
                yellowcounter+=1;
            }
        };

        TimerTask yellowmove = new TimerTask() {

            @Override
            public void run() {
                clydeChase();
                if(player==yellow_ghost && !yellowVulnerable){
                    player=cells[3][9];
                    if(lives == 1){
                        checkLives();
                    } else {
                        lives = lives - 1;
                        TextView live = (TextView) ((Maze) context).findViewById(R.id.livesTextView);
                        live.setText("Lives:" + lives);
                    }
                } else if (player==yellow_ghost && yellowVulnerable) {
                    kills+=1;
                    yellowVulnerable = false;
                    score += 25;
                    updateScore();
                    //scoreText.invalidate();
                    int[] pos= yellowq.poll();
                    yellow_ghost=cells[4][5];
                }
                invalidate();
            }
        };

        TimerTask pinkstart =new TimerTask() {
            @Override
            public void run() {
                int[] pos= pinkq.poll();
                pink_ghost=cells[pos[0]][pos[1]];
                invalidate();
                pinkcounter+=1;

            }
        };

        TimerTask pinkmove = new TimerTask() {

            @Override
            public void run() {
                pinkchase();
                if(player==pink_ghost && !pinkVulnerable){
                    player=cells[3][9];
                    if(lives == 1){
                        checkLives();
                    } else {
                        lives = lives - 1;
                        TextView live = (TextView) ((Maze) context).findViewById(R.id.livesTextView);
                        live.setText("Lives:" + lives);
                    }
                }  else if (player==pink_ghost && pinkVulnerable) {
                    kills+=1;
                    pinkVulnerable = false;
                    score += 25;
                    updateScore();
                    //scoreText.invalidate();
                    int[] pos= pinkq.poll();
                    pink_ghost=cells[2][4];
                }
                invalidate();
            }
        };
        TimerTask cancel =new TimerTask() {
            @Override
            public void run() {
                if(redcounter==4){
                    redstart.cancel();
                }
                if(yellowcounter==4){
                    yellowstart.cancel();
                }
                if(bluecounter==3){
                    bluestart.cancel();
                }
                if(pinkcounter==3){
                    pinkstart.cancel();
                }
            }
        };

        TimerTask scorecheck= new TimerTask() {
            @Override
            public void run() {
                if (pelletscount==63){
                    checkScore();
                }
            }
        };
        t.schedule(scorecheck,0,1);
        t.schedule(redstart,0,1000);
        t.schedule(yellowstart,2000,1000);
        t.schedule(bluestart,4000,1000);
        t.schedule(pinkstart,5000,1000);
        t.schedule(cancel,0,1);
        t.schedule(bluemove,7500,750);
        t.schedule(redmove,4500,750);
        t.schedule(pinkmove,8500,7500);
        t.schedule(yellowmove,6500,750);

    }
    void updateScore() {
        Activity a = (Activity) context;
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView scoreText = (TextView) ((Maze)context).findViewById(R.id.scoreTextView);
                scoreText.setText("Score:" + score);
            }
        });
    }

    void checkScore(){
        t.cancel();
        Activity b = (Activity)context;
        b.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                b.setContentView(R.layout.activity_youwin);
                Intent in = new Intent(b, youwin.class);
                b.finish();
                b.startActivity(in);
            }
        });
//        Activity a = (Activity)context;
//        a.setContentView(R.layout.end);
        //Intent intent = new Intent(this.getContext(), End.class);
//        a.startActivity(intent);

    }
    void checkLives(){

//        System.out.println("checkLives");
        t.cancel();
        Activity a = (Activity)context;
        a.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                a.setContentView(R.layout.end);
                Intent intent = new Intent(a, End.class);
                a.finish();
                a.startActivity(intent);
            }
        });
//        Activity a = (Activity)context;
//        a.setContentView(R.layout.end);
        //Intent intent = new Intent(this.getContext(), End.class);
//        a.startActivity(intent);

    }

    void clydeChase() {
        //cell player = player;
        if (player != yellow_ghost) {
            float ghostCenterX = hMar + (yellow_ghost.col) * cellSize;
            float ghostCenterY = vMar + yellow_ghost.row * cellSize;
            float playerCenterX = hMar + (player.col) * cellSize;
            float playerCenterY = hMar + player.row * cellSize;
            float dx = Math.abs(playerCenterX - ghostCenterX);
            float dy = Math.abs(playerCenterY - ghostCenterY);
            double rand = Math.random();
            //sometimes he flees
            if (rand < 0.77) {
                if (dx > dy) {
                    //moveLeft *if pinky cell is not a left wall
                    if ((yellow_ghost.col) > (player.col) && !yellow_ghost.leftWall) {
                        //yellow_ghost.col = yellow_ghost.col - 1;
                        yellow_ghost = cells[yellow_ghost.col - 1][yellow_ghost.row];
                    } else if (!yellow_ghost.rightWall && yellow_ghost.col < 5) {
                        //move right
                        yellow_ghost = cells[yellow_ghost.col + 1][yellow_ghost.row];
                    }
                } else {
                    if ((yellow_ghost.row) > (player.row) && !yellow_ghost.topWall) {
                        //move up *if pinky cell is not a top wall
                        yellow_ghost = cells[yellow_ghost.col][yellow_ghost.row - 1];
                    } else if (!yellow_ghost.bottomWall) {
                        //move down
                        yellow_ghost = cells[yellow_ghost.col][yellow_ghost.row + 1];
                    }
                }
            } else {
                if (dx > dy) {
                    //moveLeft *if pinky cell is not a left wall
                    if ((yellow_ghost.col) > (player.col) && !yellow_ghost.leftWall) {
                        //yellow_ghost.col = yellow_ghost.col - 1;
                        yellow_ghost = cells[yellow_ghost.col + 1][yellow_ghost.row];
                    } else if (!yellow_ghost.rightWall) {
                        //move right
                        //TODO: Fix this line of code so that clyde (yellow ghost) doesn't go out of bounds.
                       // yellow_ghost = cells[yellow_ghost.col - 1][yellow_ghost.row];
                    }
                } else {
                    if ((yellow_ghost.row) > (player.row) && !yellow_ghost.topWall) {
                        //move up *if pinky cell is not a top wall
                        yellow_ghost = cells[yellow_ghost.col][yellow_ghost.row + 1];
                    } else if (!yellow_ghost.bottomWall) {
                        //move down
                        yellow_ghost = cells[yellow_ghost.col][yellow_ghost.row - 1];
                    }
                }
            }
        }

    }
    void redchase(){
        int dx = Math.abs(red_ghost.col- player.col);
        int dy = Math.abs(red_ghost.row- player.row);
        if (dx > dy) {
            if((red_ghost.col) > (player.col)-1){
                if(!red_ghost.leftWall){red_ghost= cells[red_ghost.col-1][red_ghost.row];}
                else if(!red_ghost.bottomWall){red_ghost= cells[red_ghost.col][red_ghost.row + 1];}
                else if(!red_ghost.rightWall){red_ghost= cells[red_ghost.col+1][red_ghost.row];}
                else if(!red_ghost.topWall){red_ghost= cells[red_ghost.col][red_ghost.row - 1];}

            }else{
                if(!red_ghost.rightWall){red_ghost= cells[red_ghost.col+1][red_ghost.row];}
                else if(!red_ghost.leftWall){red_ghost= cells[red_ghost.col-1][red_ghost.row];}
                else if(!red_ghost.topWall){red_ghost= cells[red_ghost.col][red_ghost.row - 1];}
                else if(!red_ghost.bottomWall){red_ghost= cells[red_ghost.col][red_ghost.row + 1];}

            }
        }else{
            if((red_ghost.row) > (player.row)-1){
                if(!red_ghost.topWall){red_ghost= cells[red_ghost.col][red_ghost.row - 1];}
                else if(!red_ghost.rightWall){red_ghost= cells[red_ghost.col+1][red_ghost.row];}
                else if(!red_ghost.bottomWall){red_ghost= cells[red_ghost.col][red_ghost.row + 1];}
                else if(!red_ghost.leftWall){red_ghost= cells[red_ghost.col-1][red_ghost.row];}

            }else{
                if(!red_ghost.bottomWall){red_ghost= cells[red_ghost.col][red_ghost.row + 1];}
                else if(!red_ghost.leftWall){red_ghost= cells[red_ghost.col-1][red_ghost.row];}
                else if(!red_ghost.topWall){red_ghost= cells[red_ghost.col][red_ghost.row - 1];}
                else if(!red_ghost.rightWall){red_ghost= cells[red_ghost.col+1][red_ghost.row];}


            }
        }

    }


    void pinkchase(){
        int dx = Math.abs(pink_ghost.col- player.col);
        int dy = Math.abs(pink_ghost.row- player.row);
        if (dx > dy) {
            if((pink_ghost.col) > (player.col)-1){
                if(!pink_ghost.leftWall){pink_ghost= cells[pink_ghost.col-1][pink_ghost.row];}
                else if(!pink_ghost.bottomWall){pink_ghost= cells[pink_ghost.col][pink_ghost.row + 1];}
                else if(!pink_ghost.topWall){pink_ghost= cells[pink_ghost.col][pink_ghost.row - 1];}
                else if(!pink_ghost.rightWall){pink_ghost= cells[pink_ghost.col+1][pink_ghost.row];}
            }else{
                if(!pink_ghost.rightWall){pink_ghost= cells[pink_ghost.col+1][pink_ghost.row];}
                else if(!pink_ghost.topWall){pink_ghost= cells[pink_ghost.col][pink_ghost.row - 1];}
                else if(!pink_ghost.bottomWall){pink_ghost= cells[pink_ghost.col][pink_ghost.row + 1];}
                else if(!pink_ghost.leftWall){pink_ghost= cells[pink_ghost.col-1][pink_ghost.row];}
            }
        }else{
            if((pink_ghost.row) > (player.row)-1){
                if(!pink_ghost.topWall){pink_ghost= cells[pink_ghost.col][pink_ghost.row - 1];}
                else if(!pink_ghost.rightWall){pink_ghost= cells[pink_ghost.col+1][pink_ghost.row];}
                else if(!pink_ghost.leftWall){pink_ghost= cells[pink_ghost.col-1][pink_ghost.row];}
                else if(!pink_ghost.bottomWall){pink_ghost= cells[pink_ghost.col][pink_ghost.row + 1];}
            }else{
                if(!pink_ghost.bottomWall){pink_ghost= cells[pink_ghost.col][pink_ghost.row + 1];}
                else if(!pink_ghost.leftWall){pink_ghost= cells[pink_ghost.col-1][pink_ghost.row];}
                else if(!pink_ghost.rightWall){pink_ghost= cells[pink_ghost.col+1][pink_ghost.row];}
                else if(!pink_ghost.topWall){pink_ghost= cells[pink_ghost.col][pink_ghost.row - 1];}

            }
        }

    }
    void bluechase(){
        int dx = Math.abs(blue_ghost.col- player.col);
        int dy = Math.abs(blue_ghost.row- player.row);
        if (dx > dy) {
            if((blue_ghost.col) > (player.col)){
                if(!blue_ghost.leftWall){blue_ghost= cells[blue_ghost.col-1][blue_ghost.row];}
                else if(!blue_ghost.topWall){blue_ghost= cells[blue_ghost.col][blue_ghost.row - 1];}
                else if(!blue_ghost.bottomWall){blue_ghost= cells[blue_ghost.col][blue_ghost.row + 1];}
                else if(!blue_ghost.rightWall){blue_ghost= cells[blue_ghost.col+1][blue_ghost.row];}
            }else{
                if(!blue_ghost.rightWall){blue_ghost= cells[blue_ghost.col+1][blue_ghost.row];}
                else if(!blue_ghost.bottomWall){blue_ghost= cells[blue_ghost.col][blue_ghost.row + 1];}
                else if(!blue_ghost.topWall){blue_ghost= cells[blue_ghost.col][blue_ghost.row - 1];}
                else if(!blue_ghost.leftWall){blue_ghost= cells[blue_ghost.col-1][blue_ghost.row];}
            }
        }else{
            if((blue_ghost.row) > (player.row)){
                if(!blue_ghost.topWall){blue_ghost= cells[blue_ghost.col][blue_ghost.row - 1];}
                else if(!blue_ghost.leftWall){blue_ghost= cells[blue_ghost.col-1][blue_ghost.row];}
                else if(!blue_ghost.rightWall){blue_ghost= cells[blue_ghost.col+1][blue_ghost.row];}
                else if(!blue_ghost.bottomWall){blue_ghost= cells[blue_ghost.col][blue_ghost.row + 1];}
            }else{
                if(!blue_ghost.bottomWall){blue_ghost= cells[blue_ghost.col][blue_ghost.row + 1];}
                else if(!blue_ghost.rightWall){blue_ghost= cells[blue_ghost.col+1][blue_ghost.row];}
                else if(!blue_ghost.leftWall){blue_ghost= cells[blue_ghost.col-1][blue_ghost.row];}
                else if(!blue_ghost.topWall){blue_ghost= cells[blue_ghost.col][blue_ghost.row - 1];}

            }
        }

    }
    private void selectLayout() {
        if(configure.getDifficulty().equals("Passive")){
            layout = new int[][]{
                    {5, 11, 11, 3, 0, 3, 3, 3, 3, 7},
                    {1, 3, 7, 1, 4, 4, 2, 6, 4, 2},
                    {1, 0, 2, 10, 5, 7, 10, 5, 3, 8},
                    {1, 4, 2, 10, 0, 2, 1, 0, 0, 12},
                    {10, 5, 0, 2, 6, 8, 1, 4, 4, 7},
                    {10, 1, 4, 4, 11, 3, 0, 3, 7, 10},
                    {6, 4, 11, 11, 0, 4, 4, 4, 4, 8}
            };
        } else if(configure.getDifficulty().equals("Easy")){
            layout = new int[][]{
                    {5, 11, 11, 3, 0, 3, 3, 3, 3, 7},
                    {1, 3, 7, 1, 4, 11, 0, 7, 5, 2},
                    {1, 0, 2, 10, 5, 7, 1, 2, 1, 8},
                    {1, 0, 2, 10, 0, 2, 1, 0, 0, 12},
                    {10, 6, 4, 2, 6, 8, 10, 1, 0, 7},
                    {10, 5, 3, 0, 3, 11, 4, 4, 7, 10},
                    {6, 4, 4, 4, 0, 4, 4, 4, 4, 8}
            };
        } else if(configure.getDifficulty().equals("Medium")){
            layout = new int[][]{
                    {5, 11, 11, 3, 0, 11, 11, 3, 3, 7},
                    {1, 7, 5, 0, 4, 11, 3, 8, 6, 2},
                    {1, 2, 10, 10, 5, 7, 1, 7, 5, 8},
                    {1, 0, 0, 10, 0, 2, 1, 0, 0, 12},
                    {10, 6, 4, 2, 6, 8, 2, 2, 1, 7},
                    {10, 5, 3, 4, 3, 3, 4, 8, 6, 2},
                    {6, 4, 4, 4, 0, 11, 11, 11, 11, 8}
            };

        } else if(configure.getDifficulty().equals("Hard")){
            layout = new int[][]{
                    {5, 11, 11, 3, 0, 11, 3, 3, 11, 7},
                    {1, 7, 5, 0, 4, 11, 2, 10, 5, 2},
                    {6, 2, 1, 10, 5, 7, 1, 8, 1, 8},
                    {1, 0, 0, 10, 0, 2, 1, 3, 0, 12},
                    {6, 7, 6, 2, 6, 8, 1, 10, 1, 7},
                    {1, 2, 5, 0, 3, 11, 0, 8, 6, 2},
                    {6, 4, 4, 4, 0, 11, 4, 11, 11, 8}
            };

        } else if(configure.getDifficulty().equals("Expert")){
            layout = new int[][]{
                    {5, 7, 3, 3, 0, 11, 3, 3, 11, 7},
                    {1, 4, 8, 0, 4, 11, 0, 7, 5, 2},
                    {6, 3, 7, 10, 5, 7, 1, 10, 1, 8},
                    {1, 2, 0, 2, 0, 2, 1, 0, 0, 12},
                    {10, 6, 8, 10, 6, 8, 1, 10, 1, 7},
                    {10, 5, 11, 0, 0, 11, 0, 8, 6, 2},
                    {6, 4, 4, 4, 0, 11, 4, 11, 11, 8}
            };

        }
    }

    private void createMaze(){
        cells=new cell[COLS][ROWS];
        for(int x = 0;x<COLS;x++){
            for(int y =0;y<ROWS;y++){

                cells[x][y]=new cell(x,y);
                if(layout[x][y]==1){
                    cells[x][y].topWall=true;
                }
                else if (layout[x][y]==2){
                    cells[x][y].bottomWall=true;

                }
                else if (layout[x][y]==3){
                    cells[x][y].leftWall=true;

                }
                else if (layout[x][y]==4){
                    cells[x][y].rightWall=true;

                }
                else if (layout[x][y]==5){
                    cells[x][y].topWall=true;
                    cells[x][y].leftWall=true;

                }
                else if (layout[x][y]==6){
                    cells[x][y].rightWall=true;
                    cells[x][y].topWall=true;

                }
                else if (layout[x][y]==7){
                    cells[x][y].bottomWall=true;
                    cells[x][y].leftWall=true;
                }
                else if (layout[x][y]==8){
                    cells[x][y].rightWall=true;
                    cells[x][y].bottomWall=true;

                }
                else if (layout[x][y]==10){
                    cells[x][y].topWall=true;
                    cells[x][y].bottomWall=true;

                }
                else if (layout[x][y]==11){
                    cells[x][y].rightWall=true;
                    cells[x][y].leftWall=true;

                }
                else if (layout[x][y]==12){
                    cells[x][y].rightWall=true;
                    cells[x][y].leftWall=true;
                    cells[x][y].bottomWall=true;

                }
                else if (layout[x][y]==13){
                    cells[x][y].topWall=true;
                    cells[x][y].bottomWall=true;
                    cells[x][y].rightWall=true;

                }

            }
        }
        player=cells[3][9];
        blue_ghost=cells[4][4];
        red_ghost=cells[2][5];
        pink_ghost=cells[2][4];
        yellow_ghost=cells[4][5];
        selectFoodPos();


    }

    private void selectFoodPos() {
        if (configure.getDifficulty().equals("Passive")){
            food = cells[5][0];
        } else if(configure.getDifficulty().equals("Easy")){
            food = cells[1][2];
        } else if(configure.getDifficulty().equals("Medium")){
            food = cells[5][1];
        } else if(configure.getDifficulty().equals("Hard")){
            food = cells[4][1];
        } else if(configure.getDifficulty().equals("Expert")){
            food = cells[4][2];
        }
        food.isFood = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        int width = getWidth();
        int height =getHeight();
        cellSize = height/(ROWS+1);
        hMar = (width-COLS*cellSize)/2;
        vMar = (height-ROWS*cellSize)/2;
        canvas.translate(hMar,vMar);
        cells[3][9].pellet=false;
        cells[2][4].pellet=false;
        cells[2][5].pellet=false;
        cells[3][4].pellet=false;
        cells[3][5].pellet=false;
        cells[4][4].pellet=false;
        cells[4][5].pellet=false;
        cells[3][9].visited=true;
        for(int x = 0;x<COLS;x++){
            for(int y =0;y<ROWS;y++){

                if(cells[x][y].pellet){
                    if((x==0 && y ==0)||(x==6 && y==0)||(x==0 && y ==9)||(x==6 && y ==9)){
                        canvas.drawCircle(
                                (x)*cellSize+(cellSize/2),
                                (y)*cellSize+(cellSize/2),
                                cellSize/5,
                                pellets
                        );

                    } else if(cells[x][y].isFood == true){
                        foodSprite = BitmapFactory.decodeResource(getResources(),R.drawable.food);
                        canvas.drawBitmap(foodSprite,(food.col*cellSize)+(cellSize/4),(food.row*cellSize)+(cellSize/4),null);
                    }
                    else {
                        canvas.drawCircle(
                                (x)*cellSize+(cellSize/2),
                                (y)*cellSize+(cellSize/2),
                                cellSize/20,
                                pellets
                        );
                    }

                }
                if(cells[x][y].topWall){
                    canvas.drawLine(
                            x*cellSize,
                            y*cellSize,
                            (x+1)*cellSize,
                            y*cellSize,
                            wall
                    );
                }

                if(cells[x][y].leftWall){
                    canvas.drawLine(
                            x*cellSize,
                            y*cellSize,
                            (x)*cellSize,
                            (y+1)*cellSize,
                            wall
                    );
                }

                if(cells[x][y].bottomWall){
                    if(x==3 && y==3){
                        canvas.drawLine(
                                x*cellSize,
                                (y+1)*cellSize,
                                (x+1)*cellSize,
                                (y+1)*cellSize,
                                thinwall
                        );

                    }else{
                        canvas.drawLine(
                                x*cellSize,
                                (y+1)*cellSize,
                                (x+1)*cellSize,
                                (y+1)*cellSize,
                                wall
                        );

                    }


                }

                if(cells[x][y].rightWall){
                    canvas.drawLine(
                            (x+1)*cellSize,
                            y*cellSize,
                            (x+1)*cellSize,
                            (y+1)*cellSize,
                            wall
                    );
                }
            }
            gameCanvas = canvas;
        }
//        switch(configure.getPacRes()) {
//            case 1:
//                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mrpacright);
//                break;
//            case 2:
//                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mspacright);
//                break;
//            case 3:
//                pac=BitmapFactory.decodeResource(getResources(),R.drawable.awarepacright);
//                break;
//        }
//        canvas.drawBitmap(pac,(player.col*cellSize)+(cellSize/4),(player.row*cellSize)+(cellSize/4),null);
        // for different positions
        if(pacPostion.equals("right")){
            rotatePacRight();
        } else if(pacPostion.equals("left")){
            rotatePacLeft();
        } else if(pacPostion.equals("up")){
            rotatePacUp();
        } else if(pacPostion.equals("down")){
            rotatePacDown();
        }
        canvas.drawBitmap(pac,(player.col*cellSize)+(cellSize/4),(player.row*cellSize)+(cellSize/4),null);

        if (!blueVulnerable) {
            blue = BitmapFactory.decodeResource(getResources(), R.drawable.blue);
        } else {
            blue = BitmapFactory.decodeResource(getResources(), R.drawable.vulnerable_ghost2);
        }
        if (!redVulnerable) {
            red = BitmapFactory.decodeResource(getResources(), R.drawable.red);
        } else {
            red = BitmapFactory.decodeResource(getResources(), R.drawable.vulnerable_ghost2);
        }
        if (!pinkVulnerable) {
            pink = BitmapFactory.decodeResource(getResources(), R.drawable.pink);
        } else {
            pink = BitmapFactory.decodeResource(getResources(), R.drawable.vulnerable_ghost2);
        }
        if (!yellowVulnerable) {
            yellow = BitmapFactory.decodeResource(getResources(), R.drawable.yellow);
        } else {
            yellow = BitmapFactory.decodeResource(getResources(), R.drawable.vulnerable_ghost2);
        }
        canvas.drawBitmap(blue, (blue_ghost.col * cellSize) + (cellSize / 4), (blue_ghost.row * cellSize) + (cellSize / 4), null);
        canvas.drawBitmap(red, (red_ghost.col * cellSize) + (cellSize / 4), (red_ghost.row * cellSize) + (cellSize / 4), null);
        canvas.drawBitmap(pink, (pink_ghost.col * cellSize) + (cellSize / 4), (pink_ghost.row * cellSize) + (cellSize / 4), null);
        canvas.drawBitmap(yellow, (yellow_ghost.col * cellSize) + (cellSize / 4), (yellow_ghost.row * cellSize) + (cellSize / 4), null);


    }

    private void movePlayer(Direction direction){
        switch (direction){
            case UP:
                pacPostion = "up";
                invalidate();
                //rotatePacUp();
                if(!player.topWall){
                    player=cells[player.col][player.row-1];
                    if(!player.visited){
                        player.pellet=false;
                        pelletscount+=1;
                        if((player.col==0 && player.row ==0)||(player.col==6 && player.row==0)||(player.col==0 && player.row==9)||(player.col==6 && player.row==9)){
//                            score=score+10; //make a bigger change?
                            score=ScoreBoost.scoreBoost(score);
                            powerPelletEaten();
                        }
                        else if(player.col == food.col & player.row == food.row){
                            score += 50;
                        } else{
//                            score=score+1;
                            score=ScoreIncrement.scoreIncrement(score);

                        }

                        updateScore();
                        player.visited=true;
                    }
                }
                break;
            case DOWN:
                pacPostion = "down";
                //rotatePacDown();
                if(!player.bottomWall){
                    player=cells[player.col][player.row+1];
                    if(!player.visited){
                        pelletscount+=1;
                        player.pellet=false;
                        if((player.col== 0 && player.row == 0)||(player.col==6 && player.row==0)||(player.col==0 && player.row==9)||(player.col==6 && player.row==9)){
//                            score=score+10;
                            score=ScoreBoost.scoreBoost(score);
                            powerPelletEaten();
                        } else if(player.col == food.col & player.row == food.row) {
                            score += 50;


                        }else{
//                            score=score+1;
                            score=ScoreIncrement.scoreIncrement(score);

                        }


                        player.visited=true;
                    }
                }
                break;
            case LEFT:
                pacPostion = "left";
                //rotatePacLeft();
                if(!player.leftWall){
                    if(player.col==0 && player.row==4){
                        player=cells[6][4];
                    }else{player=cells[player.col-1][player.row];}

                    if(!player.visited){
                        pelletscount+=1;
                        player.pellet=false;
                        if((player.col==0 && player.row ==0)||(player.col==6 && player.row==0)||(player.col==0 && player.row==9)||(player.col==6 && player.row==9)){
//                            score=score+10;
                            score=ScoreBoost.scoreBoost(score);
                            powerPelletEaten();
                        }
                        else if(player.col == food.col & player.row == food.row){
                            score += 50;
                        } else{
                            //score=score+1;
                            score=ScoreIncrement.scoreIncrement(score);

                        }

                        updateScore();
                        player.visited=true;
                    }
                }
                break;
            case RIGHT:
                pacPostion = "right";
                //rotatePacRight();
                if(!player.rightWall){
                    if(player.col==6 && player.row==4){
                        player=cells[0][4];
                    }else{player=cells[player.col+1][player.row];}
                    if(!player.visited){
                        pelletscount+=1;
                        player.pellet=false;
                        if((player.col==0 && player.row ==0)||(player.col==6 && player.row==0)||(player.col==0 && player.row==9)||(player.col==6 && player.row==9)){
//                            score=score+10;
                            score=ScoreBoost.scoreBoost(score);
                            powerPelletEaten();
                        }
                         else if(player.col == food.col & player.row == food.row){
                            score += 50;
                        } else{
//                            score=score+1;
                            score=ScoreIncrement.scoreIncrement(score);

                        }

                        updateScore();
                        player.visited=true;
                    }
                }
                break;
        }
        invalidate();

    }
    TimerTask ghostFlee = new TimerTask() {
        @Override
        public void run() {
            flee(red_ghost);
            flee(blue_ghost);
            flee(yellow_ghost);
            flee(pink_ghost);
        }
    };
    TimerTask ghostReset = new TimerTask() {
        @Override
        public void run() {
            redVulnerable = false;
            blueVulnerable = false;
            pinkVulnerable = false;
            yellowVulnerable = false;
            ghostReset.cancel();
            ghostFlee.cancel();
            t.purge();
            //call original chase methods or restart timertask?
        }
    };


    private void powerPelletEaten() {
        redVulnerable = true;
        blueVulnerable = true;
        pinkVulnerable = true;
        yellowVulnerable = true;
        invalidate();
        //t.schedule(ghostFlee,0,750);
        //t.schedule(ghostReset,5000);
        if (player == red_ghost) {
            eatGhost(red_ghost);
            redVulnerable = false;
        } else if (player == blue_ghost) {
            eatGhost(blue_ghost);
            blueVulnerable = false;
        } else if (player == pink_ghost) {
            eatGhost(pink_ghost);
            pinkVulnerable = false;
        } else if (player == yellow_ghost) {
            eatGhost(yellow_ghost);
            yellowVulnerable = true;
        }

        //TODO: all timer functionality.
        //TODO: have ghosts flee player.

    }

    private void eatGhost(cell ghost) {
        //increment and display score
        score += 25;
        updateScore();


        //reset ghost position
        if (ghost == red_ghost) {
            int[] pos= redq.poll();
            red_ghost=cells[pos[0]][pos[1]];
        } else if (ghost == blue_ghost) {
            int[] pos= blueq.poll();
            blue_ghost=cells[pos[0]][pos[1]];
        } else if (ghost == pink_ghost) {
            int[] pos= pinkq.poll();
            pink_ghost=cells[pos[0]][pos[1]];
        } else if (ghost == yellow_ghost) {
            int[] pos= yellowq.poll();
            yellow_ghost=cells[pos[0]][pos[1]];
        }
        invalidate();

    }

    private void flee(cell ghost) {
        //need to cancel original timertask for the individual chases?
        //currently inky chase
        int dx = Math.abs(ghost.col- player.col);
        int dy = Math.abs(ghost.row- player.row);
        if (dx > dy) {
            if((ghost.col) > (player.col)){
                if(!ghost.leftWall){ghost= cells[ghost.col + 1][ghost.row];}
                else if(!ghost.topWall){ghost= cells[ghost.col][ghost.row + 1];}
                else if(!ghost.bottomWall){ghost= cells[ghost.col][ghost.row - 1];}
                else if(!ghost.rightWall){ghost= cells[ghost.col - 1][ghost.row];}
            }else{
                if(!ghost.rightWall){ghost= cells[ghost.col - 1][ghost.row];}
                else if(!ghost.bottomWall){ghost= cells[ghost.col][ghost.row - 1];}
                else if(!ghost.topWall){ghost= cells[ghost.col][ghost.row + 1];}
                else if(!ghost.leftWall){ghost= cells[ghost.col + 1][ghost.row];}
            }
        }else{
            if((ghost.row) > (player.row)){
                if(!ghost.topWall){ghost= cells[ghost.col][ghost.row + 1];}
                else if(!ghost.leftWall){ghost= cells[ghost.col + 1][ghost.row];}
                else if(!ghost.rightWall){ghost= cells[ghost.col - 1][ghost.row];}
                else if(!ghost.bottomWall){ghost= cells[ghost.col][ghost.row - 1];}
            }else{
                if(!ghost.bottomWall){ghost= cells[ghost.col][ghost.row - 1];}
                else if(!ghost.rightWall){ghost= cells[ghost.col - 1][ghost.row];}
                else if(!ghost.leftWall){ghost= cells[ghost.col + 1][ghost.row];}
                else if(!ghost.topWall){ghost= cells[ghost.col][ghost.row + 1];}

            }
        }
        invalidate(); //i think this will like redraw everything?? its called after other chase methods
    }

    private void rotatePacRight() {
        switch(configure.getPacRes()) {
            case 1:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mrpacright);
                break;
            case 2:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mspacright);
                break;
            case 3:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.awarepacright);
                break;
        }
//        canvas.drawBitmap(pac,(player.col*cellSize)+(cellSize/4),(player.row*cellSize)+(cellSize/4),null);

    }

    private void rotatePacLeft() {
        switch(configure.getPacRes()) {
            case 1:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mrpacleft);
                break;
            case 2:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mspacleft);
                break;
            case 3:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.awarepacleft);
                break;
        }
//        gameCanvas.drawBitmap(pac,(player.col*cellSize)+(cellSize/4),(player.row*cellSize)+(cellSize/4),null);
    }

    private void rotatePacDown() {
        switch(configure.getPacRes()) {
            case 1:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mrpacdown);
                break;
            case 2:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mspacdown);
                break;
            case 3:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.awarepacdown);
                break;
        }
//        gameCanvas.drawBitmap(pac,(player.col*cellSize)+(cellSize/4),(player.row*cellSize)+(cellSize/4),null);

    }

    private void rotatePacUp() {
        switch(configure.getPacRes()) {
            case 1:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mrpacup);
                break;
            case 2:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.mspacup);
                break;
            case 3:
                pac=BitmapFactory.decodeResource(getResources(),R.drawable.awarepacup);
                break;
        }
//        gameCanvas.drawBitmap(pac,(player.col*cellSize)+(cellSize/4),(player.row*cellSize)+(cellSize/4),null);

    }

   
    

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_MOVE){
            float x =event.getX();
            float y = event.getY();
            float playerCenterX = hMar+(player.col)*cellSize;
            float playerCenterY = vMar+(player.row)*cellSize;
            float dx = x-playerCenterX;
            float dy = y-playerCenterY;
            float absDx= Math.abs(dx);
            float absDy =Math.abs(dy);
            if(absDx>cellSize||absDy>cellSize){
                if(absDx>absDy){
                    //movex
                    if(dx>0){
                        movePlayer(Direction.RIGHT);
                    }else {
                        //move left
                        movePlayer(Direction.LEFT);
                    }
                } else{
                    //movey
                    if(dy>0){

                        movePlayer(Direction.DOWN);
                    }else {

                        movePlayer(Direction.UP);
                    }
                }
            }
            return true;

        }
        return super.onTouchEvent(event);

    }


    
}
