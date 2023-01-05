package com.example.team27pac_man;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class configure extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static String playerName;
    private static int lives;
    private static String difficulty;
    private static int pacRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_configuration);

        Spinner spinner = (Spinner) findViewById(R.id.difficultySpinner);
        Spinner spriteSpinner = (Spinner) findViewById(R.id.spriteSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.difficultiesArray,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.spritesArray,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adapter2);
        spriteSpinner.setOnItemSelectedListener(this);

        TextView errorMessage = (TextView) findViewById(R.id.errorTextView);

        Button contButton = (Button) findViewById(R.id.startButton);
        Button startButton = (Button) findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName = (EditText) findViewById(R.id.nameEditText);
                if (editName.getText().toString().contains(" ")
                        || editName.getText().toString().isEmpty()
                        || editName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid name", Toast.LENGTH_LONG).show();

                    errorMessage.setTextColor(Color.RED);
                    errorMessage.setVisibility(View.VISIBLE);
                    contButton.setVisibility(View.GONE);
                    playerName = "Invalid input";
                } else {
                    errorMessage.setVisibility(View.GONE);
                    playerName = editName.getText().toString();

                    contButton.setVisibility(View.VISIBLE);
                    contButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            GameView.setLives(configure.getLives());
                            TextView difficultyText = (TextView) findViewById(R.id.difficultyTextView);
                            String str = ((difficultyText.getText().toString()).split(" "))[1];
                            Intent intent = new Intent(configure.this, Maze.class);
                            intent.putExtra("difficulty", str);
                            startActivity(intent);

                        }
                    });
                }
                TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
                nameTextView.setText("Player: " + playerName);
            }
        });

    }

    public static String getPlayerName() {
        return playerName;
    }

    public static int getLives(){
        if(difficulty.equals("Passive")){
            lives=6;
        }
        if(difficulty.equals("Easy")){
            lives=5;
        }
        if(difficulty.equals("Medium")){
            lives=4;
        }
        if(difficulty.equals("Hard")){
            lives=3;
        }
        if(difficulty.equals("Expert")){
            lives=2;
        }

        return lives;
    }

    public static String getDifficulty(){
        return difficulty;
    }

    public static int getPacRes() {
        return pacRes;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        switch (parent.getId()) {
            case R.id.difficultySpinner: {
                difficulty = parent.getItemAtPosition(pos).toString();
                Toast.makeText(parent.getContext(), difficulty, Toast.LENGTH_SHORT).show();
                TextView difficultyText = (TextView) findViewById(R.id.difficultyTextView);
                difficultyText.setText("Difficulty: " + difficulty);
            }
            case R.id.spriteSpinner: {
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                String text = parent.getItemAtPosition(pos).toString();
                if (text.equals("mrPacMan")) {
                    imageView.setImageResource(R.drawable.mr_pacman);
                    pacRes = 1;
                } else if (text.equals("msPacMan")) {
                    imageView.setImageResource(R.drawable.ms_pacman);
                    pacRes = 2;
                } else {
                    imageView.setImageResource(R.drawable.awarepac);
                    pacRes = 3;
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
