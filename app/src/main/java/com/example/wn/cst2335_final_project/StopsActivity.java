package com.example.wn.cst2335_final_project;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

public class StopsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "StopsActivity";
    private ArrayList<String> routeMsg;
  //  private ChatAdapter messageAdapter;
    private RouteDatabaseHelper cdh;
    private static SQLiteDatabase sld;
    private ContentValues cv;
    private Cursor cursor;
    private ListView listView;
    private EditText editT;
    private Button sendBut;
    ChatAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);
        listView = findViewById(R.id.stoplistview);
        listView.setBackgroundColor(Color.GRAY);
        editT=findViewById(R.id.routetext);
        sendBut = findViewById(R.id.selectstop);
        routeMsg = new ArrayList<>();
        //abt database stuff below
        cdh = new RouteDatabaseHelper(getApplicationContext());
        sld = cdh.getWritableDatabase();
        cv = new ContentValues();
        String str = "select "+ RouteDatabaseHelper.KEY_MESSAGE + " from " + RouteDatabaseHelper.TB_NAME;
        cursor = sld.rawQuery(str, null);

        int colIndex = cursor.getColumnIndex( RouteDatabaseHelper.KEY_MESSAGE );

        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            String s = cursor.getString(colIndex);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " +s);
            routeMsg.add(s);
            cursor.moveToNext();
        }

        int n = cursor.getColumnCount();
        Log.i(ACTIVITY_NAME, "Cursor's column count="+n);
        for (int i=0; i<n; i++)
            Log.i(ACTIVITY_NAME,cursor.getColumnName(colIndex));

        //in this case, “this” is the StopsActivity, which is-A Context object
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //    Toast.makeText(getApplicationContext(), "你選擇的是" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), RouteActivity.class);

               //put data into bundle
                Bundle getinfo = new Bundle();
                getinfo.putString("routeMsg", routeMsg.get(i));//压入数据
                intent.putExtras(getinfo);
                //start acitvity
                 startActivity(intent);
            }
        });

        //listView long click delete msg event
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                //when long click , then the message poped up
                AlertDialog.Builder builder=new AlertDialog.Builder(StopsActivity.this);
                builder.setMessage("Confirm Delete??");
                builder.setTitle("Alarm!");

                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("Yes,sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(routeMsg.remove(position)!=null){
                            System.out.println("success");
                        }else {
                            System.out.println("failed");
                        }
                        listView.setAdapter(messageAdapter);
                        messageAdapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "Delete this item", Toast.LENGTH_SHORT).show();
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("NO!", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return false;
            }
        });



        //click into save
        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editT.getText().toString();
                routeMsg.add(content);
                cv.put(RouteDatabaseHelper.KEY_MESSAGE, content);
                sld.insert(RouteDatabaseHelper.TB_NAME, "NullCol", cv);
                listView.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
                editT.setText("");
            }
        });

    }

        private class ChatAdapter extends ArrayAdapter<String> {
            public ChatAdapter(Context ctx) {
                super(ctx, 0);
            }

            public int getCount(){
                return routeMsg.size();
            }

            public String getItem(int position){
                return routeMsg.get(position);
            }

            public View getView(int position, View convertView, ViewGroup parent){
                LayoutInflater inflater = StopsActivity.this.getLayoutInflater();
                View result = null ;
                if(position%2 == 0)
                    result = inflater.inflate(R.layout.stop_row_outgoing, null);
                else
                    result = inflater.inflate(R.layout.stop_row_outgoing, null);
                TextView message = (TextView)result.findViewById(R.id.stopsmsg);
                message.setText(getItem(position)); // get the string at position
                return result;
            }

            public long getId(int position){
                return position;
            }

        }

    public void onDestroy(){
        super.onDestroy();


        cursor.close();
        cdh.close();
        sld.close();
    }

}


