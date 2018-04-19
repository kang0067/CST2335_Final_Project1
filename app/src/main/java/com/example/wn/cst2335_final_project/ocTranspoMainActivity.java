package com.example.wn.cst2335_final_project;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ocTranspoMainActivity extends AppCompatActivity {

    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oc_transpo);

        //set the toolbar
        Toolbar theToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(theToolbar);


        //Button help = (Button) findViewById(R.id.button2);
        //     Button route = (Button)findViewById(R.id.button);
        Button stop = (Button) findViewById(R.id.stops);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stops = new Intent(ocTranspoMainActivity.this, StopsActivity.class);
                startActivity(stops);
            }
        });
//        help.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent OCTMain = new Intent(ocTranspoMainActivity.this, helpInfoActivity.class);
//                AlertDialog alertDialog = new AlertDialog.Builder(ocTranspoMainActivity.this).create();
//                alertDialog.setTitle("Alert Dialog");
//                alertDialog.setMessage("Are you sure you want to go to this page？.");
//                startActivity(OCTMain);
//
//            }
//        });
//        Button route = (Button) findViewById(R.id.Route);
//
//route.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        Intent intent = new Intent(ocTranspoMainActivity.this,RouteActivity.class);
//        startActivity(intent);
//    }
//});

    }
    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        mi.getItemId();
        switch(mi.getItemId()){

            case R.id.help:
                Log.d("Toolbar", "Item selected");
                AlertDialog.Builder build = new AlertDialog.Builder(ocTranspoMainActivity.this);
                LayoutInflater inflater = ocTranspoMainActivity.this.getLayoutInflater();
                View d = inflater.inflate(R.layout.activity_csutom_dialog_, null);
                final EditText newMessage =  d.findViewById(R.id.newMessage) ;
                build.setView(d)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                              //  message = newMessage.getText().toString();
                                Intent intent = new Intent(ocTranspoMainActivity.this,helpInfoActivity.class);
                                startActivity(intent);
                            }
                        });
                build.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("User clicked", "Cancel Button");
                    }
                });
                build.show();
                break;

        }
        return true;
    }

}