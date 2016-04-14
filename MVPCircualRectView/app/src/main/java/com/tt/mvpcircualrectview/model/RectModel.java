package com.tt.mvpcircualrectview.model;


import android.graphics.Rect;

/**
 * Created by TuoZhaoBing on 2016/4/14 0014.
 */
public class RectModel {
    public static final String TAG = "RectModel";
    private int left;
    private int bottom;
    private int right;
    private int top;
    private String text;
    private float angle;

    public RectModel() {
    }

    public RectModel(Rect rect, String text, float angle){
        this.left = rect.left;
        this.right = rect.right;
        this.top = rect.top;
        this.bottom = rect.bottom;
        this.text = text;
        this.angle = angle;
    }

    public RectModel(int left, int bottom, int right, int top, String text, float angle) {

        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
        this.text = text;
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCenterX(){
        return (left+right)/2;
    }

    public int getCenterY(){
        return (top+bottom)/2;
    }

    public int getWidth(){
        return right-left;
    }
}
