package com.mewlxy.qqlevelbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：luoxingyuan
 * 创建时间：2017/8/20 21:45
 * 修改人：luoxingyuan
 * 修改时间：2017/8/20 21:45
 * 修改备注：
 */

public class QQLevelBar extends View
{

    private Context context;
    private int viewWidth;
    private int viewHeight;
    private int margin;
    private Bitmap sunBitmap;
    private Bitmap moonBitmap;
    private Bitmap starBitmap;
    private Paint bitmapPaint;
    private int level;
    private int step = 5;
    private int drawableResId1;
    private int drawableResId2;
    private int drawableResId3;
    private List<String> list;

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getStep()
    {
        return step;
    }

    public void setStep(int step)
    {
        this.step = step;
    }

    public int getDrawableResId1()
    {
        return drawableResId1;
    }

    public void setDrawableResId1(int drawableResId1)
    {
        this.drawableResId1 = drawableResId1;
    }

    public int getDrawableResId2()
    {
        return drawableResId2;
    }

    public void setDrawableResId2(int drawableResId2)
    {
        this.drawableResId2 = drawableResId2;
    }

    public int getDrawableResId3()
    {
        return drawableResId3;
    }

    public void setDrawableResId3(int drawableResId3)
    {
        this.drawableResId3 = drawableResId3;
    }

    public QQLevelBar(Context context)
    {
        this(context, null, 0);
    }

    public QQLevelBar(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public QQLevelBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.qq_level_view);

        level = typedArray.getInt(R.styleable.qq_level_view_level, 0);
        step = typedArray.getInt(R.styleable.qq_level_view_step, 5);
        drawableResId1 = typedArray.getResourceId(R.styleable.qq_level_view_drawable_1, R.drawable.icon_sun_checked);
        drawableResId2 = typedArray.getResourceId(R.styleable.qq_level_view_drawable_2, R.drawable.icon_moon_checked);
        drawableResId3 = typedArray.getResourceId(R.styleable.qq_level_view_drawable_3, R.drawable.icon_star_checked);
        typedArray.recycle();


        init();
    }


    private void init()
    {
        sunBitmap = BitmapFactory.decodeResource(getResources(), drawableResId1);
        moonBitmap = BitmapFactory.decodeResource(getResources(), drawableResId2);
        starBitmap = BitmapFactory.decodeResource(getResources(), drawableResId3);
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        list = calculateLevel(level);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (widthMode)
        {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                viewWidth = (sunBitmap.getWidth() + sunBitmap.getWidth() / 3) * list.size() + sunBitmap.getWidth() / 2;
                break;
            case MeasureSpec.EXACTLY:
                viewWidth = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }
        switch (heightMode)
        {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                viewHeight = sunBitmap.getHeight() * 2;
                break;
            case MeasureSpec.EXACTLY:
                viewHeight = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }

        setMeasuredDimension(viewWidth, viewHeight);

        margin = sunBitmap.getHeight() / 2;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        int bitmapMargin = sunBitmap.getWidth() / 3;

        for (int i = 0; i < list.size(); i++)
        {

            if (list.get(i).equals("日"))
            {
                canvas.drawBitmap(sunBitmap, (sunBitmap.getWidth() + bitmapMargin) * i + sunBitmap.getWidth() / 2,
                        margin, bitmapPaint);
            } else if (list.get(i).equals("月"))
            {
                canvas.drawBitmap(moonBitmap, (moonBitmap.getWidth() + bitmapMargin) * i + moonBitmap.getWidth() / 2,
                        margin, bitmapPaint);
            } else
            {
                canvas.drawBitmap(starBitmap, (starBitmap.getWidth() + bitmapMargin) * i + starBitmap.getWidth() / 2,
                        margin, bitmapPaint);
            }

        }
    }

    private List<String> calculateLevel(int level)
    {

        List<String> list = new ArrayList<>();

        int sunNum = level / (step * step);
        int moonNum = (level - (step * step) * sunNum) / step;
        int starNum = level - sunNum * (step * step) - moonNum * step;


        for (int i = 0; i < sunNum; i++)
        {
            list.add("日");
        }

        for (int i = 0; i < moonNum; i++)
        {
            list.add("月");
        }

        for (int i = 0; i < starNum; i++)
        {

            list.add("星");
        }


        return list;
    }
}
