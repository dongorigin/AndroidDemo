package cn.dong.demo.ui.sensor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cn.dong.demo.R;
import cn.dong.demo.model.GeoResponse;
import cn.dong.demo.model.GeoResult;
import cn.dong.demo.model.GeoResult.Location;
import cn.dong.demo.ui.common.BaseActivity;
import cz.msebera.android.httpclient.Header;

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
                startGeo("beijing");
            }
        });
    }

    private void startGeo(String city) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(GEO_API + city, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                GeoResponse geoResponse = new Gson().fromJson(responseString, GeoResponse.class);
                if (geoResponse != null && geoResponse.getStatus().equals("OK") && geoResponse.getResults().size() > 0) {
                    GeoResult geoResult = geoResponse.getResults().get(0);
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
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }

}
