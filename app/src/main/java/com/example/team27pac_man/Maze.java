package com.example.team27pac_man;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.util.DisplayMetrics;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //final TextView score = (TextView) findViewById(R.id.scoreTextView);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maze);
        TextView playerName = (TextView) findViewById(R.id.nameTextView);
        TextView score = (TextView) findViewById(R.id.scoreTextView);
        TextView lives = (TextView) findViewById(R.id.livesTextView);
        TextView difficulty = (TextView) findViewById(R.id.difficultyTextView);
        playerName.setText("Player name: "+ configure.getPlayerName());
        lives.setText("Lives: "+ configure.getLives());
        difficulty.setText("Difficulty: " + configure.getDifficulty());
        score.setText("score:"+0);
        GameView.setScore(0);
    }

//    public void updateScore(String text, TextView score) {
//        score.setText("Score: " +  text);
//        score.invalidate();
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
