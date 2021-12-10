package edu.wm.cs.cs301.JustinPark.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class MazePanel extends View {
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;

    public MazePanel(Context context) {
        super(context);
        bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    public MazePanel(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //setColor(Color.RED);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawRect(130, 130, 180, 180, paint);
        canvas.drawBitmap(bitmap, null, new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), paint);
    }

    public void setColor(int rgb){
        paint.setColor(rgb);
    }

    public int getColor(){
        return paint.getColor();
    }
}
