package com.example.floas.utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * @Copyright © 2017 Umeng Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2017/11/9 10:21
 * @author: safei
 * @mail: yongxu.xyf@alibaba-inc.com
 */
public class VolumeHelper {

    private AudioManager mAudioManager = null;
    /* The audio stream for phone calls */
    public static final int STREAM_VOICE_CALL = 0;
    /* The audio stream for system sounds */
    public static final int STREAM_SYSTEM = 1;
    /* The audio stream for the phone ring */
    public static final int STREAM_RING = 2;
    /* The audio stream for music playback */
    public static final int STREAM_MUSIC = 3;
    /* The audio stream for alarms */
    public static final int STREAM_ALARM = 4;
    /* The audio stream for notifications */
    public static final int STREAM_NOTIFICATION = 5;
    /* The audio stream for phone calls when connected to bluetooth */
    public static final int STREAM_BLUETOOTH_SCO = 6;
    /* The audio stream for enforced system sounds in certain countries (e.g camera in Japan) */
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    /* The audio stream for DTMF Tones */
    public static final int STREAM_DTMF = 8;
    /*The audio stream for text to speech (TTS) */
    public static final int STREAM_TTS = 9;

    //降低音量
    public static final int DURATION_ADJUST_LOWER = -1;
    //保持不变.可以展示当前音量
    public static final int DURATION_ADJUST_SAME = 0;
    //增加音量
    public static final int DURATION_ADJUST_RAISE = 1;
    /**
     * 使用于如下方法
     * adjustStreamVolume(int, int, int)
     * adjustVolume(int, int)
     * setStreamVolume(int, int, int)
     * setRingerMode(int)
     */
    public static final int FLAG_SHOW_UI = 1 << 0;         //调整时显示系统的音量进度条
    public static final int FLAG_ALLOW_RINGER_MODES = 1 << 1;
    public static final int FLAG_PLAY_SOUND = 1 << 2;         //调整音量时播放声音
    public static final int FLAG_REMOVE_SOUND_AND_VIBRATE = 1 << 3;
    public static final int FLAG_VIBRATE = 1 << 4;
    public static final int FLAG_FIXED_VOLUME = 1 << 5;
    public static final int FLAG_BLUETOOTH_ABS_VOLUME = 1 << 6;
    public static final int FLAG_SHOW_SILENT_HINT = 1 << 7;
    public static final int FLAG_HDMI_SYSTEM_AUDIO_VOLUME = 1 << 8;
    public static final int FLAG_ACTIVE_MEDIA_ONLY = 1 << 9;
    public static final int FLAG_SHOW_UI_WARNINGS = 1 << 10;
    public static final int FLAG_SHOW_VIBRATE_HINT = 1 << 11;
    public static final int FLAG_FROM_KEY = 1 << 12;

    private VolumeHelper() {}

    private static class Holder {
        private static final VolumeHelper INSTANCE = new VolumeHelper();
    }

    public static VolumeHelper getInstance() {
        if (Holder.INSTANCE.mAudioManager == null) {
            Holder.INSTANCE.mAudioManager = (AudioManager)Content.CONTEXT.getSystemService(
                Context.AUDIO_SERVICE);
        }
        return Holder.INSTANCE;
    }

    /**
     * 获取当前音量
     *
     * @param streamType
     * @return
     */
    public int getCurrentVolume(int streamType) {
        return mAudioManager.getStreamVolume(streamType);
    }

    /**
     * 获取最大音量
     *
     * @param streamType
     * @return
     */
    public int getMaxVolume(int streamType) {
        return mAudioManager.getStreamMaxVolume(streamType);
    }

    /**
     * 设置绝对值的音量
     *
     * @param streamType
     * @param index
     * @param flags
     */
    public void setSilentVolume(int streamType, int index, int flags) {
        mAudioManager.setStreamVolume(streamType, index, flags);
    }

    /**
     * 递减音量
     *
     * @param flag
     */
    public void lowerVolume(int flag) {
        mAudioManager.adjustStreamVolume(flag, AudioManager.ADJUST_LOWER,
            AudioManager.FX_FOCUS_NAVIGATION_UP);
    }

    /**
     * 更改音量
     *
     * @param streamType
     * @param duration
     * @param flag
     */
    public void updateVolume(int streamType, int duration, int flag) {
        mAudioManager.adjustStreamVolume(streamType, duration, flag);
    }

    public void setVolume(int index) {
        mAudioManager.setStreamVolume(STREAM_MUSIC, index, FLAG_PLAY_SOUND);
    }

    public void upVolume() {
        mAudioManager.adjustStreamVolume(STREAM_MUSIC, DURATION_ADJUST_RAISE, FLAG_PLAY_SOUND);
    }
}
