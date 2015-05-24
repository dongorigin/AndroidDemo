package cn.dong.demo.ui.creation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.L;
import cn.dong.demo.util.ScrollUtils;
import cn.dong.demo.view.DRecyclerView;

/**
 * 滚动控件研究
 *
 * @author dong on 15/2/13.
 */
public class FadeoutHeaderRecyclerViewActivity extends BaseActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recycler)
    DRecyclerView mRecyclerView;
    View headerView;

    NumberedAdapter adapter;

    private int mHeaderHeight;


    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_scroll;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);

        toolbar.setBackgroundColor(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        headerView = LayoutInflater.from(this).inflate(R.layout.activity_scroll_header, null);
        adapter = new NumberedAdapter(0, headerView);

        mRecyclerView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? mRecyclerView.getSpanCount() : 1;
            }
        });

        mRecyclerView.setColorSchemeResources(android.R.color.holo_blue_light);
        mRecyclerView.setOnLoadListener(new DRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                L.d(TAG, "onRefresh");
                delayAction(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addHeader(5);
                        adapter.notifyDataSetChanged();
                        mRecyclerView.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onLoadMore() {
                L.d(TAG, "onLoadMore");
                delayAction(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter.getItemCount() < 50) {
                            mRecyclerView.setLoading(false);
                            adapter.add(6);
                            adapter.notifyItemRangeChanged(adapter.getItemCount() - 6, 6);
                        } else {
                            mRecyclerView.promptEnd();
                        }
                    }
                });
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollY;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mRecyclerView.getLayoutManager().findFirstCompletelyVisibleItemPosition() == 0) {
                    scrollY = 0;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollY += dy;
                L.d(TAG, "onScrolled dx=%d dy=%d scrollY=%d", dx, dy, scrollY);
                float alpha = (float) scrollY / mHeaderHeight;
                int color = ScrollUtils.getColorWithAlpha(alpha, getResources().getColor(R.color.primary));
                toolbar.setBackgroundColor(color);
                headerView.setTranslationY(scrollY / 2);
            }
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.promptLoading();
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.add(20);
                adapter.notifyDataSetChanged();

                mRecyclerView.setLoadMoreEnable(true);
            }
        }, 2000);
    }

    private void delayAction(Runnable action) {
        mRecyclerView.postDelayed(action, 1500);
    }

}
