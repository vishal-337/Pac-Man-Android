package com.example.team27pac_man;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScoreIncrementTest {
    @Test
    public void checkScoreIncrement(){
        int score=10;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(11,newScore);
    }
    @Test
    public void ScoreIncrementWorkCheck(){
        int score=100;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(101,newScore);
    }
    @Test
    public void checkScoreIncrementNOT10(){
        int score=45;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(46,newScore);
    }
    @Test
    public void checkScoreIncrementNegative1(){
        int score=-2;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(0,newScore);
    }
    @Test
    public void checknegativetreat(){
        int score=-1;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(0,newScore);
    }
    @Test
    //as score can't be negative
    public void checkScoreIncrementNegative(){
        int score=-45;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(0,newScore);
    }
    @Test
    public void checkScoreIncrementOverflow(){
        int score=4500;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(0,newScore);
    }
    @Test
    public void checkScoreIncrementOverflow2(){
        int score=1000;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(0,newScore);
    }
    @Test
    public void checkScoreIncrementOverflow3(){
        int score=100000;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(0,newScore);
    }
    @Test
    public void checkScoreIncreemntboundary(){
        int score=999;
        int newScore=ScoreIncrement.scoreIncrement(score);
        assertEquals(1000,newScore);

    }
    //possible error 1
    @Test
    public void checkScoreIncreemntdifference(){
        int score=999;
        int newScore=ScoreIncrement.scoreIncrement(score);
        newScore=newScore-1;
        assertEquals(score,newScore);

    }
    //possible error 2
    @Test
    public void checkScoreIncreemntdifference2(){
        int score=0;
        int newScore=ScoreIncrement.scoreIncrement(score);
        newScore=newScore-1;
        assertEquals(score,newScore);

    }

}
