package cn.dong.demo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;
import cn.dong.demo.util.StrUtil;

public class WebViewActivity extends BaseActivity {
    private WebView mWebView;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initPageView() {
        mWebView = (WebView) findViewById(R.id.webview);
    }

    @Override
    protected void initPageViewListener() {
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("about:blank") || url == null || url.equals("")) {
                    // 解决有些网站跳转到blank
                } else if (url.startsWith("wtai://wp/mc;")) {
                    // 解决webview下不识别自动打电话wtai://wp/mc;问题
                    String num = StrUtil.getSingleSplitStr(url, 1, ";");
                    if (num != null && !num.equals("")) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + num));
                        startActivity(intent);
                    }
                } else if (url.toLowerCase().endsWith(".mp4") || url.toLowerCase().endsWith(".3gp")
                        || url.toLowerCase().endsWith(".wma") || url.toLowerCase().endsWith(".avi")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "video/*");
                    startActivity(intent);
                } else {
                    mWebView.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        initWebView();
        mWebView.loadUrl("https://www.google.com.hk/");
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true); // 解决有些js进度条加载后不跳转问题
        settings.setPluginState(PluginState.ON);
    }

    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
