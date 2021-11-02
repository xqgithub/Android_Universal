package com.universal.aifun.baselibrary.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.luck.picture.lib.tools.DateUtils

/**
 * Author  guoyw
 * Date    2021/6/23
 * Desc    媒体工具类
 */
class TKMediaUtil {

    companion object {
        // 获取音视频的展示时间

        fun getShowTime(context: Context, url: String): String {

            val mediaPlayer: MediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(context, Uri.parse(url))
            mediaPlayer.prepare()

            val showTime = DateUtils.formatDurationTime(mediaPlayer.duration.toLong())
            mediaPlayer.release()

            return showTime
        }
    }

}