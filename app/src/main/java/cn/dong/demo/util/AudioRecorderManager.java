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
public class AudioRecorderManager {
    // 缓存目录名称
    private static final String CACHE_DIR_NAME = "audio";

    private static AudioRecorderManager instance;

    private MediaRecorder mMediaRecorder;
    private StateCallback mStateCallback;

    private boolean isRecording;
    private long mRecordStartTime; // 录音开始时间
    private int mRecordDuration; // 录音持续时间，单位为秒
    private File mCurrentFile;

    public static AudioRecorderManager getInstance() {
        if (instance == null) {
            synchronized (AudioRecorderManager.class) {
                if (instance == null) {
                    instance = new AudioRecorderManager();
                }
                return instance;
            }
        }
        return instance;
    }

    private AudioRecorderManager() {

    }

    public interface StateCallback {
        void onRecordingStart();
    }

    public void setStateCallback(StateCallback stateCallback) {
        mStateCallback = stateCallback;
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

    public int getRecordDuration() {
        if (isRecording) {
            return (int) (Math.abs(System.nanoTime() - mRecordStartTime) / (1000 * 1000 * 1000));
        } else {
            return mRecordDuration;
        }
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
            String filename = "AUDIO_" + timestamp + ".aac";
            mCurrentFile = new File(cacheDir, filename);
            // recorder
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            mMediaRecorder.setOutputFile(mCurrentFile.getAbsolutePath());
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            mRecordStartTime = System.nanoTime();
            isRecording = true;
            if (mStateCallback != null) {
                mStateCallback.onRecordingStart();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release() {
        if (mMediaRecorder == null) {
            return;
        }

        if (isRecording) {
            isRecording = false;
            mMediaRecorder.setOnErrorListener(null);
            try {
                mMediaRecorder.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                deleteCurrentFile();
            } finally {
                mRecordDuration = (int) (Math.abs(System.nanoTime() - mRecordStartTime) / (1000 * 1000 * 1000));
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } else {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    public void cancel() {
        release();
        // 删除已录制的文件
        deleteCurrentFile();
    }

    private void deleteCurrentFile() {
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
