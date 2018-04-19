package com.example.wn.cst2335_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button startOCTranspo = (Button) findViewById(R.id.startOCTranspo);
        startOCTranspo.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View v) {
                                                  Intent OCTMain = new Intent(MainMenu.this, ocTranspoMainActivity.class);
                                                  startActivity(OCTMain);

                                              }
                                          })
    ;}
    }


/*
        Button startQuiz = (Button) findViewById(R.id.startQuizApp);
        startQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent quizMain = new Intent (MainMenu.this, QuizMain.class);
                startActivity(quizMain);
            }
        });

        Button startPatient = (Button) findViewById(R.id.startPatientForm);
        startPatient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent patientMain = new Intent (MainMenu.this, PatientMain.class);
                startActivity(patientMain);
            }
        });


        Button startMovie = (Button) findViewById(R.id.startMovie);
        startMovie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent startMovie = new Intent (MainMenu.this, MovieMain.class);
                startActivity(startMovie);
            }
        });



            }
        });

*/






