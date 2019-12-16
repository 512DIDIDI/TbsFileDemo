package com.dididi.packrat.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.dididi.packrat.BuildConfig


/**
 * @author dididi(yechao)
 * @since 09/10/2019
 * @describe 日志工具类
 */

private const val TAG = "PackRat"
//最长日志行数
private const val LOG_MAX_LENGTH = 2000
private var debug = BuildConfig.DEBUG

fun Context.toast(value: String) {
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}

fun log(content: String){
    if (debug) {
        //增加log日志的长度
        val strLength = content.length
        var start = 0
        var end = LOG_MAX_LENGTH
        for (i in 0..100) {
            if (strLength > end) {
                Log.d(TAG, content.substring(start, end))
                start = end
                end += LOG_MAX_LENGTH
            } else {
                Log.d(TAG, content.substring(start, strLength))
                break
            }
        }
    }
}


