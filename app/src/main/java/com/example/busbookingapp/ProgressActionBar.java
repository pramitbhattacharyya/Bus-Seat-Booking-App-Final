//package com.example.busbookingapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.animation.Animation;
//import android.view.animation.Transformation;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//public class ProgressActionBar extends Animation {
//    private Context context;
//    private ProgressBar prog_bar;
//    private TextView textView;
//    private float from;
//    private float to;
//    public ProgressActionBar(Context context,ProgressBar prog_bar,TextView textView,float from, float to)
//    {
//        this.context=context;
//        this.prog_bar=prog_bar;
//        this.textView=textView;
//        this.from=from;
//        this.to=to;
//    }
//
//    @Override
//    protected void applyTransformation(float interpolatedTime, Transformation t) {
//        super.applyTransformation(interpolatedTime, t);
//        float value=from+(to-from)*interpolatedTime;
//        prog_bar.setProgress((int)value);
//        textView.setText((int)value+"%");
//        if(value==to)
//            context.startActivity(new Intent(context,Developers.class));
//    }
//}
