package com.example.user.json_parsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private WebView webView;
    private ProgressDialog progressDialog;
    private ListView listView;
    private static String url = "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=6681f674a47543a8b53fb7e4a7482d2f";
    ArrayList<HashMap<String, String>> contactList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
        new GetContacts().execute();

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait . . . ");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            fetchData fh = new fetchData();
            String jsonStr = fh.makeServiceCall(url);
            Log.e(TAG, "Response from URL: " + jsonStr);

            if (jsonStr != null) {


                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    JSONArray articles = jsonObject.getJSONArray("articles");
                   // JSONArray company =  contacts.getJSONArray("company");
                    ///for(int i=0;i<friends.length();i++)
                   /// JSONObject company = friends.getJSONObject("company");
                  ///  JSONObject phone = friends.getJSONObject("company");


                    for (int i = 0; i < articles.length(); i++) {
                       JSONObject c = articles.getJSONObject(i);
                       // String id = c.getString("id");
                        String author = c.getString("author");
                        String title = c.getString("title");
                        String description = c.getString("description");
                        String url = c.getString("url");

                        ///String content = c.getString("content");
                        ///phone node in jsonobj
                      //  JSONObject company = contacts.getJSONObject("company");
                        //String mobile = phone.getString("mobile");
                       // String home = phone.getString("home");
                       // String office = phone.getString("office");
                       // JSONObject  ph = company.getJSONObject("phone");
                        ///  String name = friends.getString("name");

                       // JSONObject con= ph.getJSONObject("contacts");

                       // JSONArray out=



                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();
                        contact.put("author",author);
                        contact.put("title",title);
                        contact.put(" description", description);
                        contact.put("url",url);
                       // contact.put("content",content);

                        // adding each child node to HashMap key => value
                        //contact.put("id", id);
                        //contact.put("name", name);
                       // contact.put("email", email);
                        //contact.put("mobile", mobile);
                       // contact.put("gender",gender);

                        // adding contact to contact list
                        contactList.add(contact);

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from the server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from the server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList, R.layout.list_item, new String[]{"author","title"," description","url"}, new int[]{R.id.author,R.id.title,R.id.description,R.id.url});
            listView.setAdapter(adapter);
        }
/// public void execute() {
        //}
        // }
    }
}
