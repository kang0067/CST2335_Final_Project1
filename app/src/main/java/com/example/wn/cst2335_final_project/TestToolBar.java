/*
package com.example.wn.cst2335_final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TestToolBar extends AppCompatActivity {
    String message = "FBI Warning:You are looking some sensetive information!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oc_transpo);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
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
                AlertDialog.Builder build = new AlertDialog.Builder(TestToolBar.this);
                LayoutInflater inflater = TestToolBar.this.getLayoutInflater();
                View d = inflater.inflate(R.layout.activity_csutom_dialog_, null);
                final EditText newMessage =  d.findViewById(R.id.newMessage) ;
                build.setView(d)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                message = newMessage.getText().toString();
                            }
                        });
                build.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                build.show();
                break;

        }
        return true;
    }
}


*/
