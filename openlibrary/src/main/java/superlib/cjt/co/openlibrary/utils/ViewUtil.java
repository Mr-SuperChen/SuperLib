/*
 * Copyright (C) 2012 www.amsoft.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package superlib.cjt.co.openlibrary.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn 名称：AbViewUtil.java 描述：View工具类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-01-17 下午11:52:13
 */

public class ViewUtil {

    /**
     * 无效值
     */
    public static final int INVALID = Integer.MIN_VALUE;


    /**
     * 测量这个view 最后通过getMeasuredWidth()获取宽度和高度.
     *
     * @param view 要测量的view
     * @return 测量过的view
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 获得这个View的宽度 测量这个view，最后通过getMeasuredWidth()获取宽度.
     *
     * @param view 要测量的view
     * @return 测量过的view的宽度
     */
    public static int getViewWidth(View view) {
        measureView(view);
        return view.getMeasuredWidth();
    }

    /**
     * 获得这个View的高度 测量这个view，最后通过getMeasuredHeight()获取高度.
     *
     * @param view 要测量的view
     * @return 测量过的view的高度
     */
    public static int getViewHeight(View view) {
        measureView(view);
        return view.getMeasuredHeight();
    }

    /**
     * 从父亲布局中移除自己
     *
     * @param v
     */
    public static void removeSelfFromParent(View v) {
        ViewParent parent = v.getParent();
        if (parent != null) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(v);
            }
        }
    }

    /**
     * 描述：dip转换为px.
     *
     * @param context  the context
     * @param dipValue the dip value
     * @return px值
     */
    public static float dip2px(Context context, float dipValue) {
        DisplayMetrics mDisplayMetrics = AppUtil.getDisplayMetrics(context);
        return applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
                mDisplayMetrics);
    }

    /**
     * 描述：px转换为dip.
     *
     * @param context the context
     * @param pxValue the px value
     * @return dip值
     */
    public static float px2dip(Context context, float pxValue) {
        DisplayMetrics mDisplayMetrics = AppUtil.getDisplayMetrics(context);
        return pxValue / mDisplayMetrics.density;
    }

    /**
     * 描述：sp转换为px.
     *
     * @param context the context
     * @param spValue the sp value
     * @return sp值
     */
    public static float sp2px(Context context, float spValue) {
        DisplayMetrics mDisplayMetrics = AppUtil.getDisplayMetrics(context);
        return applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                mDisplayMetrics);
    }

    /**
     * 描述：px转换为sp.
     *
     * @param context the context
     *                the sp value
     * @return sp值
     */
    public static float px2sp(Context context, float pxValue) {
        DisplayMetrics mDisplayMetrics = AppUtil.getDisplayMetrics(context);
        return pxValue / mDisplayMetrics.scaledDensity;
    }

    /**
     * 描述：根据屏幕大小缩放.
     *
     * @param context the context
     *                the px value
     * @return the int
     */
    public static int scale(Context context, float value) {
        DisplayMetrics mDisplayMetrics = AppUtil.getDisplayMetrics(context);
        return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels,
                value);
    }


    /**
     * UI设计的基准宽度.
     */
    public static int UI_WIDTH = 750;

    /**
     * UI设计的基准高度.
     */
    public static int UI_HEIGHT = 1334;

    /**
     * 描述：根据屏幕大小缩放.
     *
     * @param displayWidth  the display width
     * @param displayHeight the display height
     * @param pxValue       the px value
     * @return the int
     */
    public static int scale(int displayWidth, int displayHeight, float pxValue) {
        if (pxValue == 0) {
            return 0;
        }
        float scale = 1;
        try {
            float scaleWidth = (float) displayWidth / UI_WIDTH;
            float scaleHeight = (float) displayHeight / UI_HEIGHT;
            scale = Math.min(scaleWidth, scaleHeight);
        } catch (Exception e) {
        }
        return Math.round(pxValue * scale + 0.5f);
    }

    /**
     * TypedValue官方源码中的算法，任意单位转换为PX单位
     *
     * @param unit    TypedValue.COMPLEX_UNIT_DIP
     * @param value   对应单位的值
     * @param metrics 密度
     * @return px值
     */
    public static float applyDimension(int unit, float value,
                                       DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    /**
     * 描述：View树递归调用做适配. AbAppConfig.uiWidth = 1080; AbAppConfig.uiHeight = 700;
     * scaleContentView((RelativeLayout)findViewById(R.id.rootLayout));
     * 要求布局中的单位都用px并且和美工的设计图尺寸一致，包括所有宽高，Padding,Margin,文字大小
     *
     * @param contentView
     */
    public static void scaleContentView(ViewGroup contentView) {
        ViewUtil.scaleView(contentView);
        if (contentView.getChildCount() > 0) {
            for (int i = 0; i < contentView.getChildCount(); i++) {
                if (contentView.getChildAt(i) instanceof ViewGroup) {
                    scaleContentView((ViewGroup) (contentView.getChildAt(i)));
                } else {
                    scaleView(contentView.getChildAt(i));
                }
            }
        }
    }

    /**
     * 按比例缩放View，以布局中的尺寸为基准
     *
     * @param view
     */
    public static void scaleView(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            setTextSize(textView, textView.getTextSize());
        }

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view
                .getLayoutParams();
        if (null != params) {
            int width = INVALID;
            int height = INVALID;
            if (params.width != ViewGroup.LayoutParams.WRAP_CONTENT
                    && params.width != ViewGroup.LayoutParams.MATCH_PARENT) {
                width = params.width;
            }

            if (params.height != ViewGroup.LayoutParams.WRAP_CONTENT
                    && params.height != ViewGroup.LayoutParams.MATCH_PARENT) {
                height = params.height;
            }

            // size
            setViewSize(view, width, height);

            // Padding
            setPadding(view, view.getPaddingLeft(), view.getPaddingTop(),
                    view.getPaddingRight(), view.getPaddingBottom());
        }

        // Margin
        if (view.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams mMarginLayoutParams = (MarginLayoutParams) view
                    .getLayoutParams();
            if (mMarginLayoutParams != null) {
                setMargin(view, mMarginLayoutParams.leftMargin,
                        mMarginLayoutParams.topMargin,
                        mMarginLayoutParams.rightMargin,
                        mMarginLayoutParams.bottomMargin);
            }
        }

    }

    /**
     * 缩放文字大小
     *
     * @param textView button
     * @param size     sp值
     * @return
     */
    public static void setSPTextSize(TextView textView, float size) {
        float scaledSize = scale(textView.getContext(), size);
        textView.setTextSize(scaledSize);
    }

    /**
     * 缩放文字大小,这样设置的好处是文字的大小不和密度有关， 能够使文字大小在不同的屏幕上显示比例正确
     *
     * @param textView   button
     * @param sizePixels px值
     * @return
     */
    public static void setTextSize(TextView textView, float sizePixels) {
        float scaledSize = scale(textView.getContext(), sizePixels);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
    }

    /**
     * 缩放文字大小
     *
     * @param context
     * @param textPaint
     * @param sizePixels px值
     * @return
     */
    public static void setTextSize(Context context, TextPaint textPaint,
                                   float sizePixels) {
        float scaledSize = scale(context, sizePixels);
        textPaint.setTextSize(scaledSize);
    }

    /**
     * 缩放文字大小
     *
     * @param context
     * @param sizePixels px值
     * @return
     */
    public static void setText(Context context, TextView textView,
                               String sizePixels) {
        if (textView != null) {
            if (!TextUtils.isEmpty(sizePixels)) {
                textView.setText(sizePixels);
            } else {
                textView.setText("");
            }
        }
    }

    /**
     * 缩放文字大小
     *
     * @param context
     * @param paint
     * @param sizePixels px值
     * @return
     */
    public static void setTextSize(Context context, Paint paint, float sizePixels) {
        float scaledSize = scale(context, sizePixels);
        paint.setTextSize(scaledSize);
    }

    /**
     * 设置View的PX尺寸
     *
     * @param view         如果是代码new出来的View，需要设置一个适合的LayoutParams
     * @param widthPixels
     * @param heightPixels
     */
    public static void setViewSize(View view, int widthPixels, int heightPixels) {
        int scaledWidth = scale(view.getContext(), widthPixels);
        int scaledHeight = scale(view.getContext(), heightPixels);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            L.e("setViewSize出错,如果是代码new出来的View，需要设置一个适合的LayoutParams");
            return;
        }
        if (widthPixels != INVALID) {
            params.width = scaledWidth;
        }
        if (heightPixels != INVALID) {
            params.height = scaledHeight;
        }
        view.setLayoutParams(params);
    }

    /**
     * 设置PX padding.
     *
     * @param view   the view
     * @param left   the left padding in pixels
     * @param top    the top padding in pixels
     * @param right  the right padding in pixels
     * @param bottom the bottom padding in pixels
     */
    public static void setPadding(View view, int left, int top, int right,
                                  int bottom) {
        int scaledLeft = scale(view.getContext(), left);
        int scaledTop = scale(view.getContext(), top);
        int scaledRight = scale(view.getContext(), right);
        int scaledBottom = scale(view.getContext(), bottom);
        view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
    }

    /**
     * 设置 PX margin.
     *
     * @param view   the view
     * @param left   the left margin in pixels
     * @param top    the top margin in pixels
     * @param right  the right margin in pixels
     * @param bottom the bottom margin in pixels
     */
    public static void setMargin(View view, int left, int top, int right,
                                 int bottom) {
        int scaledLeft = scale(view.getContext(), left);
        int scaledTop = scale(view.getContext(), top);
        int scaledRight = scale(view.getContext(), right);
        int scaledBottom = scale(view.getContext(), bottom);

        if (view.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams mMarginLayoutParams = (MarginLayoutParams) view
                    .getLayoutParams();
            if (mMarginLayoutParams != null) {
                if (left != INVALID) {
                    mMarginLayoutParams.leftMargin = scaledLeft;
                }
                if (right != INVALID) {
                    mMarginLayoutParams.rightMargin = scaledRight;
                }
                if (top != INVALID) {
                    mMarginLayoutParams.topMargin = scaledTop;
                }
                if (bottom != INVALID) {
                    mMarginLayoutParams.bottomMargin = scaledBottom;
                }
                view.setLayoutParams(mMarginLayoutParams);
            }
        }

    }


    public static void setHeightRelativeLayout(Activity activity, ViewGroup view, int mwidth, int mheight) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）
        int height = dm.heightPixels; // 屏幕高度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);//屏幕宽度(dp)
        int screenHeight = (int) (height / density);//屏幕高度(dp)


        double div = BigDecimalUtil.div(mheight, mwidth);
        double mul = BigDecimalUtil.mul(div, width);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) mul);
        view.setLayoutParams(layoutParams);
    }

    public static void setHeightRelativeLayout(Context activity, ViewGroup view, int mwidth, int mheight) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）
        int height = dm.heightPixels; // 屏幕高度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);//屏幕宽度(dp)
        int screenHeight = (int) (height / density);//屏幕高度(dp)

        double div = BigDecimalUtil.div(mheight, mwidth);
        double mul = BigDecimalUtil.mul(div, width);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) mul);
        view.setLayoutParams(layoutParams);
    }

//    public static void setHeightRelativeLayout(Context activity, ViewGroup view, float scale, int mheight) {
//        setHeightRelativeLayoutDifference(activity, view, scale, 50);
//    }

//    public static void setHeightRelativeLayoutDifference(Context activity, ViewGroup view, float scale, int difference) {
//        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;// 屏幕宽度（像素）
//        int height = dm.heightPixels; // 屏幕高度（像素）
//        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
//        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        int screenWidth = (int) (width / density);//屏幕宽度(dp)
//        int screenHeight = (int) (height / density);//屏幕高度(dp)
//
//        double mul = BigDecimalUtil.mul(scale, width - AppController.getInstance().dp2px(difference));
//        L.e("最后的宽度：" + mul);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) mul);
//        view.setLayoutParams(layoutParams);
//    }

//    public static void setHeightRelativeLayoutDifference(Context activity, Banner view, float scale, int difference) {
//        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;// 屏幕宽度（像素）
//        int height = dm.heightPixels; // 屏幕高度（像素）
//        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
//        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        int screenWidth = (int) (width / density);//屏幕宽度(dp)
//        int screenHeight = (int) (height / density);//屏幕高度(dp)
//
//        double mul = BigDecimalUtil.mul(scale, width - AppController.getInstance().dp2px(difference));
//        L.e("最后的宽度：" + mul);
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//        layoutParams.height = (int) mul;
//        view.setLayoutParams(layoutParams);
//    }

//    public static void setHeightConstraintLayoutDifference(Context activity, ViewGroup view, float scale, int difference) {
//        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;// 屏幕宽度（像素）
//        int height = dm.heightPixels; // 屏幕高度（像素）
//        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
//        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        int screenWidth = (int) (width / density);//屏幕宽度(dp)
//        int screenHeight = (int) (height / density);//屏幕高度(dp)
//
//        double mul = BigDecimalUtil.mul(scale, width - AppController.getInstance().dp2px(difference));
//        L.e("最后的宽度：" + mul);
//        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, (int) mul);
//        view.setLayoutParams(layoutParams);
//    }


    /***
     *
     * @param activity
     * @param view
     * @param scale 0.38
     * @param widthScale 0.48
     */
//    public static void setHeightWithRelativeLayoutDifference(Context activity, RelativeLayout view, float scale, float widthScale) {
//        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;// 屏幕宽度（像素）
//        int height = dm.heightPixels; // 屏幕高度（像素）
//
//        double screenWidth = BigDecimalUtil.sub(width, AppController.getInstance().dp2px(47f));
//
//        double viewWidth = BigDecimalUtil.mul(screenWidth, widthScale);
//
//        double viewHeight = BigDecimalUtil.mul(scale, viewWidth);
//
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//        layoutParams.height = (int) viewHeight;
//        layoutParams.width = (int) viewWidth;
//        view.setLayoutParams(layoutParams);
//    }


    /***
     *
     * @param activity
     * @param view
     * @param scale 0.38
     * @param widthScale 0.48
     */
//    public static void setHeightWithConstraintLayoutDifference(Context activity, ImageView view, float scale, float widthScale) {
//        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;// 屏幕宽度（像素）
//        int height = dm.heightPixels; // 屏幕高度（像素）
//
//        double screenWidth = BigDecimalUtil.sub(width, AppController.getInstance().dp2px(47f));
//
//        double viewWidth = BigDecimalUtil.mul(screenWidth, widthScale);
//
//        double viewHeight = BigDecimalUtil.mul(scale, viewWidth);
//
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
//        layoutParams.height = (int) viewHeight;
//        layoutParams.width = (int) viewWidth;
//        view.setLayoutParams(layoutParams);
//    }


//    public static void setHeightConstraintLayoutDifference(Context activity, ImageView view, float scale, int difference) {
//        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;// 屏幕宽度（像素）
//        int height = dm.heightPixels; // 屏幕高度（像素）
//        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
//        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        int screenWidth = (int) (width / density);//屏幕宽度(dp)
//        int screenHeight = (int) (height / density);//屏幕高度(dp)
//
//        double mul = BigDecimalUtil.mul(scale, width - AppController.getInstance().dp2px(difference));
//        L.e("最后的宽度：" + mul);
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
//        layoutParams.height = (int) mul;
//        view.setLayoutParams(layoutParams);
//    }


}
