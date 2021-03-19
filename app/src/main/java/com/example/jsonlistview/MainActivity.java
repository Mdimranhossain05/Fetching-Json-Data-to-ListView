package com.example.jsonlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lsitViewID);
        JsonTask jsonTask = new JsonTask();
        jsonTask.execute();

    }

    private class JsonTask extends AsyncTask<String,String,List<DemoStudent>>{

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String jsonFile;

        @Override
        protected List<DemoStudent> doInBackground(String... strings) {
            try {
                URL url = new URL("https://jsonkeeper.com/b/KLZZ");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                StringBuffer stringBuffer = new StringBuffer();

                while ((line=bufferedReader.readLine())!=null){
                    stringBuffer.append(line);
                }
                jsonFile = stringBuffer.toString();
                JSONObject mainObject = new JSONObject(jsonFile);
                JSONArray studentArray = mainObject.getJSONArray("students");
                List<DemoStudent> demoStudentList = new ArrayList<DemoStudent>();

                for (int i=0; i<studentArray.length(); i++){
                    JSONObject ArrayObject = studentArray.getJSONObject(i);
                    DemoStudent student = new DemoStudent();
                    student.setName(ArrayObject.getString("name"));
                    student.setDepartment(ArrayObject.getString("department"));
                    student.setCountry(ArrayObject.getString("country"));
                    demoStudentList.add(student);
                }

                return demoStudentList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                try {
                    httpURLConnection.disconnect();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<DemoStudent> s) {
            super.onPostExecute(s);

            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),R.layout.sample,s);
            listView.setAdapter(customAdapter);

        }
    }

}