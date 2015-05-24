package cn.dong.demo.ui.creation;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.ScrollUtils;
import cn.dong.demo.view.DRecyclerView;

/**
 * @author dong on 15/3/31.
 */
public class FlexibleHeaderRecyclerViewActivity extends BaseActivity {
    @InjectView(R.id.header_frame)
    View mHeaderView;
    @InjectView(R.id.recycler)
    DRecyclerView mRecyclerView;

    private int mFlexibleHeight;
    private int mFlexibleMinHeight;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_flexibleheader;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFlexibleHeight = getResources().getDimensionPixelSize(R.dimen.flexible_max_height);
        mFlexibleMinHeight = getResources().getDimensionPixelSize(R.dimen.flexible_min_height);
        // header
        mHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "header click", Toast.LENGTH_SHORT).show();
            }
        });

        // recycler
        mRecyclerView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollY;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollY += dy;
//                L.d(TAG, "onScrolled %d", scrollY);
                mHeaderView.setTranslationY(ScrollUtils.getFloat(-scrollY, mFlexibleMinHeight - mFlexibleHeight, 0));
            }
        });

        mRecyclerView.setAdapter(new HeaderAdapter());
        mRecyclerView.setRefreshEnabled(false);
        mRecyclerView.promptEnd();
    }

    private static class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_CARD = 1;

        public HeaderAdapter() {

        }

        @Override
        public int getItemCount() {
            return 20;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                return TYPE_CARD;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == TYPE_HEADER) {
                View headerView = inflater.inflate(R.layout.activity_flexibleheader_spacing, parent, false);
                return new HeaderViewHolder(headerView);
            } else {
                View cardView = inflater.inflate(R.layout.activity_flexibleheader_item, parent, false);
                return new CardViewHolder(cardView);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == TYPE_CARD) {
                CardViewHolder cardViewHolder = (CardViewHolder) holder;
                cardViewHolder.titleView.setText(String.format("item %d", position));
            }
        }

    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;

        public CardViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.text);
        }
    }

}
