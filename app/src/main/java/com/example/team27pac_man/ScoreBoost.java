package com.example.team27pac_man;

public class ScoreBoost {
    public static int scoreBoost(int score) {
        if (score >= 0 && score<1000) {
            score += 10;
            return score;
        } else {
            return 0;
        }
    }
}
