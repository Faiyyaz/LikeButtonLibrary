package com.dexter.likebuttonlib;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;

public class LikeButton extends PorterShapeImageView {
    private static final String TAG = "ShineButton";
    private boolean isChecked = false;

    private int btnColor;
    private int btnFillColor;

    DisplayMetrics metrics = new DisplayMetrics();


    Activity activity;
    LikeView mLikeView;
    ValueAnimator shakeAnimator;
    LikeView.LikeParams mLikeParams = new LikeView.LikeParams();

    OnCheckedChangeListener listener;

    private int bottomHeight;
    private int realBottomHeight;

    public LikeButton(Context context) {
        super(context);
        if (context instanceof Activity) {
            init((Activity) context);
        }
    }

    public LikeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initButton(context, attrs);
    }


    public LikeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initButton(context, attrs);
    }

    private void initButton(Context context, AttributeSet attrs) {

        if (context instanceof Activity) {
            init((Activity) context);
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LikeButton);
        btnColor = a.getColor(R.styleable.LikeButton_btn_color, Color.GRAY);
        btnFillColor = a.getColor(R.styleable.LikeButton_btn_fill_color, Color.BLACK);
        mLikeParams.allowRandomColor = a.getBoolean(R.styleable.LikeButton_allow_random_color, false);
        mLikeParams.animDuration = a.getInteger(R.styleable.LikeButton_like_animation_duration, (int) mLikeParams.animDuration);
        mLikeParams.bigShineColor = a.getColor(R.styleable.LikeButton_big_like_color, mLikeParams.bigShineColor);
        mLikeParams.clickAnimDuration = a.getInteger(R.styleable.LikeButton_click_animation_duration, (int) mLikeParams.clickAnimDuration);
        mLikeParams.enableFlashing = a.getBoolean(R.styleable.LikeButton_enable_flashing, false);
        mLikeParams.shineCount = a.getInteger(R.styleable.LikeButton_like_count, mLikeParams.shineCount);
        mLikeParams.shineDistanceMultiple = a.getFloat(R.styleable.LikeButton_like_distance_multiple, mLikeParams.shineDistanceMultiple);
        mLikeParams.shineTurnAngle = a.getFloat(R.styleable.LikeButton_like_turn_angle, mLikeParams.shineTurnAngle);
        mLikeParams.smallShineColor = a.getColor(R.styleable.LikeButton_small_like_color, mLikeParams.smallShineColor);
        mLikeParams.smallShineOffsetAngle = a.getFloat(R.styleable.LikeButton_small_like_offset_angle, mLikeParams.smallShineOffsetAngle);
        mLikeParams.shineSize = a.getDimensionPixelSize(R.styleable.LikeButton_like_size, mLikeParams.shineSize);
        a.recycle();
        setSrcColor(btnColor);
    }

    public int getBottomHeight(boolean real) {
        if (real) {
            return realBottomHeight;
        }
        return bottomHeight;
    }

    public int getColor() {
        return btnFillColor;
    }

    public boolean isChecked() {
        return isChecked;
    }


    public void setBtnColor(int btnColor) {
        this.btnColor = btnColor;
        setSrcColor(this.btnColor);
    }

    public void setBtnFillColor(int btnFillColor) {
        this.btnFillColor = btnFillColor;
    }

    public void setChecked(boolean checked, boolean anim) {
        setChecked(checked, anim, true);
    }

    private void setChecked(boolean checked, boolean anim, boolean callBack) {
        isChecked = checked;
        if (checked) {
            setSrcColor(btnFillColor);
            isChecked = true;
            if (anim) showAnim();
        } else {
            setSrcColor(btnColor);
            isChecked = false;
            if (anim) setCancel();
        }
        if (callBack) {
            onListenerUpdate(checked);
        }
    }

    public void setChecked(boolean checked) {
        setChecked(checked, false, false);
    }

    private void onListenerUpdate(boolean checked) {
        if (listener != null) {
            listener.onCheckedChanged(this, checked);
            if (checked) {
                showAnim();
            } else {
                setCancel();
            }
        }
    }

    public void setCancel() {
        setSrcColor(btnColor);
        if (shakeAnimator != null) {
            shakeAnimator.end();
            shakeAnimator.cancel();
        }
    }

    public void setAllowRandomColor(boolean allowRandomColor) {
        mLikeParams.allowRandomColor = allowRandomColor;
    }

    public void setAnimDuration(int durationMs) {
        mLikeParams.animDuration = durationMs;
    }

    public void setBigShineColor(int color) {
        mLikeParams.bigShineColor = color;
    }

    public void setClickAnimDuration(int durationMs) {
        mLikeParams.clickAnimDuration = durationMs;
    }

    public void enableFlashing(boolean enable) {
        mLikeParams.enableFlashing = enable;
    }

    public void setShineCount(int count) {
        mLikeParams.shineCount = count;
    }

    public void setShineDistanceMultiple(float multiple) {
        mLikeParams.shineDistanceMultiple = multiple;
    }

    public void setShineTurnAngle(float angle) {
        mLikeParams.shineTurnAngle = angle;
    }

    public void setSmallShineColor(int color) {
        mLikeParams.smallShineColor = color;
    }

    public void setSmallShineOffAngle(float angle) {
        mLikeParams.smallShineOffsetAngle = angle;
    }

    public void setShineSize(int size) {
        mLikeParams.shineSize = size;
    }

    @Override
    public void setOnClickListener(View.OnClickListener l) {
        if (l instanceof OnButtonClickListener) {
            super.setOnClickListener(l);
        } else {
            if (onButtonClickListener != null) {
                onButtonClickListener.setListener(l);
            }
        }
    }

    public void setOnCheckStateChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }


    OnButtonClickListener onButtonClickListener;

    public void init(Activity activity) {
        this.activity = activity;
        onButtonClickListener = new OnButtonClickListener();
        setOnClickListener(onButtonClickListener);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calPixels();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    public void showAnim() {
        if (activity != null) {
            final ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
            mLikeView = new LikeView(activity, this, mLikeParams);
            rootView.addView(mLikeView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            doShareAnim();
        } else {
            Log.e(TAG, "Please init.");
        }
    }

    public void removeView(View view) {
        if (activity != null) {
            final ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
            rootView.removeView(view);
        } else {
            Log.e(TAG, "Please init.");
        }
    }

    public void setShapeResource(int raw) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setShape(getResources().getDrawable(raw, null));
        } else {
            setShape(getResources().getDrawable(raw));
        }
    }

    private void doShareAnim() {
        shakeAnimator = ValueAnimator.ofFloat(0.4f, 1f, 0.9f, 1f);
        shakeAnimator.setInterpolator(new LinearInterpolator());
        shakeAnimator.setDuration(500);
        shakeAnimator.setStartDelay(180);
        invalidate();
        shakeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setScaleX((float) valueAnimator.getAnimatedValue());
                setScaleY((float) valueAnimator.getAnimatedValue());
            }
        });
        shakeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                setSrcColor(btnFillColor);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setSrcColor(isChecked ? btnFillColor : btnColor);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                setSrcColor(btnColor);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        shakeAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void calPixels() {
        if (activity != null && metrics != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int[] location = new int[2];
            getLocationInWindow(location);
            Rect visibleFrame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleFrame);
            realBottomHeight = visibleFrame.height() - location[1];
            bottomHeight = metrics.heightPixels - location[1];
        }
    }

    public class OnButtonClickListener implements View.OnClickListener {
        void setListener(View.OnClickListener listener) {
            this.listener = listener;
        }

        View.OnClickListener listener;

        OnButtonClickListener() {
        }

        public OnButtonClickListener(View.OnClickListener l) {
            listener = l;
        }

        @Override
        public void onClick(View view) {
            onListenerUpdate(isChecked);
            if (listener != null) {
                listener.onClick(view);
            }
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View view, boolean checked);
    }

}
