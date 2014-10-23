package cn.dong.demo.ui.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;
import cn.dong.demo.model.GeoResponse;
import cn.dong.demo.model.GeoResult;
import cn.dong.demo.model.GeoResult.Location;
import cn.dong.demo.util.T;

import com.google.gson.Gson;

/**
 * 地址解析
 * 
 * @author dong 2014-8-23
 */
public class GeocoderActivity extends BaseActivity {
    private static final String GEO_API =
            "http://maps.googleapis.com/maps/api/geocode/json?address=";

    private EditText addressView;
    private Button parseButton;
    private TextView resultView;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_geocoder;
    }

    @Override
    protected void initPageView() {
        addressView = (EditText) findViewById(R.id.address);
        parseButton = (Button) findViewById(R.id.parse_button);
        resultView = (TextView) findViewById(R.id.result);
    }

    @Override
    protected void initPageViewListener() {
        parseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new GeocodingTask().execute("beijing");
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

    }

    private class GeocodingTask extends AsyncTask<String, Void, GeoResponse> {

        @Override
        protected GeoResponse doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            GeoResponse geoResponse = null;
            try {
                HttpResponse response = httpClient.execute(new HttpGet(GEO_API + params[0]));
                HttpEntity entity = response.getEntity();
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(entity.getContent()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                String responseStr = sb.toString();
                Gson gson = new Gson();
                geoResponse = gson.fromJson(responseStr, GeoResponse.class);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return geoResponse;
        }

        @Override
        protected void onPostExecute(GeoResponse result) {
            if (result != null && result.getStatus().equals("OK") && result.getResults().size() > 0) {
                GeoResult geoResult = result.getResults().get(0);
                if (geoResult != null) {
                    Location location = geoResult.getGeometry().getLocation();
                    // 你要的东西在这里
                    double lat = location.getLat();
                    double lng = location.getLng();
                    Log.i("geo", "lat=" + lat + ", lng=" + lng);
                }
            } else {
                // 解析失败
                Log.i("geo", "error");
            }
        }
    }
}
