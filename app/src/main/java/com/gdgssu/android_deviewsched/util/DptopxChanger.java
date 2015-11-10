package com.gdgssu.android_deviewsched.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by acekim on 15. 11. 10.
 */
public class DptopxChanger {
    public static int dpToPXChange(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
