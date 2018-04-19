package com.example.wn.cst2335_final_project;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {
        protected static final String ACTIVITY_NAME = "RouteActivity";
        String number;
        ProgressBar progressBar;
        TextView stopDescription;
        TextView stopID;
        ListView busListView;
        String stopName;
        String stopNumber;
        Bundle getinfo = new Bundle();
        ArrayList<String> resultArr;
        ArrayList<String> routeArr = new ArrayList<>();
        ArrayList<String> directionArr = new ArrayList<>();

    @Override
       protected void onCreate(Bundle savedInstanceState) {
         //init
       super.onCreate(savedInstanceState);

        //routeFragment theFragment = new routeFragment();
        //fragmentTransaction.replace(android.R.id.content, theFragment);
        setContentView(R.layout.activity_route);
       Log.i(ACTIVITY_NAME,"This is "+ACTIVITY_NAME);
        progressBar = findViewById(R.id.progressBar);
        getFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new blank()).commit();

        //declaration
        busListView = findViewById(R.id.buslistview);
        stopDescription=findViewById(R.id.stopName);
        stopID = findViewById(R.id.stopId);

           //bundle from stops activity ,get the stop number to ...

         getinfo= this.getIntent().getExtras();
         number =getinfo.getString("routeMsg");


        //Start the AsyncTask thread
        taskRunner runner = new taskRunner();
        //the OCtransport  which using the api from octransport
        String urlString ="https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";
        //execute the asynctask
        Log.i(ACTIVITY_NAME,"Execute the asynctask thread");
        runner.execute(urlString);

    }
         //asynctask function
        //https://www.journaldev.com/9708/android-asynctask-example-tutorial
        class taskRunner extends AsyncTask<String, Integer, String> {
        protected void onProgressUpdate(Integer... value) {

        }

        protected void onPostExecute(String result) {
           //stopDescription.setText(stopName);
          //  routeadapter.notifyDataSetChanged();

           super.onPostExecute(result);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(RouteActivity.this,android.R.layout.simple_list_item_activated_1,resultArr);
            busListView.setAdapter(adapter);
            progressBar.setProgress(100);
            progressBar.setVisibility(View.INVISIBLE);
            busListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    routeFragment myFragment = new routeFragment();
                    //bind the data
                    Bundle data = new Bundle();
                    data.putString("number",number);
                    data.putString("route",routeArr.get(i));
                    data.putString("direction",directionArr.get(i));
                    myFragment.setArguments(data);
                    getFragmentManager().beginTransaction().replace(R.id.fragmentHolder,myFragment).commit();

                    Log.i(ACTIVITY_NAME,"LIST VIEW CLICKED "+i);
                }
            });
    }


           @Override
           protected String doInBackground(String... args) {
               HttpURLConnection conn = null;
               String inputURL = args[0];

               try {
                   resultArr = downloadInfo(conn,inputURL);
                   }catch (IOException ex){
                    Log.i(ACTIVITY_NAME,"IO exception");
               }
              return null;
           }

           /*
           * The method to connect with web server
           * */
           private ArrayList<String> downloadInfo(HttpURLConnection conn,String inputURL) throws IOException{
            ArrayList<String> resultArr = new ArrayList<>();
            URL url;
            InputStream resultStream = null;

               try {

                   url = new URL(inputURL+number);
                   Log.i(ACTIVITY_NAME,"URL: host is "+url.getHost()+"number is :"+number);

                   conn = (HttpURLConnection) url.openConnection();
                   conn.setReadTimeout(10000 /* milliseconds */);
                   conn.setConnectTimeout(15000 /* milliseconds */);
                   //Start the connection with webserver
                   conn.connect();
                   progressBar.setProgress(10);
                   //get the response code
                   //https://developer.android.com/training/basics/network-ops/connecting.html
                   int responseCode = conn.getResponseCode();

                   //handle the connection error
                   if (responseCode != HttpURLConnection.HTTP_OK) {
                       throw new IOException("HTTP error code: " + responseCode);
                   }
                    //get the output stream
                   resultStream = conn.getInputStream();
                   if(resultStream != null){
                       progressBar.setProgress(30);
                       resultArr = readStream(resultStream);
                   }
                   //Log.i(ACTIVITY_NAME,"OCTRANSPORT RESPONSE "+ result);

               } catch (MalformedURLException e) {
               }
               catch (IOException e) {
               } finally {
                   // Close Stream and disconnect HTTPS connection.
                   if (resultStream != null) {
                       resultStream.close();
                   }
                   if (resultStream != null) {
                       conn.disconnect();
                   }
               }
            return resultArr;

           }

           private ArrayList<String> readStream(InputStream resultStream){

               ArrayList <String> result = new ArrayList<>();
               String routeId = null;
               String direction = null;

               try {
                   XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                   factory.setNamespaceAware(false);
                   XmlPullParser parser = factory.newPullParser();
                   parser.setInput(resultStream, "UTF-8");
                   progressBar.setProgress(70);
                   //start read the stream
                   while((parser.getEventType())!= XmlPullParser.END_DOCUMENT){
                        if(parser.getEventType() == XmlPullParser.START_TAG){

                            String tag = parser.getName();
                            if(tag.equalsIgnoreCase("stopdescription")){
                                stopName = parser.nextText();
                                Log.i("Stream Reader","Stop Name: "+stopName);
                            }else if(tag.equalsIgnoreCase("stopno")){
                                stopNumber = parser.nextText();
                                Log.i("Stream Reader","Stop Number: "+stopNumber);
                            }else if(tag.equalsIgnoreCase("routeno")){
                                routeId = parser.nextText();
                                routeArr.add(routeId);
                                Log.i("Stream Reader","Route Number: "+routeId);
                            }else if(tag.equalsIgnoreCase("direction")){
                                direction = parser.nextText();
                                directionArr.add(direction);
                                Log.i("Stream Reader","Direction: "+direction);
                            }

                        }

                       parser.next();
                   }

                   //Load information information into view
                   if (routeArr.size() == directionArr.size()){
                       for (int i = 0; i<routeArr.size();i++){
                           result.add(routeArr.get(i)+" --> "+directionArr.get(i));
                       }
                   }
                   stopID.setText(stopNumber);
                   stopDescription.setText(stopName);
               }catch(Exception ex){
                   ex.printStackTrace();
                   //Log.i(ACTIVITY_NAME,"STREAM reader exception");
               }
               // Log.i(ACTIVITY_NAME,"Stream reader result "+null);


            return result;
           }



}

       }
