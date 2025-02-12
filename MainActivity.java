package com.example.kolko_krzyz;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String player = "kolko";
    int count = 0;
    String[][] pola = {
            {"1","1","1"},
            {"1","1","1"},
            {"1","1","1"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init_button();

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 2);
        }

    }

    void init_button(){
        ImageButton button_0 = findViewById(R.id.button_0);
        ImageButton button_1 = findViewById(R.id.button_1);
        ImageButton button_2 = findViewById(R.id.button_2);
        ImageButton button_4 = findViewById(R.id.button_4);
        ImageButton button_5 = findViewById(R.id.button_5);
        ImageButton button_6 = findViewById(R.id.button_6);
        ImageButton button_7 = findViewById(R.id.button_7);
        ImageButton button_8 = findViewById(R.id.button_8);
        ImageButton button_9 = findViewById(R.id.button_9);

        List<ImageButton> przyciski= new ArrayList<ImageButton>();
        przyciski.add(button_0);
        przyciski.add(button_1);
        przyciski.add(button_2);
        przyciski.add(button_4);
        przyciski.add(button_5);
        przyciski.add(button_6);
        przyciski.add(button_7);
        przyciski.add(button_8);
        przyciski.add(button_9);

        przyciski.forEach(imageButton -> {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String content = imageButton.getContentDescription().toString();
                    int row = Integer.parseInt(Character.toString(content.charAt(0)));
                    int column = Integer.parseInt(Character.toString(content.charAt(1)));
                    System.out.println(row);
                    System.out.println(column);

                    if(player == "kolko"){
                        imageButton.setImageResource(R.drawable.kolko);
                        pola[row][column] = "O";
                        count+=1;
                        check_if_win();
                        player = "krzyzyk";
                        updateGracz();
                        return;
                    }
                    imageButton.setImageResource(R.drawable.krzyzyk);
                    pola[row][column] = "X";
                    check_if_win();
                    player = "kolko";
                    updateGracz();
                    count+=1;
                }
            });
        });
    }

    void check_if_win(){
        boolean if_win = false;

        if(count == 9){
            player="remis";
            if_win = true;
        }
        for(int i=0;i<3;i++){
            if(pola[i][0]=="1"){
                continue;
            }
            if(pola[i][0] == pola[i][1] && pola[i][1] == pola[i][2]){
                if_win = true;
            }
        }

        for(int i=0;i<3;i++){
            if(pola[0][i]=="1"){
                continue;
            }
            if(pola[0][i] == pola[1][i] && pola[1][i] == pola[2][i]){
                if_win = true;
            }
        }

        if(pola[0][0] == pola[1][1] && pola[1][1] == pola[2][2] && pola[1][1]!="1"){
            if_win = true;
        }

        else if(pola[0][2] == pola[1][1] && pola[1][1] == pola[2][0] && pola[1][1]!="1"){
            if_win = true;
        }

        if(!if_win){
            return;
        }
        Intent intent = new Intent(this,ScreenActivity.class);
        intent.putExtra("player",player);
        startActivity(intent);
    }

    void updateGracz(){
        TextView pole = findViewById(R.id.ruch);
        pole.setText("Gra teraz :"+player);
    }
}