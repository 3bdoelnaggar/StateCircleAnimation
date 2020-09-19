package elnaggar.statecircleanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;


public class StateCircleAnimation extends View {

    Paint arcPaint1;
    Paint arcPaint2;
    Paint arcPaint3;
    Paint arcPaint4;
    Paint arcPaint5;
    private float sweapAngel = 0;
    private ArrayList<ImageView> imageViews;
    private TextView tvStateName;
    private Paint activePaint;
    private Paint unActivePaint;
    private int stateId;

    public StateCircleAnimation(Context context) {
        super(context);
        setInitialColors();
    }


    public StateCircleAnimation(Context context,  AttributeSet attrs) {
        super(context, attrs);
        setInitialColors();

    }

    /*Initialise color we have two color active an unActive
    stroke width
    dashed style for un Active part
    */
    void setInitialColors() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getContext().getResources().getColor(R.color.colorAccent));

        paint.setStrokeWidth(getResources().getDimension(R.dimen.circleStorke));

        activePaint = new Paint(paint);
        activePaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));

        unActivePaint = new Paint(paint);
        unActivePaint.setColor(getResources().getColor(android.R.color.darker_gray));
        unActivePaint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

        arcPaint1 = new Paint(unActivePaint);
        arcPaint2 = new Paint(unActivePaint);
        arcPaint3 = new Paint(unActivePaint);
        arcPaint4 = new Paint(unActivePaint);
        arcPaint5 = new Paint(unActivePaint);
    }

    public void setViews(ArrayList<ImageView> imageViews, TextView tvStateName) {
        this.imageViews = imageViews;
        this.tvStateName = tvStateName;
    }

    // draw part by part check which piece in any part to get the part style
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int parts = (int) (sweapAngel / 72);
        float remains = sweapAngel % 72;
        if (remains != 0) {
            parts += 1;
        }

        for (int i = 0; i < parts; i++) {
            if (i == parts - 1) {
                if (remains == 0) {
                    drawPart(i + 1, 72, canvas);

                } else {
                    drawPart(i + 1, remains, canvas);

                }
            } else {
                drawPart(i + 1, 72, canvas);

            }
        }
    }

    private void drawPart(int part, float sweapAngel, Canvas canvas) {
        final RectF oval = new RectF(0, 0, getWidth(), getWidth());
        oval.inset(2, 2);
        Path redPath = new Path();
        redPath.arcTo(oval, getStartAngelByPart(part), sweapAngel, true);
        //canvas.drawPath(redPath, getArcPaint(getStartAngelByPart(part)));
        canvas.drawPath(redPath, getArcPaintByPart(part));

    }

    // return where that part start
    int getStartAngelByPart(int part) {
        int returnedValue = 198 + (part * 72);
        if (returnedValue > 360) {
            returnedValue = returnedValue % 360;
        }
        return returnedValue;

    }


    Paint getArcPaintByPart(int acrNum) {
        switch (acrNum) {
            case 1:
                return arcPaint1;
            case 2:
                return arcPaint2;

            case 3:
                return arcPaint3;

            case 4:
                return arcPaint4;


            case 5:
                return arcPaint5;

        }
        return arcPaint1;
    }

    //this is used if you want to change the icon based on the state too
    public void changeState(int stateID) {
        switch (stateID) {
            case 1:
            case 0:
            case 6:
                arcPaint1 = new Paint(unActivePaint);
                arcPaint2 = new Paint(unActivePaint);
                arcPaint3 = new Paint(unActivePaint);
                arcPaint4 = new Paint(unActivePaint);
                arcPaint5 = new Paint(unActivePaint);
                break;
            case 2:
                arcPaint1 = new Paint(activePaint);
                arcPaint2 = new Paint(unActivePaint);
                arcPaint3 = new Paint(unActivePaint);
                arcPaint4 = new Paint(unActivePaint);
                arcPaint5 = new Paint(unActivePaint);
                break;
            case 3:

                arcPaint1 = new Paint(activePaint);
                arcPaint2 = new Paint(activePaint);
                arcPaint3 = new Paint(unActivePaint);
                arcPaint4 = new Paint(unActivePaint);
                arcPaint5 = new Paint(unActivePaint);


                break;
            case 4:

                arcPaint1 = new Paint(activePaint);
                arcPaint2 = new Paint(activePaint);
                arcPaint3 = new Paint(activePaint);
                arcPaint4 = new Paint(unActivePaint);
                arcPaint5 = new Paint(unActivePaint);
                break;
            case 5:
                arcPaint1 = new Paint(activePaint);
                arcPaint2 = new Paint(activePaint);
                arcPaint3 = new Paint(activePaint);
                arcPaint4 = new Paint(activePaint);
                arcPaint5 = new Paint(activePaint);

                break;

        }

    }

    //hide the icon to before animations
    private void prepareAnimation() {
        for (ImageView imageView : imageViews) {
            imageView.setScaleX(0f);
            imageView.setScaleY(0f);
        }
    }

//load animations base on order if you want to change order you can use int array get order based on index
    void loadAnimationOrdaly(final int i) {
        imageViews.get(i).animate().scaleX(1f).scaleY(1f).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int newI = i + 1;
                if (newI < imageViews.size() && i >= 0) {
                    loadAnimationOrdaly(newI);
                }
                if (i == 3) {
                    loadCircleAnimation();
                }
                if (i == 4) {
                }
            }
        }).start();
    }


    void loadAnimationOrdalyReverse() {
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = imageViews.get(i);
            final int finalI = i;
            imageView.animate().scaleX(0f).scaleY(0f).setDuration(200).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (finalI == 4) {
                        loadNormalAnimation();
                    }

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }


    public void loadNormalAnimation() {
        changeState(stateId);
        prepareAnimation();
        loadAnimationOrdaly(0);
    }

    public void loadReverseAnimation() {
        loadCircleAnimationReverse();
    }

    void loadCircleAnimation() {
        this.setSweapAngel(0);
        CircleAngleAnimation circleAngelAnimation = new CircleAngleAnimation(CircleAngleAnimation.ZORDER_NORMAL, this, 360);
        circleAngelAnimation.setDuration(1000);
        this.startAnimation(circleAngelAnimation);

    }

    void loadCircleAnimationReverse() {
        this.setSweapAngel(360);
        CircleAngleAnimation circleAngelAnimation = new CircleAngleAnimation(CircleAngleAnimation.REVERSE, this, 0);
        circleAngelAnimation.setDuration(1000);
        circleAngelAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadAnimationOrdalyReverse();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        this.startAnimation(circleAngelAnimation);

    }


    public void setSweapAngel(float sweapAngel) {

        this.sweapAngel = sweapAngel;
    }

    public float getSweapAngel() {
        return sweapAngel;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public static class CircleAngleAnimation extends Animation {

        private final int type;
        private final StateCircleAnimation circle;

        private final float oldAngle;
        private final float newAngle;

        public CircleAngleAnimation(int type, StateCircleAnimation circle, int newAngle) {
            this.oldAngle = circle.getSweapAngel();
            this.newAngle = newAngle;
            this.circle = circle;
            this.type = type;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            float angle;
            if (type == REVERSE) {
                angle = oldAngle - ((oldAngle - newAngle) * interpolatedTime);
            } else {
                angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);
            }

            circle.setSweapAngel(angle);
            circle.requestLayout();
        }
    }
}
