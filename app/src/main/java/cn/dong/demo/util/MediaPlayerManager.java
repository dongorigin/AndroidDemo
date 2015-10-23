package cn.dong.demo.util;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * 多媒体播放管理
 *
 * @author dong on 15/10/22.
 */
public class MediaPlayerManager {

    private static MediaPlayer sMediaPlayer;

    private static void initMediaPlayer() {
        if (sMediaPlayer == null) {
            sMediaPlayer = new MediaPlayer();
            sMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    sMediaPlayer.reset();
                    return false;
                }
            });
        } else {
            sMediaPlayer.reset();
        }
    }

    public static void playAudio(String audioPath, MediaPlayer.OnCompletionListener listener) {
        initMediaPlayer();
        try {
            sMediaPlayer.setDataSource(audioPath);
            sMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            sMediaPlayer.setOnCompletionListener(listener);
            sMediaPlayer.prepare();
            sMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void release() {
        if (sMediaPlayer != null) {
            sMediaPlayer.release();
            sMediaPlayer = null;
        }
    }

}
