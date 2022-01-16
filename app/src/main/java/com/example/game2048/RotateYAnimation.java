package com.example.game2048;

import android.graphics.Camera;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class RotateYAnimation extends Animation {
    // Camera工具对象
    private Camera mCamera;

    // 开始的角度
    private float mFromDegrees;
    // 结束的角度
    private float mToDegrees;

    // 定义旋转的中心轴位置
    private int mPivotXType;
    private int mPivotYType;
    private float mPivotXValue;
    private float mPivotYValue;

    private float mPivotX;
    private float mPivotY;

    public RotateYAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue,
                            int pivotYType, float pivotYValue) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;

        mPivotXValue = pivotXValue;
        mPivotXType = pivotXType;
        mPivotYValue = pivotYValue;
        mPivotYType = pivotYType;

        mCamera = new Camera();
    }

    // 初始化操作，计算各种情况下的旋转轴位置
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        switch (mPivotXType) {
            case Animation.ABSOLUTE:
                mPivotX = mPivotXValue;
                break;
            case Animation.RELATIVE_TO_PARENT:
                mPivotX = mPivotXValue * parentWidth;
                break;
            case Animation.RELATIVE_TO_SELF:
                mPivotX = mPivotXValue * width;
                break;
        }

        switch (mPivotYType) {
            case Animation.ABSOLUTE:
                mPivotY = mPivotYValue;
                break;
            case Animation.RELATIVE_TO_PARENT:
                mPivotY = mPivotYValue * parentHeight;
                break;
            case Animation.RELATIVE_TO_SELF:
                mPivotY = mPivotYValue * height;
                break;
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // 根据动画播放时间确认目前旋转到的角度
        float degrees = mFromDegrees + ((mToDegrees - mFromDegrees) * interpolatedTime);
        float scale = getScaleFactor();

        mCamera.save();
        if (mPivotX == 0.0f && mPivotY == 0.0f) {
            mCamera.rotateY(degrees);
            mCamera.getMatrix(t.getMatrix());
        } else {
            mCamera.translate(mPivotX * scale, mPivotY * scale, 0);
            mCamera.rotateY(degrees);
            mCamera.translate(-mPivotX * scale, -mPivotY * scale, 0);
            mCamera.getMatrix(t.getMatrix());
        }
        mCamera.restore();

    }



}
