package com.core.app.ui.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class FlipAnimation2 extends Animation {
    private Camera camera;
    private float centerX;
    private float centerY;
    private boolean forward = true;
    private View fromView;
    private View toView;

    public FlipAnimation2(View fromView, View toView) {
        this.fromView = fromView;
        this.toView = toView;
        setDuration(700);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void reverse() {
        this.forward = false;
        View switchView = this.toView;
        this.toView = this.fromView;
        this.fromView = switchView;
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.centerX = (float) (width / 2);
        this.centerY = (float) (height / 2);
        this.camera = new Camera();
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degrees = (float) ((180.0d * (3.141592653589793d * ((double) interpolatedTime))) / 3.141592653589793d);
        if (interpolatedTime >= 0.5f) {
            degrees -= 180.0f;
            this.fromView.setVisibility(View.GONE);
            this.toView.setVisibility(View.VISIBLE);
        }
        Matrix matrix;
        if (this.forward) {
            matrix = t.getMatrix();
            this.camera.save();
            this.camera.rotateX(degrees);
            this.camera.getMatrix(matrix);
            this.camera.restore();
            matrix.preTranslate(-this.centerX, -this.centerY);
            matrix.postTranslate(this.centerX, this.centerY);
        } else {
            matrix = t.getMatrix();
            this.camera.save();
            this.camera.rotateX(degrees);
            this.camera.getMatrix(matrix);
            this.camera.restore();
            matrix.preTranslate(-this.centerX, -this.centerY);
            matrix.postTranslate(this.centerX, this.centerY);
        }
    }
}