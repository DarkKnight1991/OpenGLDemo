package com.outliers.android.opengltest;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by nayakasu on 3/6/18.
 */

public class MyGLSurfaceRenderer implements GLSurfaceView.Renderer {

    //MyTriangle triangle;
    //MyTwoSquares squares;
    MyCube myCube;
    float[] projectionMatrix = new float[16];
    float[] modelViewMatrix = new float[16];
    float[] mvpMatrix = new float[16];
    float eyeX=0,eyeY=3,eyeZ=5,centerX=0,centerY=0,centerZ=0,upX=0,upY=1,upZ=0;
    MyGLSurfaceView glSurfaceView;
    int zNear = 1;
    int zFar = 10;
    float[] rotationMatrixX = new float[16];
    float[] rotationMatrixY = new float[16];
    float[] rotationMatrix = new float[16];
    float[] rotation = new float[16];
    float angle,degreeX,degreeY;

    public MyGLSurfaceRenderer(MyGLSurfaceView glSurfaceView){
        this.glSurfaceView = glSurfaceView;
        Log.e("MyGLRenderer","renderer");
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES30.glClearColor(0.5f, 0.5f,0.5f, 1.0f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        //GLES30.glDepthMask(false); makes depth buffer readOnly.. Opengl can't change it
        //triangle = new MyTriangle(this);
        Matrix.setIdentityM(rotationMatrixX,0);
        Matrix.setIdentityM(rotationMatrixY,0);
        Matrix.setLookAtM(modelViewMatrix,0,eyeX,eyeY,eyeZ,centerX,centerY,centerZ,upX,upY,upZ);
        //squares = new MyTwoSquares(this);
        myCube = new MyCube(this);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES30.glViewport(0,0, width, height);
        float ratio = (float) width/height;
        //Matrix.frustumM(projectionMatrix,0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.perspectiveM(projectionMatrix,0,30,ratio,zNear,zFar);
        Matrix.multiplyMM(mvpMatrix,0, projectionMatrix,0,modelViewMatrix, 0);
        rotation = mvpMatrix;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        //draw
        //triangle.draw();
        //squares.draw(rotation);
        myCube.draw(rotation);
    }

    public static int loadShader(int type, String sourceCode){
        int shader = GLES30.glCreateShader(type);
        Log.e("createShader", GLU.gluErrorString(GLES30.glGetError()));
        GLES30.glShaderSource(shader, sourceCode);
        GLES30.glCompileShader(shader);
        IntBuffer statusBuff = IntBuffer.allocate(1);
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, statusBuff);
        if(statusBuff.get(0) != GLES30.GL_NO_ERROR){
            //Error
            Log.e("ShaderLoad", GLU.gluErrorString(GLES30.glGetError()));
        }
        return shader;
    }

    public void onTranslate(float factorX, float factorY){
        eyeZ += factorY;
        if(eyeZ <= -6)
            eyeZ = -6;

        if(eyeZ >= 6)
            eyeZ = 6;

        eyeX += factorX;
        if(eyeX <= -6)
            eyeX = -6;

        if(eyeX >= 6)
            eyeX = 6;


        Matrix.setLookAtM(modelViewMatrix,0,eyeX,eyeY,eyeZ,centerX,centerY,centerZ,upX,upY,upZ);
        Matrix.multiplyMM(mvpMatrix,0, projectionMatrix,0,modelViewMatrix, 0);
        rotation = mvpMatrix;
        glSurfaceView.requestRender();
        //Log.e("onTranslate","eyeZ="+eyeZ+",fac:"+factorX+","+factorY);
    }

    public void onRotate(float factorX, float factorY){
        int xAxisComponent = 0;
        int yAxisComponent = 0;
        int zAxisComponent = 0;

        if(factorX != 0){
            yAxisComponent = 1;
            degreeY += 10 * factorX;
            Matrix.setRotateM(rotationMatrixX,0,degreeY,0,1,0);
        }

        if(factorY != 0){
            xAxisComponent = 1;
            degreeX += 10 * factorY;
            Matrix.setRotateM(rotationMatrixY,0,degreeX,1,0,0);
        }
        Matrix.setLookAtM(modelViewMatrix,0,eyeX,eyeY,eyeZ,centerX,centerY,centerZ,upX,upY,upZ);
        Matrix.multiplyMM(mvpMatrix,0, projectionMatrix,0,modelViewMatrix, 0);
        //long time = SystemClock.uptimeMillis() % 4000L;
        angle += 10*factorX;
        Log.e("angle",degreeX+","+degreeY);
        //Matrix.setRotateM(rotationMatrix, 0, angle, 0, 1.0f, 0);
        Matrix.multiplyMM(rotationMatrix,0, rotationMatrixX,0,rotationMatrixY,0);
        Matrix.multiplyMM(rotation, 0, mvpMatrix, 0, rotationMatrix, 0);
        glSurfaceView.requestRender();
    }

    public void resetObjects(){
        eyeX = 0;
        eyeY = 3;
        eyeZ = 5;
        angle = 0;
        degreeX = 0;
        degreeY = 0;
        Matrix.setIdentityM(rotationMatrixX,0);
        Matrix.setIdentityM(rotationMatrixY,0);
        //triangle.draw();
        Matrix.setLookAtM(modelViewMatrix,0,eyeX,eyeY,eyeZ,centerX,centerY,centerZ,upX,upY,upZ);
        Matrix.multiplyMM(mvpMatrix,0, projectionMatrix,0,modelViewMatrix, 0);
        rotation = mvpMatrix;
        glSurfaceView.requestRender();
    }
}
