package com.dexter.likebuttonlib;

import android.animation.ValueAnimator;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;

class LikeAnimator extends ValueAnimator {

    LikeAnimator(long duration, float max_value, long delay) {
        setFloatValues(1f, max_value);
        setDuration(duration);
        setStartDelay(delay);
        setInterpolator(new EasingInterpolator(Ease.QUART_OUT));
    }

    void startAnim() {
        start();
    }
}
