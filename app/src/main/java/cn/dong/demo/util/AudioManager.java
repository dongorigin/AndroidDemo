package cn.dong.demo.util;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.dong.demo.MyApp;

/**
 * 录音管理
 *
 * @author dong on 15/10/21.
 */
public class AudioManager {
    // 缓存目录名称
    private static final String CACHE_DIR_NAME = "audio";

    private static AudioManager instance;

    private AudioStateListener mAudioStateListener;
    private MediaRecorder mMediaRecorder;

    private boolean isRecording;
    private File mCurrentFile;

    public static AudioManager getInstance() {
        if (instance == null) {
            synchronized (AudioManager.class) {
                if (instance == null) {
                    instance = new AudioManager();
                }
                return instance;
            }
        }
        return instance;
    }

    private AudioManager() {

    }

    public interface AudioStateListener {
        void onRecording();
    }

    public void setAudioStateListener(AudioStateListener audioStateListener) {
        mAudioStateListener = audioStateListener;
    }

    public File getCurrentFile() {
        return mCurrentFile;
    }

    private File getCacheDir() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 不可访问外部存储
            return MyApp.getInstance().getCacheDir();
        } else {
            return MyApp.getInstance().getExternalCacheDir();
        }
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void startRecord() {
        if (isRecording) {
            cancel();
        }
        File cacheDir = new File(getCacheDir(), CACHE_DIR_NAME);
        // create dir
        if (!cacheDir.exists()) {
            if (cacheDir.mkdir()) {
                // create failure
                return;
            }
        }

        try {
            // create file name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = "AUDIO_" + timestamp + ".amr";
            mCurrentFile = new File(cacheDir, filename);
            // recorder
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(mCurrentFile.getAbsolutePath());
            mMediaRecorder.prepare();
            mMediaRecorder.start();

            isRecording = true;
            if (mAudioStateListener != null) {
                mAudioStateListener.onRecording();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release() {
        if (isRecording) {
            isRecording = false;
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        } else {
            mMediaRecorder.reset();
            mMediaRecorder.release();
        }
    }

    public void cancel() {
        release();
        // 删除已录制的文件
        if (mCurrentFile != null) {
            mCurrentFile.delete();
        }
    }

    public int getVoiceLevel(int maxLevel) {
        if (isRecording) {
            try {
                // getMaxAmplitude 的范围为 0-32767
                return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return 1;
            }
        }
        return 1;
    }

}
