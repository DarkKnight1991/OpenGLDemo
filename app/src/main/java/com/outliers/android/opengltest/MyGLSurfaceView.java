package com.outliers.android.opengltest;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by nayakasu on 3/6/18.
 */

public class MyGLSurfaceView extends GLSurfaceView{

    MyGLSurfaceRenderer renderer;
    int prevX, prevY;
    float prevX0, prevY0, prevX1, prevY1;
    double prevDis, currDis;
    long fingerDownTime;
    boolean zoomMode;
    GestureDetector gestureDetector;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(3);
        renderer = new MyGLSurfaceRenderer(this);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        //gestureDetector = new GestureDetector(this,this);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getRawX();
        int y = (int) motionEvent.getRawY();
        int touchPoints = motionEvent.getPointerCount();
        MotionEvent.PointerCoords pointerCoords1 = new MotionEvent.PointerCoords();
        MotionEvent.PointerCoords pointerCoords2 = new MotionEvent.PointerCoords();
        //Log.e("touchPoints","count="+touchPoints);

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN :
                //region OldCode
                prevX = x;
                prevY = y;
                if(System.currentTimeMillis() - fingerDownTime < 500 && System.currentTimeMillis() - fingerDownTime > 200){
                    renderer.resetObjects();
                    fingerDownTime = 0;
                }
                fingerDownTime = System.currentTimeMillis();
                //endregion

                zoomMode = motionEvent.getPointerCount() > 1 ? true : false;
                if(zoomMode) {
                    prevX0 = motionEvent.getX(0);
                    prevY0 = motionEvent.getY(0);

                    prevX1 = motionEvent.getX(1);
                    prevY1 = motionEvent.getY(1);

                    prevDis = Math.sqrt((Math.pow((prevX0 - prevX1), 2) + Math.pow((prevY0 - prevY1), 2)));
                }
                break;

            case MotionEvent.ACTION_MOVE :
                //region OldCode
                int dx = x - prevX;
                int dy = y - prevY;
                prevX = x;
                prevY = y;
                //dx = -dx;
                //dy = -dy;
                float factorY = dy*0.003f;
                float factorX = dx*0.003f;

                /*if(!zoomMode){
                    //Log.e("translate",x+","+y);
                    renderer.onTranslate(factorX,factorY);
                }else if(zoomMode){*/
                    //Log.e("rotate", motionEvent.getX(0)+","+motionEvent.getY(0)+"::"+motionEvent.getX(1)+","+motionEvent.getY(1));
                    renderer.onRotate(factorX,factorY);
                //}
                //endregion
                /*if(zoomMode) {
                    float x0 = motionEvent.getX(0);
                    float y0 = motionEvent.getY(0);

                    float x1 = motionEvent.getX(1);
                    float y1 = motionEvent.getY(1);

                    currDis = Math.sqrt((Math.pow((x0-x1), 2) + Math.pow((y0-y1), 2)));

                    double newDis = currDis-prevDis;


                }*/
                break;

            case MotionEvent.ACTION_UP :

                break;
        }
        return true;
    }
}
