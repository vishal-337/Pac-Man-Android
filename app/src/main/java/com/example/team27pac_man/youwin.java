package com.example.team27pac_man;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class youwin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youwin);
        Button restart = (Button) findViewById(R.id.restart_button);
        Button quit = (Button) findViewById(R.id.end_button);
        TextView points =(TextView)findViewById(R.id.winpints);
        points.setText("Score: "+ GameView.getScore());
        TextView lives =(TextView)findViewById(R.id.lost);
        lives.setText("Lives Lost: "+ (configure.getLives()-GameView.getLives()));
        TextView kills =(TextView)findViewById(R.id.winkills);
        kills.setText("Ghosts Killed: "+ GameView.getkills());
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameView.setLives(configure.getLives());
                System.out.print(GameView.getLives());
                setContentView(R.layout.initial_configuration);
                Intent intent = new Intent(youwin.this, configure.class);
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