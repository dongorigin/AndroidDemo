package cn.dong.demo.ui.original;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    @InjectView(R.id.recycler)
    DRecyclerView mDRecyclerView;
    View headerView;

    NumberedAdapter adapter;

    private int mHeaderHeight;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_fadeoutheader;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        getActionBarToolbar().setBackgroundColor(Color.TRANSPARENT);

        headerView = LayoutInflater.from(this).inflate(R.layout.activity_scroll_header, null);
        adapter = new NumberedAdapter(0, headerView);

        mDRecyclerView.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? mDRecyclerView.getSpanCount() : 1;
            }
        });

        mDRecyclerView.setColorSchemeResources(android.R.color.holo_blue_light);
        mDRecyclerView.setOnLoadListener(new DRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                L.d(TAG, "onRefresh");
                delayAction(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addHeader(5);
                        adapter.notifyDataSetChanged();
                        mDRecyclerView.setRefreshing(false);
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
                            mDRecyclerView.setLoading(false);
                            adapter.add(10);
                            adapter.notifyItemRangeChanged(adapter.getItemCount() - 10, 10);
                        } else {
                            mDRecyclerView.promptEnd();
                        }
                    }
                });
            }
        });

        mDRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollY;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mDRecyclerView.getLayoutManager().findFirstCompletelyVisibleItemPosition() == 0) {
                    scrollY = 0;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollY += dy;
                L.d(TAG, "onScrolled dx=%d dy=%d scrollY=%d", dx, dy, scrollY);
                float alpha = (float) scrollY / mHeaderHeight;
                int color = ScrollUtils.getColorWithAlpha(alpha, getResources().getColor(R.color.primary));
                getActionBarToolbar().setBackgroundColor(color);
                headerView.setTranslationY(scrollY / 2);
            }
        });

        mDRecyclerView.setAdapter(adapter);
        mDRecyclerView.promptLoading();
        mDRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.add(20);
                adapter.notifyDataSetChanged();

                mDRecyclerView.setLoadMoreEnable(true);
            }
        }, 2000);
    }

    private void delayAction(Runnable action) {
        mDRecyclerView.postDelayed(action, 1500);
    }

}
