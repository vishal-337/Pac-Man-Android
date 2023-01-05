package com.example.team27pac_man;

public class ScoreIncrement {
    public static int scoreIncrement(int score) {
        if (score >= 0 && score < 1000) {
            score++;
            return score;
        } else {
            return 0;
        }

    }
    }

