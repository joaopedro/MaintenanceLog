package com.smartech.maintenancelog.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartech.maintenancelog.R;
import com.smartech.maintenancelog.dummy.DummyContent;

public class MaintenanceRowAdapter extends ArrayAdapter<DummyContent.DummyItem> {
 
        private final Context context;
        private final List<DummyContent.DummyItem> itemsArrayList;
 
        public MaintenanceRowAdapter(Context context, List<DummyContent.DummyItem> itemsArrayList) {
 
            super(context, R.layout.layout_maintenance_row, itemsArrayList);
 
            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            // 1. Create inflater 
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.layout_maintenance_row, parent, false);
 

            TextView labelView = (TextView) rowView.findViewById(R.id.maintenance_detail_label);

            ImageView periodicityView = (ImageView) rowView.findViewById(R.id.maintenance_detail_periodicity);

 
            // 4. Set the text for textView 
            labelView.setText(itemsArrayList.get(position).content);

            int anInt = randInt(1,4);

            if(anInt==1) createDaily(periodicityView);
            if(anInt==2) createWeekly(periodicityView);
            if(anInt==3) createMonthly(periodicityView);
            if(anInt==4) createYearly(periodicityView);

            // 5. retrn rowView
            return rowView;
        }

    private void createDaily(ImageView periodicityView) {
        Bitmap b = Bitmap.createBitmap(40, 50, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        c.drawColor(Color.BLUE, PorterDuff.Mode.ADD);

        c.drawText("D", 18, 40, paint);
        periodicityView.setImageBitmap(b);
    }
    private void createWeekly(ImageView periodicityView) {
        Bitmap b = Bitmap.createBitmap(40, 50, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        c.drawColor(Color.LTGRAY, PorterDuff.Mode.ADD);

        c.drawText("S", 18, 40, paint);
        periodicityView.setImageBitmap(b);
    }
    private void createMonthly(ImageView periodicityView) {
        Bitmap b = Bitmap.createBitmap(40, 50, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        c.drawColor(Color.GREEN, PorterDuff.Mode.ADD);

        c.drawText("M", 18, 40, paint);
        periodicityView.setImageBitmap(b);
    }
    private void createYearly(ImageView periodicityView) {
        Bitmap b = Bitmap.createBitmap(40, 50, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        c.drawColor(Color.YELLOW, PorterDuff.Mode.ADD);

        c.drawText("A", 18, 40, paint);
        periodicityView.setImageBitmap(b);
    }

    private int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}