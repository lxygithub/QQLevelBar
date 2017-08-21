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
    private Bitmap defaultBitmap;
    private Paint bitmapPaint;
    private int level;
    private List<String> list;

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
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
        typedArray.recycle();


        init();
    }


    private void init()
    {
        sunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_sun_checked);
        moonBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_moon_checked);
        starBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_star_checked);
        defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_star_uncheck);
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
                viewWidth = defaultBitmap.getWidth() * 7 + defaultBitmap.getWidth() / 3;
                break;
            case MeasureSpec.EXACTLY:
                viewWidth = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }
        switch (heightMode)
        {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                viewHeight = defaultBitmap.getHeight() * 2;
                break;
            case MeasureSpec.EXACTLY:
                viewHeight = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }

        setMeasuredDimension(viewWidth, viewHeight);

        margin = defaultBitmap.getHeight() / 2;
    }



    @Override
    protected void onDraw(Canvas canvas)
    {
        int bitmapMargin = defaultBitmap.getWidth() / 3;

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
            } else if (list.get(i).equals("星"))
            {
                canvas.drawBitmap(starBitmap, (starBitmap.getWidth() + bitmapMargin) * i + starBitmap.getWidth() / 2,
                        margin, bitmapPaint);
            } else
            {
                canvas.drawBitmap(defaultBitmap, (defaultBitmap.getWidth() + bitmapMargin) * i + defaultBitmap.getWidth() / 2,
                        margin, bitmapPaint);
            }

        }
    }

    private List<String> calculateLevel(int level)
    {

        List<String> list = new ArrayList<>();
        if (level > 125)
        {
            level = 125;
        }
        int sunNum = level / 25;
        int moonNum = (level - 25 * sunNum) / 5;
        int starNum = level - sunNum * 25 - moonNum * 5;


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

        if (list.size() < 5)
        {
            while (list.size() < 5)
            {
                list.add("空");
            }
        }
        if (list.size() > 5)
        {
            list = list.subList(0, 5);
        }

        return list;
    }
}
