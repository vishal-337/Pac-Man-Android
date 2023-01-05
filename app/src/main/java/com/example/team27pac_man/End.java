package com.example.team27pac_man;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class End extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end);
        TextView points =(TextView)findViewById(R.id.points);
        points.setText("Score: "+ GameView.getScore());
        TextView lives =(TextView)findViewById(R.id.Liveslost);
        lives.setText("Lives Lost: "+ configure.getLives());
        TextView kills =(TextView)findViewById(R.id.ghostkills);
        kills.setText("Ghosts killed: "+ GameView.getkills());
        Button restart = (Button) findViewById(R.id.restart_button);
        Button quit = (Button) findViewById(R.id.end_button);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameView.setLives(configure.getLives());
                System.out.print(GameView.getLives());
                setContentView(R.layout.initial_configuration);
                Intent intent = new Intent(End.this, configure.class);
                startActivity(intent);
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
                finishAffinity();
                //System.exit(0);

            }
        });
    }
}
