package cn.dong.demo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.dong.demo.R;

/**
 * 提示
 *
 * @author dong on 15/3/25.
 */
class DRecyclerViewPrompt extends FrameLayout {

    @InjectView(R.id.loadmore_frame)
    View frameView;
    @InjectView(R.id.footer_image)
    ImageView mPromptImageView;
    @InjectView(R.id.footer_progressbar)
    ProgressBar mPromptProgressBar;
    @InjectView(R.id.footer_text)
    TextView mPromptTextView;

    public DRecyclerViewPrompt(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.d_recycler_footer_layout, this);
        ButterKnife.inject(this, this);
    }

    public void resizeLarge() {
        frameView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.d_recycler_prompt_large_height)));
    }

    public void resizeFooter() {
        frameView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.d_recycler_prompt_footer_height)));
    }

    public void hide() {
        frameView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
    }

    public void loading() {
        resizeLarge();
        setClickable(false);
        mPromptImageView.setVisibility(View.GONE);
        mPromptProgressBar.setVisibility(View.VISIBLE);
        mPromptTextView.setVisibility(View.GONE);
    }

    public void empty(String hint) {
        resizeLarge();
        setClickable(false);
        mPromptImageView.setVisibility(View.VISIBLE);
        mPromptProgressBar.setVisibility(View.GONE);
        mPromptTextView.setVisibility(View.VISIBLE);
        mPromptTextView.setText(hint);
    }

    public void moreLoading() {
        resizeFooter();
        setClickable(false);
        mPromptImageView.setVisibility(View.GONE);
        mPromptProgressBar.setVisibility(View.VISIBLE);
        mPromptTextView.setVisibility(View.GONE);
    }

    public void moreButton() {
        resizeFooter();
        setClickable(true);
        mPromptImageView.setVisibility(View.GONE);
        mPromptProgressBar.setVisibility(View.GONE);
        mPromptTextView.setVisibility(View.VISIBLE);
        mPromptTextView.setText("LOAD MORE");
    }

    public void moreEnd() {
        resizeFooter();
        setClickable(false);
        mPromptImageView.setVisibility(View.GONE);
        mPromptProgressBar.setVisibility(View.GONE);
        mPromptTextView.setVisibility(View.VISIBLE);
        mPromptTextView.setText("THE END");
    }

}
