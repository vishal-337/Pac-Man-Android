package com.example.team27pac_man;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScoreBoostTest {
    @Test
    public void checkScoreBoost(){
        int score=10;
        int newScore=ScoreBoost.scoreBoost(score);
        assertEquals(20,newScore);
    }
    @Test
    public void ScoreBoostWorkCheck(){
        int score=100;
        int newScore=ScoreBoost.scoreBoost(score);
        assertEquals(110,newScore);
    }
    @Test
    public void checkScoreBoostNOT10(){
        int score=45;
        int newScore=ScoreBoost.scoreBoost(score);
        assertEquals(55,newScore);
    }
    @Test
    public void checknegativetreat(){
    int score=-1;
        int newScore=ScoreBoost.scoreBoost(score);
        assertEquals(0,newScore);
    }
    @Test
    //as score can't be negative
    public void checkScoreBoostNegative(){
        int score=-45;
        int newScore=ScoreBoost.scoreBoost(score);
        assertEquals(0,newScore);
    }
    @Test
    public void checkScoreBoostOverflow(){
        int score=4500;
        int newScore=ScoreBoost.scoreBoost(score);
        assertEquals(0,newScore);
    }
    @Test
    public void checkScoreBoostOverflow2(){
        int score=1000;
        int newScore=ScoreBoost.scoreBoost(score);
        assertEquals(0,newScore);
    }
    @Test
    public void checkScoreBoostOverflow3(){
        int score=100000;
        int newScore=ScoreBoost.scoreBoost(score);
        assertEquals(0,newScore);
    }
    @Test
    public void checkboundary(){
        int score=999;
        int newScore=ScoreBoost.scoreBoost(score);
        assertEquals(1009,newScore);

    }
    //possible error 1
    @Test
    public void checkscoredifference(){
        int score=999;
        int newScore=ScoreBoost.scoreBoost(score);
        newScore=newScore-10;
        assertEquals(score,newScore);

    }
    //possible error 2
    @Test
    public void checkscoredifference2(){
        int score=0;
        int newScore=ScoreBoost.scoreBoost(score);
        newScore=newScore-10;
        assertEquals(score,newScore);

    }



}
