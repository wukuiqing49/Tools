package util;



import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

/**
 * @Author: wkq
 * @Time: 2025/2/6 16:50
 * @Desc:
 */



import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

public class OnlineMediaPlayerUtil {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;
    private static Vibrator vibrator;

    // 单例模式，确保只有一个实例
    private static class SingletonHolder {
        private static final OnlineMediaPlayerUtil INSTANCE = new OnlineMediaPlayerUtil();
    }

    public static OnlineMediaPlayerUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void init(Context context) {
        OnlineMediaPlayerUtil.context = context.getApplicationContext();
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public synchronized void playAudio(String url, boolean enableVibration, long[] pattern, int[] amplitudes) {
        if (isPlaying) return;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(context, Uri.parse(url));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    isPlaying = true;
                    if (enableVibration) {
                        startVibration(pattern, amplitudes);
                    }
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopAudio();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    stopAudio();
                    return false;
                }
            });
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void stopAudio() {
        if (!isPlaying) return;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer = null;
        }
        isPlaying = false;
        stopVibration();
    }

    private void startVibration(long[] pattern, int[] amplitudes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startVibrationNew(pattern, amplitudes);
        } else {
            startVibrationOld(pattern);
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startVibrationNew(long[] pattern, int[] amplitudes) {
        android.os.VibrationEffect effect;
        if (pattern != null && amplitudes != null && pattern.length == amplitudes.length) {
            effect = android.os.VibrationEffect.createWaveform(pattern, amplitudes, 2); // 0 表示循环播放
        } else {
            // 默认震动模式
            long[] defaultPattern = {0, 200, 100, 200, 100, 200};
            int[] defaultAmplitudes = {255, 255, 255, 255, 255, 255};
            effect = android.os.VibrationEffect.createWaveform(defaultPattern, defaultAmplitudes, 2); // 0 表示循环播放
        }
        vibrator.vibrate(effect);
    }

    @SuppressWarnings("deprecation")
    private void startVibrationOld(long[] pattern) {
        if (pattern != null) {
            vibrator.vibrate(pattern, 0); // 0 表示循环播放
        } else {
            // 默认震动模式
            long[] defaultPattern = {0, 200, 100, 200, 100, 200};
            vibrator.vibrate(defaultPattern, 2); // 0 表示循环播放
        }
    }

    private void stopVibration() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}

