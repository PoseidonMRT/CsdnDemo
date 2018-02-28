package com.example.code.animationdrawabledemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BulletBallDrawable bulletBallDrawable = new BulletBallDrawable(Color.parseColor("#f08d43"));
        findViewById(R.id.bullet_ball).setBackground(bulletBallDrawable);
        bulletBallDrawable.start();

        FiveCircleRotateDrawable loopRectangleDrawable = new FiveCircleRotateDrawable();
        findViewById(R.id.five_circle_rotate).setBackground(loopRectangleDrawable);
        loopRectangleDrawable.start();

        Rectangle2CircleDrawable rectangle2CircleDrawable = new Rectangle2CircleDrawable(Color.parseColor("#f08d43"));
        findViewById(R.id.rectangle_circle).setBackground(rectangle2CircleDrawable);
        rectangle2CircleDrawable.start();

        RingRotateDrawable ringRotateDrawable = new RingRotateDrawable(Color.parseColor("#f08d43"));
        findViewById(R.id.ring_rotate).setBackground(ringRotateDrawable);
        ringRotateDrawable.start();

        SubRingRotateDrawable subRingRotateDrawable = new SubRingRotateDrawable(Color.parseColor("#f08d43"));
        findViewById(R.id.sub_ring_rotate).setBackground(subRingRotateDrawable);
        subRingRotateDrawable.start();

        TwoSubRingRotateDrawable twoSubRingRotateDrawable = new TwoSubRingRotateDrawable(Color.parseColor("#f08d43"));
        findViewById(R.id.two_ring).setBackground(twoSubRingRotateDrawable);
        twoSubRingRotateDrawable.start();

        CircualRotateDrawable circualRotateDrawable = new CircualRotateDrawable();
        findViewById(R.id.circual_rotate).setBackground(circualRotateDrawable);
        circualRotateDrawable.start();

        FourCircleRotateDrawable fourCircleRotateDrawable = new FourCircleRotateDrawable();
        findViewById(R.id.four_circle_rotate).setBackground(fourCircleRotateDrawable);
        fourCircleRotateDrawable.start();

        CircleRingDrawable circleRingDrawable = new CircleRingDrawable(Color.parseColor("#f08d43"),Color.parseColor("#f08d43"));
        findViewById(R.id.circle_ring).setBackground(circleRingDrawable);
        circleRingDrawable.start();

        FiveCircleScaleDrawable fiveCircleScaleDrawable = new FiveCircleScaleDrawable();
        findViewById(R.id.five_circle_scale).setBackground(fiveCircleScaleDrawable);
        fiveCircleScaleDrawable.start();

        StrokeRectangleDrawable strokeRectangleDrawable = new StrokeRectangleDrawable(Color.parseColor("#f08d43"));
        findViewById(R.id.stroke_rectangle).setBackground(strokeRectangleDrawable);
        strokeRectangleDrawable.start();

    }
}
