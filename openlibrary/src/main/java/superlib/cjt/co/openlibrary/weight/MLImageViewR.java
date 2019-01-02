package superlib.cjt.co.openlibrary.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import superlib.cjt.co.openlibrary.R;


/**
 * Created by wupeitao on 2018/1/9.
 */

/***
 * 设置单个角圆角
 */
@SuppressLint("AppCompatCustomView")
public class MLImageViewR extends ImageView {

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    public MLImageViewR(Context context) {
        super(context);
    }

    public MLImageViewR(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MLImageViewR);
            radius_btm_left = array.getDimensionPixelSize(R.styleable.MLImageViewR_radius_btm_left, radius_btm_left);
            radius_btm_right = array.getDimensionPixelSize(R.styleable.MLImageViewR_radius_btm_right, radius_btm_right);
            radius_top_left = array.getDimensionPixelSize(R.styleable.MLImageViewR_radius_top_left, radius_top_left);
            radius_top_right = array.getDimensionPixelSize(R.styleable.MLImageViewR_radius_top_right, radius_top_right);
            /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
            rids[0] = radius_top_left;
            rids[1] = radius_top_left;
            rids[2] = radius_top_right;
            rids[3] = radius_top_right;
            rids[4] = radius_btm_right;
            rids[5] = radius_btm_right;
            rids[6] = radius_btm_left;
            rids[7] = radius_btm_left;
            array.recycle();
        }
    }

    int radius_btm_left;
    int radius_btm_right;
    int radius_top_left;
    int radius_top_right;

    public MLImageViewR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MLImageViewR);
            radius_btm_left = array.getDimensionPixelSize(R.styleable.MLImageViewR_radius_btm_left, radius_btm_left);
            radius_btm_right = array.getDimensionPixelSize(R.styleable.MLImageViewR_radius_btm_right, radius_btm_right);
            radius_top_left = array.getDimensionPixelSize(R.styleable.MLImageViewR_radius_top_left, radius_top_left);
            radius_top_right = array.getDimensionPixelSize(R.styleable.MLImageViewR_radius_top_right, radius_top_right);
            /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
            rids[0] = radius_top_left;
            rids[1] = radius_top_left;
            rids[2] = radius_top_right;
            rids[3] = radius_top_right;
            rids[4] = radius_btm_right;
            rids[5] = radius_btm_right;
            rids[6] = radius_btm_left;
            rids[7] = radius_btm_left;
            array.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        /**
         * 去除锯齿
         */
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG));
        super.onDraw(canvas);
    }
}
