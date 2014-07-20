package cn.dong.demo.ui.image;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.dong.demo.Constants;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoaderActivity extends BaseActivity {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private ListView listView;
    private String[] imageUrls;

    @Override
    protected int initPageLayoutID() {
        return R.layout.imageloader_activity;
    }

    @Override
    protected void initPageView() {
        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    protected void initPageViewListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        options =
                new DisplayImageOptions.Builder().resetViewBeforeLoading(true)
                        // .showImageOnLoading(R.drawable.ic_stub)
                        .cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
                        .displayer(new FadeInBitmapDisplayer(300)).build();

        imageUrls = Constants.IMAGES;

        listView.setAdapter(new ImageAdapter());
    }

    class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return imageUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView =
                        getLayoutInflater().inflate(R.layout.activity_imageloader_item, parent,
                                false);
            }
            TextView text = (TextView) convertView.findViewById(R.id.text);
            text.setText("item " + position);
            ImageView image = (ImageView) convertView.findViewById(R.id.image);
            imageLoader.displayImage(imageUrls[position], image, options);
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_clear_memory_cache:
                imageLoader.clearMemoryCache();
                return true;
            case R.id.item_clear_disc_cache:
                imageLoader.clearDiscCache();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
