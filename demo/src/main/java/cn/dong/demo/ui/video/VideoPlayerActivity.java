package cn.dong.demo.ui.video;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import cn.dong.demo.Constants;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

import com.baidu.cyberplayer.core.BMediaController;
import com.baidu.cyberplayer.core.BVideoView;
import com.baidu.cyberplayer.core.BVideoView.OnCompletionListener;
import com.baidu.cyberplayer.core.BVideoView.OnErrorListener;
import com.baidu.cyberplayer.core.BVideoView.OnInfoListener;
import com.baidu.cyberplayer.core.BVideoView.OnPlayingBufferCacheListener;
import com.baidu.cyberplayer.core.BVideoView.OnPreparedListener;

/**
 * 百度视频播放器
 * 
 * @author dong 2014-7-29
 */
public class VideoPlayerActivity extends BaseActivity {
    private static final String TAG = "VideoPlayerActivity";
    public static final String EXTRA_VIDEO_URL = "video_url";

    private BVideoView mBVideoView;
    private BMediaController mBMediaController;
    private ViewGroup mActionLayout;
    private ImageView mActionButton;

    private String mVideoSource;
    private HandlerThread mHandlerThread;
    private Handler mEventHandler;

    private final Object SYNC_Playing = new Object();

    private final int EVENT_PLAY = 0;

    /**
     * 播放状态
     */
    private enum PLAYER_STATUS {
        PLAYER_IDLE, PLAYER_PREPARING, PLAYER_PREPARED,
    }

    /** 当前播放状态 */
    private PLAYER_STATUS mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
    /** 上次播放位置 */
    private int mLastPos = 0;

    @Override
    protected void init() {
        super.init();
        BVideoView.setAKSK(Constants.API_KEY, Constants.SECRET_KEY16);
        mVideoSource = getIntent().getStringExtra(EXTRA_VIDEO_URL);
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.video_player_activity;
    }

    @Override
    protected void initPageView() {
        mBVideoView = (BVideoView) findViewById(R.id.bvideo);
        mBMediaController = (BMediaController) findViewById(R.id.bmedia_controller);
        mActionLayout = (ViewGroup) findViewById(R.id.action_layout);
        mActionButton = (ImageView) findViewById(R.id.action_button);
    }

    @Override
    protected void initPageViewListener() {
        mBVideoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                Log.v(TAG, "onPrepared");
                mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARED;
            }
        });

        mBVideoView.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion() {
                Log.v(TAG, "onCompletion");

                synchronized (SYNC_Playing) {
                    SYNC_Playing.notify();
                }
                mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;

            }
        });

        mBVideoView.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(int what, int extra) {
                Log.v(TAG, "onError");
                synchronized (SYNC_Playing) {
                    SYNC_Playing.notify();
                }
                mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
                return true;
            }
        });

        mBVideoView.setOnInfoListener(new OnInfoListener() {
            @Override
            public boolean onInfo(int what, int extra) {
                switch (what) {
                    case BVideoView.MEDIA_INFO_BUFFERING_START:
                        // 开始缓冲
                        break;
                    case BVideoView.MEDIA_INFO_BUFFERING_END:
                        // 结束缓冲
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        mBVideoView.setOnPlayingBufferCacheListener(new OnPlayingBufferCacheListener() {
            @Override
            public void onPlayingBufferCache(int percent) {
                // TODO Auto-generated method stub

            }
        });

        mActionLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionLayout.setVisibility(View.GONE);
                play();
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mBVideoView.setVideoPath(mVideoSource); // 设置播放url
        mBVideoView.showCacheInfo(true); // 显示或者隐藏缓冲提示
        mBVideoView.setDecodeMode(BVideoView.DECODE_SW); // 设置解码模式
        mBVideoView.setMediaController(mBMediaController); // 关联BMediaController
        mBMediaController.hide();

        // 开启后台事件处理线程
        mHandlerThread =
                new HandlerThread("event handler thread", Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mEventHandler = new EventHandler(mHandlerThread.getLooper());
    }

    /**
     * 发起一次播放任务
     */
    private void play() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 保持屏幕常亮
        mEventHandler.sendEmptyMessage(EVENT_PLAY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActionLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在停止播放前,先记录当前播放的位置,以便以后可以续播
        if (mPlayerStatus == PLAYER_STATUS.PLAYER_PREPARED) {
            mLastPos = mBVideoView.getCurrentPosition();
            mBVideoView.stopPlayback();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 取消屏幕常亮
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }

    class EventHandler extends Handler {

        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(Thread.currentThread().getName());
            switch (msg.what) {
                case EVENT_PLAY:
                    // 如果已经播放了，等待上一次播放结束
                    if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
                        synchronized (SYNC_Playing) {
                            try {
                                SYNC_Playing.wait();
                                Log.v(TAG, "wait player status to idle");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    // 续播
                    if (mLastPos > 0) {
                        mBVideoView.seekTo(mLastPos);
                        mLastPos = 0;
                    }
                    // 开始播放
                    mBVideoView.start();

                    mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARING;
                    break;
                default:
                    break;
            }
        }
    }

}
