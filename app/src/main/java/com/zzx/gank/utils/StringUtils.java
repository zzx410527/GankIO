package com.zzx.gank.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;

import com.zzx.gank.R;
import com.zzx.gank.mvp.model.entity.Gank;

/**
 * Created by zuozhixiang on 17/11/17.
 */

public class StringUtils {
    public static CharSequence getGankStyleStr(Context context, Gank gank) {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(
                format(context, "[" + DateUtil.friendlyTime(gank.publishedAt) +
                        "]", R.style.ByTextAppearance));
        builder.append(gank.desc);
        builder.append(
                format(context, " [via " +
                        gank.who + "]", R.style.ByTextAppearance));
        return builder.subSequence(0, builder.length());
    }

    public static SpannableString format(Context context, String text, int style) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(),
                0);
        return spannableString;
    }
}
