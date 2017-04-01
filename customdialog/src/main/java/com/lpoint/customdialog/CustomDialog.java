package com.lpoint.customdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by Lpoint on 2017/3/31.
 */

public class CustomDialog extends RelativeLayout {
    private RelativeLayout relLayout;
    private View customView;
    private boolean inHide = false;

    public CustomDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, this);
        relLayout = (RelativeLayout) findViewById(R.id.rel_layout);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomDialog);
        customView = View.inflate(context, ta.getResourceId(R.styleable.CustomDialog_dialog_layout, 0), null);
        relLayout.addView(customView);
        bringToFront();
    }

    public void show() {
        if (getVisibility() == View.VISIBLE)
            return;
        setVisibility(View.VISIBLE);
        showAnimation();
    }

    public void hide() {
        if (getVisibility() == View.GONE || inHide)
            return;
        hideAnimation();
    }

    private void showAnimation() {
        AnimationSet animSet = new AnimationSet(true);
        TranslateAnimation ta = new TranslateAnimation(0f, 0f, (float) -getHeight() / 2, 0f);
        ta.setDuration(300);
        AlphaAnimation aa = new AlphaAnimation(0f, 1);
        aa.setDuration(300);
        animSet.addAnimation(ta);
        animSet.addAnimation(aa);
        startAnimation(aa);
        relLayout.startAnimation(animSet);
    }

    private void hideAnimation() {
        AnimationSet animSet = new AnimationSet(true);
        TranslateAnimation ta = new TranslateAnimation(0f, 0f, 0f, (float) -getHeight() / 2);
        ta.setDuration(300);
        AlphaAnimation aa = new AlphaAnimation(1f, 0f);
        aa.setDuration(300);
        animSet.addAnimation(ta);
        animSet.addAnimation(aa);
        startAnimation(aa);
        relLayout.startAnimation(animSet);
        animSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                inHide = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.GONE);
                inHide = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public View getCustomView() {
        return customView;
    }
}
