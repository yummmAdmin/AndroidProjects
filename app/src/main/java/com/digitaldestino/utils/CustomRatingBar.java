package com.digitaldestino.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.digitaldestino.R;

public class CustomRatingBar extends FrameLayout implements OnClickListener {
    private static final int MAX_COUNT = 5;
    private static final String TAG = CustomRatingBar.class.getSimpleName();
    private boolean mClickRating = true;
    private Context mContext;
    private float mCount = 0.0f;
    private Drawable mEmptyDrawable = null;
    private Drawable mFillDrawable = null;
    private ImageView[] mImageViews = null;
    private int mMaxCount = 5;
    private int mOldStarCount = 0;
    private float mOldX = 0.0f;
    private float mOldY = 0.0f;
    private OnRatingChangeListener mOnRatingChangeListener = null;
    private LinearLayout mRootLayout;
    private int mSpace = 0;
    private boolean mTouchRating = true;

    public interface OnRatingChangeListener {
        void onChange(CustomRatingBar customRatingBar, float f, float f2);
    }

    public boolean isTouchRating() {
        return this.mTouchRating;
    }

    public void setTouchRating(boolean touchRating) {
        this.mTouchRating = touchRating;
    }

    public boolean isClickRating() {
        return this.mClickRating;
    }

    public void setClickRating(boolean clickRating) {
        this.mClickRating = clickRating;
    }

    public CustomRatingBar(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public int getSpace() {
        return this.mSpace;
    }

    public void setSpace(int space) {
        space = Math.max(0, space);
        if (this.mSpace != space) {
            this.mSpace = space;
            updateStarViews();
        }
    }

    public Drawable getFillDrawable() {
        return this.mFillDrawable;
    }

    public void setFillDrawable(Drawable fillDrawable) {
        if (this.mFillDrawable != fillDrawable) {
            this.mFillDrawable = fillDrawable;
            updateStarViews();
        }
    }

    public void setFillDrawableRes(@DrawableRes int res) {
        if (VERSION.SDK_INT >= 21) {
            setFillDrawable(this.mContext.getDrawable(res));
        } else {
            setFillDrawable(this.mContext.getResources().getDrawable(res));
        }
    }

    public Drawable getEmptyDrawable() {
        return this.mEmptyDrawable;
    }

    public void setEmptyDrawable(@Nullable Drawable emptyDrawable) {
        this.mEmptyDrawable = emptyDrawable;
        updateStarViews();
    }

    public void setEmptyDrawableRes(@DrawableRes int res) {
        if (VERSION.SDK_INT >= 21) {
            setEmptyDrawable(this.mContext.getDrawable(res));
        } else {
            setEmptyDrawable(this.mContext.getResources().getDrawable(res));
        }
    }

    public void setOnRatingChangeListener(@Nullable OnRatingChangeListener listener) {
        this.mOnRatingChangeListener = listener;
    }

    public int getMaxCount() {
        return this.mMaxCount;
    }

    public void setMaxCount(int maxCount) {
        maxCount = Math.max(0, maxCount);
        if (maxCount != this.mMaxCount) {
            this.mMaxCount = maxCount;
            createStarViews(maxCount);
            if (((float) maxCount) < this.mCount) {
                setCount((float) maxCount);
            }
        }
    }

    public float getCount() {
        return this.mCount;
    }

    public void setCount(float count) {
        count = Math.max(0.0f, Math.min((float) this.mMaxCount, count));
        if (count != this.mCount) {
            float oldCount = this.mCount;
            this.mCount = count;
            updateStarViews();
            if (this.mOnRatingChangeListener != null) {
                this.mOnRatingChangeListener.onChange(this, oldCount, this.mCount);
            }
        }
    }

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
            this.mMaxCount = typedArray.getInteger(4, 5);
            this.mCount = (float) typedArray.getInteger(1, 0);
            this.mFillDrawable = typedArray.getDrawable(3);
            this.mEmptyDrawable = typedArray.getDrawable(2);
            this.mSpace = typedArray.getDimensionPixelSize(5, 0);
            this.mClickRating = typedArray.getBoolean(0, true);
            this.mTouchRating = typedArray.getBoolean(6, true);
            typedArray.recycle();
            if (this.mFillDrawable == null) {
                if (VERSION.SDK_INT >= 21) {
                    this.mFillDrawable = context.getDrawable(R.drawable.profile_star_yellow);
                } else {
                    this.mFillDrawable = context.getResources().getDrawable(R.drawable.profile_star_yellow);
                }
            }
            this.mMaxCount = Math.max(0, this.mMaxCount);
            this.mCount = Math.max(0.0f, Math.min(this.mCount, (float) this.mMaxCount));
        } else if (VERSION.SDK_INT >= 21) {
            this.mFillDrawable = context.getDrawable(R.drawable.profile_star_yellow);
            this.mEmptyDrawable = context.getDrawable(R.drawable.profile_star_grey);
        } else {
            this.mFillDrawable = context.getResources().getDrawable(R.drawable.profile_star_yellow);
            this.mEmptyDrawable = context.getResources().getDrawable(R.drawable.profile_star_grey);
        }
        this.mRootLayout = new LinearLayout(context);
        addView(this.mRootLayout, new LayoutParams(-1, -1));
        createStarViews(this.mMaxCount);
    }

    private void createStarViews(int count) {
        if (this.mRootLayout.getChildCount() > 0) {
            this.mRootLayout.removeAllViews();
        }
        this.mImageViews = new ImageView[count];
        for (int i = 0; i < this.mImageViews.length; i++) {
            FrameLayout frameLayout = new FrameLayout(getContext());
            this.mRootLayout.addView(frameLayout, new LinearLayout.LayoutParams(0, -1, 1.0f));
            this.mImageViews[i] = new ImageView(getContext());
            ImageView imageView = this.mImageViews[i];
            imageView.setOnClickListener(this);
            imageView.setScaleType(ScaleType.CENTER_INSIDE);
            imageView.setTag(Integer.valueOf(i));
            frameLayout.addView(imageView, new LayoutParams(-1, -1, 17));
        }
        updateStarViews();
    }

    private void updateStarViews() {
        int i = 0;
        while (i < this.mImageViews.length) {
            ImageView imgView = this.mImageViews[i];
            imgView.setImageDrawable(((float) i) < this.mCount ? this.mFillDrawable : this.mEmptyDrawable);
            ViewGroup parent = (ViewGroup) imgView.getParent();
            MarginLayoutParams mlp = (MarginLayoutParams) parent.getLayoutParams();
            if (imgView.getDrawable() == null || i - 1 < 0 || this.mImageViews[i - 1].getDrawable() == null) {
                mlp.setMargins(0, 0, 0, 0);
            } else {
                mlp.setMargins(this.mSpace, 0, 0, 0);
            }
            parent.setLayoutParams(mlp);
            i++;
        }
    }

    public void onClick(View v) {
        setCount((float) (((Integer) v.getTag()).intValue() + 1));
    }

    public void setClickable(boolean clickable) {
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mOldX = event.getX();
                this.mOldY = event.getY();
                this.mOldStarCount = getTouchStarCount(event);
                return true;
            case 1:
                if (this.mClickRating) {
                    int starCount = getTouchStarCount(event);
                    if (starCount == this.mOldStarCount) {
                        setCount((float) starCount);
                    }
                    break;
                }
                break;
            case 2:
                if (this.mTouchRating) {
                    if (((int) Math.round(Math.sqrt(Math.pow((double) (event.getX() - this.mOldX), 2.0d) + Math.pow((double) (event.getY() - this.mOldY), 2.0d)))) >= ViewConfiguration.getTouchSlop()) {
                        setCount((float) getTouchStarCount(event));
                    }
                }
                this.mOldX = event.getX();
                this.mOldY = event.getY();
                break;
            default:
                break;
        }
        super.onTouchEvent(event);
        return false;
    }

    private View getStarView(int index) {
        return this.mRootLayout.getChildAt(index);
    }

    private int getTouchStarCount(MotionEvent event) {
        int count = 1;
        float rawX = event.getRawX();
        for (int i = 0; i < getMaxCount(); i++) {
            Rect rect = new Rect();
            View view = getStarView(i);
            view.getGlobalVisibleRect(rect);
            if (rawX > ((float) (rect.right + ((MarginLayoutParams) view.getLayoutParams()).rightMargin))) {
                count++;
            }
        }
        return count;
    }
}
