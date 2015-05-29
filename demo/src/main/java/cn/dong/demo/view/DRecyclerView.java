package cn.dong.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.dong.demo.R;

/**
 * 增加下拉刷新与加载更多功能的RecyclerView
 *
 * @author dong on 15/3/4.
 */
public class DRecyclerView extends FrameLayout {
    private static final String TAG = DRecyclerView.class.getSimpleName();
    private static final int DEFAULT_SPAN_COUNT = 1;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    // todo 还不清楚GridLayoutManager与LinearLayoutManager的性能差别。假如性能有区别，之后需要根据SpanCount使用不同的LayoutManager
    private GridLayoutManager mLayoutManager;
    private HeaderAdapter mAdapter;
    private OnLoadListener mListener;
    private RecyclerView.OnScrollListener mScrollListener; // user's scroll listener

    // load more
    private boolean mEnableLoadMore = true; // 是否开启列表加载更多功能
    private boolean mEnableAutoLoadMore = true; // 是否开启列表到底自动加载更多
    private boolean mLoading = false; // 是否加载中
    //
    private DRecyclerViewPrompt mPromptView;

    public DRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }

        initView();
        parseAttrs(attrs);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.d_recycler_layout, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.d_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.d_recycler);

        // swipe refresh
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mListener != null) {
                    mListener.onRefresh();
                }
            }
        });

        // recycler
        mLayoutManager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mScrollListener != null) {
                    mScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mScrollListener != null) {
                    mScrollListener.onScrolled(recyclerView, dx, dy);
                }
                if (mEnableLoadMore && mEnableAutoLoadMore && !mLoading && mLayoutManager.findLastVisibleItemPosition() == mLayoutManager.getItemCount() - 1) {
                    // 开启加载更多，并且列表滑动到最后一项
                    performLoadMore();
                }
            }
        });

        // load more
        mPromptView = new DRecyclerViewPrompt(getContext());
        mPromptView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEnableLoadMore && !mLoading) {
                    performLoadMore();
                }
            }
        });
    }

    private void parseAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DRecyclerView);

        int spanCount = a.getInt(R.styleable.DRecyclerView_dSpanCount, 1);
        setSpanCount(spanCount);

        int overScrollMode = a.getInt(R.styleable.DRecyclerView_android_overScrollMode, OVER_SCROLL_NEVER);
        setRecyclerOverScrollMode(overScrollMode);

        a.recycle();
    }

    public interface OnLoadListener {
        void onRefresh();

        void onLoadMore();
    }

    public void setOnLoadListener(OnLoadListener listener) {
        mListener = listener;
    }

    public void setRecyclerOverScrollMode(int overScrollMode) {
        mRecyclerView.setOverScrollMode(overScrollMode);
    }

    /**
     * 设置跨度数量
     */
    public void setSpanCount(int spanCount) {
        mLayoutManager.setSpanCount(spanCount);
    }

    public int getSpanCount() {
        return mLayoutManager.getSpanCount();
    }

    /**
     * 设置跨度大小查找器，提供每项的宽度数据
     */
    public void setSpanSizeLookup(final GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == HeaderAdapter.ITEM_VIEW_TYPE_HEADER_OR_FOOTER ? mLayoutManager.getSpanCount() : spanSizeLookup.getSpanSize(position);
            }
        });
    }

    /**
     * 设置Adapter，Adapter将被内部类引用来提供功能
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("adapter not null");
        }
        mAdapter = new HeaderAdapter(adapter, mPromptView);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setAdapterHasStableIds(boolean hasStableIds) {
        if (mAdapter != null) {
            mAdapter.setHasStableIds(hasStableIds);
        }
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        mScrollListener = listener;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void setRefreshEnabled(boolean enabled) {
        mSwipeRefreshLayout.setEnabled(enabled);
    }

    public void setColorSchemeColors(int... colors) {
        mSwipeRefreshLayout.setColorSchemeColors(colors);
    }

    public void setColorSchemeResources(int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
        if (!mEnableAutoLoadMore) {
            setFooterLoading(loading);
        }
    }

    public void promptEmpty(String prompt) {
        mEnableLoadMore = false;
        mLoading = false;
        mPromptView.empty(prompt);
    }

    public void promptLoading() {
        mEnableLoadMore = false;
        mLoading = false;
        mPromptView.loading();
    }

    public void promptEnd() {
        mEnableLoadMore = false;
        mLoading = false;
        mPromptView.moreEnd();
    }

    /**
     * 设置加载更多提示
     *
     * @param loading
     */
    private void setFooterLoading(boolean loading) {
        if (loading) {
            mPromptView.moreLoading();
        } else {
            mPromptView.moreButton();
        }
    }

    /**
     * 是否开启加载更多
     * 开启时，列表末尾显示一个Footer，展示加载状态
     * 关闭时，列表末尾隐藏Footer
     */
    public void setLoadMoreEnable(boolean enable) {
        mEnableLoadMore = enable;
        mLoading = false;
        if (mEnableLoadMore) {
            if (mEnableAutoLoadMore) {
                mPromptView.moreLoading();
            } else {
                mPromptView.moreButton();
            }
        } else {
            mPromptView.hide();
        }
    }

    /**
     * 是否开启列表到底自动加载更多
     * 开启时，列表滑动到底自动加载更多，这时Footer长显progress不变
     * 关闭时，手动点击执行加载更多
     */
    public void setAutoLoadMore(boolean enable) {
        mEnableAutoLoadMore = enable;
        if (enable) {
            setFooterLoading(true);
        }
    }

    /**
     * 执行加载更多，改变Footer显示样式，执行监听回调
     */
    private void performLoadMore() {
        setLoading(true);
        if (mListener != null) {
            mListener.onLoadMore();
        }
    }

    /**
     * 提供一个固定的FooterView，并通过引用外部Adapter，提供外部Adapter的绝大部分功能。
     * hasStableIds方法无法覆盖，所以对外提供setAdapterHasStableIds方法
     *
     * @see #setHasStableIds
     */
    private static class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int ITEM_VIEW_TYPE_HEADER_OR_FOOTER = -1; // 特别注意，WrappedAdapter中的ItemType不能为-1，否则会造成冲突。建议从0开始递增

        private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter; // 暂时限制adapter不为null
        private View loadMoreView;

        private HeaderAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, View footerView) {
            this.adapter = adapter;
            this.loadMoreView = footerView;
        }

        public RecyclerView.Adapter getWrappedAdapter() {
            return adapter;
        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1) {
                return ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
            } else {
                return adapter.getItemViewType(position);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
                return new HeaderViewHolder(loadMoreView);
            } else {
                return adapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() != ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
                adapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public long getItemId(int position) {
            if (getItemViewType(position) != ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
                return adapter.getItemId(position);
            } else {
                return super.getItemId(position);
            }
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            if (holder.getItemViewType() != ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
                adapter.onViewRecycled(holder);
            }
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            if (holder.getItemViewType() != ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
                adapter.onViewAttachedToWindow(holder);
            }
        }

        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            if (holder.getItemViewType() != ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
                adapter.onViewDetachedFromWindow(holder);
            }
        }

        @Override
        public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.registerAdapterDataObserver(observer);
            }
        }

        @Override
        public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            adapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            adapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
