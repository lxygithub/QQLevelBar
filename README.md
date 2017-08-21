## 仿QQ等级显示

#### 效果：


![效果](images/TIM截图20170821135937.png)

#### 使用：

      <com.mewlxy.qqlevelbar.QQLevelBar
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:level="111"
            app:step="4"/>
            
#### 一些自定义属性：
     <declare-styleable name="qq_level_view">
             <attr name="level" format="integer"/><!--设置等级-->
             <attr name="step" format="integer"/><!--设置步进-->
             <attr name="drawable_1" format="reference"/><!--一级图片-->
             <attr name="drawable_2" format="reference"/><!--二级图片-->
             <attr name="drawable_3" format="reference"/><!--三级图片-->
     </declare-styleable>

