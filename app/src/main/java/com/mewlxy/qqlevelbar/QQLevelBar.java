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
    private int step = 5;//默认步进，就是五个星星==一个月亮，QQ是4
    private int drawableResId1;
    private int drawableResId2;
    private int drawableResId3;
    private List<String> list; //这里面的每个元素就代表一个需要绘制的小图标

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

    //重写构造方法，让所有构造方法都指向三个参数的方法。
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

        //获取自定义属性样式列表
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.qq_level_view);

        level = typedArray.getInt(R.styleable.qq_level_view_level, 0);
        step = typedArray.getInt(R.styleable.qq_level_view_step, 5);
        drawableResId1 = typedArray.getResourceId(R.styleable.qq_level_view_drawable_1, R.drawable.icon_sun_checked);
        drawableResId2 = typedArray.getResourceId(R.styleable.qq_level_view_drawable_2, R.drawable.icon_moon_checked);
        drawableResId3 = typedArray.getResourceId(R.styleable.qq_level_view_drawable_3, R.drawable.icon_star_checked);

        //获取完了之后别忘了回收
        typedArray.recycle();


        init();
    }


    //初始化一些资源
    private void init()
    {
        sunBitmap = BitmapFactory.decodeResource(getResources(), drawableResId1);
        moonBitmap = BitmapFactory.decodeResource(getResources(), drawableResId2);
        starBitmap = BitmapFactory.decodeResource(getResources(), drawableResId3);
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        list = calculateLevel(level);


    }

    @Override//View测量
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //获取宽度和高度的测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (widthMode)
        {
            //如果使用者没有明确指定View的尺寸，那么我们就给它设置一个默认值
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED://这里我的默认宽度是根据图片大小和图片数量计算出来的，具体的含义后面会说
                viewWidth = (sunBitmap.getWidth() + sunBitmap.getWidth() / 3) * list.size() + sunBitmap.getWidth() / 2;
                break;
            case MeasureSpec.EXACTLY://如果用户明确指定了尺寸，就按照用户指定的来
                viewWidth = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }
        switch (heightMode)
        {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED://View高度默认设置为图片高度的两倍，如果涉及到换行，这里还需要做动态计算，我就先偷个懒
                viewHeight = sunBitmap.getHeight() * 2;
                break;
            case MeasureSpec.EXACTLY:
                viewHeight = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }

        setMeasuredDimension(viewWidth, viewHeight);//把测量出来的宽高尺寸设置到view中去，这个很重要

        margin = sunBitmap.getHeight() / 2;//上下左右的margin值
    }


    @Override
    protected void onDraw(Canvas canvas)//在这里绘制想要的效果
    {
        int bitmapMargin = sunBitmap.getWidth() / 3;//图片之间的横向间隔

        for (int i = 0; i < list.size(); i++)
        {

            //从list中循环取出元素，根据元素的值来判断该绘制哪种图标
            if (list.get(i).equals("日"))
            {
                //绘制太阳图标，（横坐标为图片宽度+相邻两张图片的间隔）*i+整体左边的margin值，纵坐标为view的高度/2-整体的margin值，下面的绘制同理
                canvas.drawBitmap(sunBitmap, (sunBitmap.getWidth() + bitmapMargin) * i + sunBitmap.getWidth() / 2,
                        viewHeight / 2 - margin, bitmapPaint);
            } else if (list.get(i).equals("月"))
            {
                canvas.drawBitmap(moonBitmap, (moonBitmap.getWidth() + bitmapMargin) * i + moonBitmap.getWidth() / 2,
                        viewHeight / 2 - margin, bitmapPaint);
            } else
            {
                canvas.drawBitmap(starBitmap, (starBitmap.getWidth() + bitmapMargin) * i + starBitmap.getWidth() / 2,
                        viewHeight / 2 - margin, bitmapPaint);
            }

        }

    }

    /**
     * 输入要表示的等级，计算所需要的各种图标的个数，并返回list
     * @param level
     * @return
     */
    private List<String> calculateLevel(int level)
    {

        List<String> list = new ArrayList<>();

        int sunNum = level / (step * step);//太阳图标的个数为：等级/步进的平方（一个太阳为25级）
        int moonNum = (level - (step * step) * sunNum) / step;//月亮的个数：总等级先减去太阳表示的等级/步进值
        int starNum = level - sunNum * (step * step) - moonNum * step;//同理，计算出星星的个数


        //根据不同图标的个数添加不同的元素
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
