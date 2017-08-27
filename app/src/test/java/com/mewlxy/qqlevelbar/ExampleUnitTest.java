package com.mewlxy.qqlevelbar;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest//类名可以随便取，不过最好是有实际意义的名称
{
    @Test//所有的测试都必须加上@Test注解(非常重要！！)，这样测试框架才能识别你的代码，并把它当作单元测试进行处理
    public void addition_isCorrect() throws Exception
    {
        assertEquals(4, 2 + 2);
    }

    //测试无参无返回值的函数
    @Test
    public void printText()
    {
        System.out.println("Hello test!");
    }

    //测试带参数和返回值的函数
    @Test
    public void printTextWithArgs()
    {
        System.out.println(text("Hello test with args!"));
    }

    //被测试的函数
    private String text(String text)
    {
        return text;
    }

    @Test
    public void complexFun()
    {
        List<String> list = calculateLevel(111);
        System.out.print(list);
    }

    //复杂函数的测试
    private List<String> calculateLevel(int level)
    {

        List<String> list = new ArrayList<>();

        int sunNum = level / (5 * 5);
        int moonNum = (level - (5 * 5) * sunNum) / 5;
        int starNum = level - sunNum * (5 * 5) - moonNum * 5;


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