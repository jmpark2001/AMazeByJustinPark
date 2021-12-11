package edu.wm.cs.cs301.JustinPark.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.fonts.Font;
import android.util.AttributeSet;
import android.view.View;

public class MazePanel extends View implements P5PanelF21{
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;
    private String fontName;
    private int fontStyle, fontSize;

    public MazePanel(Context context) {
        super(context);
        bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    public MazePanel(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, null, new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), paint);
    }

    public Canvas bufferGraphics(){
        if(bitmap == null){
            init();
        }
        return canvas;
    }

    private void init(){
        bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    public void commit() {
        invalidate();
    }

    /**
     * Checks if graphics is null or not
     */
    @Override
    public boolean isOperational() {
        return canvas != null;
    }

    /**
     * Create color object and set graphics color
     */
    @Override
    public void setColor(int rgb){
        paint.setColor(rgb);
    }

    /**
     * return color
     */
    @Override
    public int getColor(){
        return paint.getColor();
    }

    /**
     * return color with string parameter
     * @return
     */
    public static int getColor(String color) {
        return Color.parseColor(color);
    }

    /**
     * Create color object with a float array
     * Set graphics color
     * @param rgba
     */
    public void setColor(float[] rgba) {
        paint.setColor(Color.argb(rgba[3], rgba[0], rgba[1], rgba[2]));
    }

    /**
     * return font name given parameter
     * @param fontCode
     * @return

    public static String getFontName(String fontCode) {
        return Font.decode(fontCode).getFontName();
    }
    */
    /**
     * return font style given parameter
     * @param fontCode
     * @return

    public static int getFontStyle(String fontCode) {
        return Font.decode(fontCode).getStyle();
    }
    */
    /**
     * return font size given parameter
     * @param fontCode
     * @return

    public static int getFontSize(String fontCode) {
        return Font.decode(fontCode).getSize();
    }
    */
    /**
     * set font name to parameter
     * @param name
     */
    public void setFontName(String name) {
        fontName = name;
    }

    /**
     * set font style to parameter
     * @param style
     */
    public void setFontStyle(int style) {
        fontStyle = style;
    }

    /**
     * set font size to parameter
     * @param size
     */
    public void setFontSize(int size) {
        fontSize = size;
    }

    @Override
    public void addBackground(float percentToExit) {
        // black rectangle in upper half of screen
        // graphics.setColor(Color.black);
        // dynamic color setting:
        //bufferGraphics().setColor(getBackgroundColor(percentToExit, true));
        //graphics.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT/2);
        // grey rectangle in lower half of screen
        // graphics.setColor(Color.darkGray);
        // dynamic color setting:
        //graphics.setColor(getBackgroundColor(percentToExit, false));
        //graphics.fillRect(0, Constants.VIEW_HEIGHT/2, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT/2);
        setColor(Color.BLACK);
        addFilledRectangle(0, 0, canvas.getWidth(), canvas.getHeight()/2);
        setColor(Color.DKGRAY);
        addFilledRectangle(0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2);
    }

    /**
     * Determine the background color for the top and bottom
     * rectangle as a blend between starting color settings
     * of yellowWM and lightGray towards goldWM and greenWM as final
     * color settings close to the exit
     * @param percentToExit describes how far it is to the exit as a percentage value
     * @param top is true for the top rectangle, false for the bottom
     * @return the color to use for the background rectangle
     */
    /**
    private Color getBackgroundColor(float percentToExit, boolean top) {

        return top? blend(yellowWM, goldWM, percentToExit) :
                blend(Color.lightGray, greenWM, percentToExit);
    }
    */
    /**
     * Calculates the weighted average of the two given colors.
     * The weight for the first color is expected to be between
     * 0 and 1. The weight for the other color is then 1-weight0.
     * The result is the weighted average of the red, green, and
     * blue components of the colors. The resulting alpha value
     * for transparency is the max of the alpha values of both colors.
     * @param fstColor is the first color
     * @param sndColor is the second color
     * @param weightFstColor is the weight of fstColor, {@code 0.0 <= weightFstColor <= 1.0}
     * @return blend of both colors as weighted average of their rgb values
     */

    private Color blend(Color fstColor, Color sndColor, double weightFstColor) {
        /**
        if (weightFstColor < 0.1)
            return sndColor;
        if (weightFstColor > 0.95)
            return fstColor;
        double r = weightFstColor * fstColor.getRed() + (1-weightFstColor) * sndColor.getRed();
        double g = weightFstColor * fstColor.getGreen() + (1-weightFstColor) * sndColor.getGreen();
        double b = weightFstColor * fstColor.getBlue() + (1-weightFstColor) * sndColor.getBlue();
        double a = Math.max(fstColor.getAlpha(), sndColor.getAlpha());

        return new Color((int) r, (int) g, (int) b, (int) a);
         */
        return fstColor;
    }

    @Override
    public void addFilledRectangle(int x, int y, int width, int height) {
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(3);
        bufferGraphics().drawRect((float)x, (float)y, (float)x+width, (float)y+height, paint);
    }

    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Path path = new Path();
        path.moveTo(xPoints[0], yPoints[0]);
        for(int i = 1; i<nPoints; i++){
            path.lineTo(xPoints[i], yPoints[i]);
        }
        path.lineTo(xPoints[0], yPoints[0]);
        bufferGraphics().drawPath(path, paint);
    }

    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(xPoints[0], yPoints[0]);
        for(int i = 1; i<nPoints; i++){
            path.lineTo(xPoints[i], yPoints[i]);
        }
        path.lineTo(xPoints[0], yPoints[0]);
        bufferGraphics().drawPath(path, paint);
    }

    @Override
    public void addLine(int startX, int startY, int endX, int endY) {
        bufferGraphics().drawLine((float)startX, (float)startY, (float)endX, (float)endY, paint);
    }

    @Override
    public void addFilledOval(int x, int y, int width, int height) {
        bufferGraphics().drawOval((float)x, (float)y, (float)width+x, (float)height+y, paint);
    }

    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        Path path = new Path();
        path.addArc(x, y, width, height, startAngle, arcAngle);
        bufferGraphics().drawPath(path, paint);
    }

    @Override
    public void addMarker(float x, float y, String str) {
        /**
        Font f = new Font(fontName, fontStyle, fontSize);
        GlyphVector gv = f.createGlyphVector(graphics.getFontRenderContext(), str);
        Rectangle2D rect = gv.getVisualBounds();
        // need to update x, y by half of rectangle width, height
        // to serve as x, y coordinates for drawing a GlyphVector
        x -= rect.getWidth() / 2;
        y += rect.getHeight() / 2;

        graphics.drawGlyphVector(gv, x, y);
         */
    }

    @Override
    public void setRenderingHint(P5RenderingHints hintKey, P5RenderingHints hintValue) {
        /**
        switch (hintKey) {
            case KEY_RENDERING:
                if (hintValue == RenderingHints.VALUE_ANTIALIAS_ON) {
                    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_ANTIALIAS_ON);
                }
                else if (hintValue == RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
                    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                }
                else {
                    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                }
                break;
            case KEY_ANTIALIASING:
                if (hintValue == RenderingHints.VALUE_ANTIALIAS_ON) {
                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                }
                else if (hintValue == RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                }
                else {
                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_RENDER_QUALITY);
                }
                break;
            case KEY_INTERPOLATION:
                if (hintValue == RenderingHints.VALUE_ANTIALIAS_ON) {
                    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_ANTIALIAS_ON);
                }
                else if (hintValue == RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
                    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                }
                else {
                    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_RENDER_QUALITY);
                }
            default:
                break;
        }
         */
    }
    /**
    private void myTestImage(Canvas c){
        addFilledRectangle(0,0,50,100);
        paint.setColor(Color.GREEN);
        addFilledOval(200, 200, 80, 80);
        paint.setColor(Color.RED);
        addFilledOval(50, 50, 50, 50);
        paint.setColor(Color.BLUE);
        addFilledPolygon(new int[]{100, 200, 200, 100, 100}, new int[]{50, 100, 200, 250, 50}, 4);
        addPolygon(new int[]{300, 200, 200, 300, 300}, new int[]{50, 100, 200, 250, 50}, 4);
        paint.setColor(Color.BLACK);
        addLine(0, 0, 400, 400);
        addArc(300, 300, 50, 50, 0, 180);
    }*/
}
