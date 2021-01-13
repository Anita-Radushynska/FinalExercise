package pl.chi.finalhw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChannelsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);

        //Assign variable
        listView = findViewById(R.id.listCannels);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        arrayList.get(position), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChannelsActivity.this, VideosActivity.class));
            }
        });

        OkHttpClient client = new OkHttpClient();
        String url = "https://api.screenlife.com/api/get-channels";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                finish();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String channel_array = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(channel_array);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String channelID = object.getString("id");
                            String title = object.getString("title");
                            arrayList.add(channelID + " " + title );
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Initialize Array Adapter
                                arrayAdapter = new ArrayAdapter<String>(ChannelsActivity.this,
                                        android.R.layout.simple_list_item_1, arrayList);
                                // Set Array Adapter to ListView
                                listView.setAdapter(arrayAdapter);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


}