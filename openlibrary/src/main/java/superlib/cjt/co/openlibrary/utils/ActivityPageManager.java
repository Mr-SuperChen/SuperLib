/*
    ShengDao Android Client, ActivityPageManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package superlib.cjt.co.openlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ActivityPageManager {

    private static List<Activity> activityStack;
    private static ActivityPageManager instance;

    /**
     * constructor
     */
    private ActivityPageManager() {

    }

    /**
     * get the AppManager instance, the AppManager is singleton.
     */
    public static ActivityPageManager getInstance() {
        if (instance == null) {
            instance = new ActivityPageManager();
        }
        return instance;
    }


    Handler handler = new Handler();

    boolean isAdd = true;

    /**
     * add Activity to Stack
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new ArrayList<Activity>();
        }

        activityStack.add(activity);
    }


    /**
     * remove Activity from Stack
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.remove(activity);
    }
    
    public void finishActivity(Activity activity) {
        try {
            if (activity != null) {
                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        } catch (Exception e) {

        }

    }

    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {

        }
    }

    public boolean isExit(Class<?> cls) {
        boolean isExit = false;
        try {

            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    isExit = true;
                }
            }
        } catch (Exception e) {
            isExit = false;
        }
        return isExit;
    }


    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (null != activity) {
                activity.finish();
            }
        }
        activityStack.clear();
        //杀死该应用进程
//		android.os.Process.killProcess(android.os.Process.myPid());

    }

    public static void unbindReferences(View view) {
        try {
            if (view != null) {
                view.destroyDrawingCache();
                unbindViewReferences(view);
                if (view instanceof ViewGroup) {
                    unbindViewGroupReferences((ViewGroup) view);
                }
            }
        } catch (Throwable e) {

        }
    }

    private static void unbindViewGroupReferences(ViewGroup viewGroup) {
        int nrOfChildren = viewGroup.getChildCount();
        for (int i = 0; i < nrOfChildren; i++) {
            View view = viewGroup.getChildAt(i);
            unbindViewReferences(view);
            if (view instanceof ViewGroup)
                unbindViewGroupReferences((ViewGroup) view);
        }
        try {
            viewGroup.removeAllViews();
        } catch (Throwable mayHappen) {
            // AdapterViews, ListViews and potentially other ViewGroups don't
            // support the removeAllViews operation
        }
    }

    @SuppressWarnings("deprecation")
    private static void unbindViewReferences(View view) {
        // set all listeners to null (not every view and not every API level
        // supports the methods)
        try {
            view.setOnClickListener(null);
            view.setOnCreateContextMenuListener(null);
            view.setOnFocusChangeListener(null);
            view.setOnKeyListener(null);
            view.setOnLongClickListener(null);
            view.setOnClickListener(null);
        } catch (Throwable mayHappen) {
            //todo
        }

        // set background to null
        Drawable d = view.getBackground();
        if (d != null) {
            d.setCallback(null);
        }

        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            d = imageView.getDrawable();
            if (d != null) {
                d.setCallback(null);
            }
            imageView.setImageDrawable(null);
            imageView.setBackgroundDrawable(null);
        }

        // destroy WebView
        if (view instanceof WebView) {
            WebView webview = (WebView) view;
            webview.stopLoading();
            webview.clearFormData();
            webview.clearDisappearingChildren();
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            webview.destroyDrawingCache();
            webview.destroy();
            webview = null;
        }

        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            try {
                listView.removeAllViewsInLayout();
            } catch (Throwable mayHappen) {
            }
            ((ListView) view).destroyDrawingCache();
        }
    }

    /**
     * exit System
     *
     * @param context
     */
    public void exit(Context context) {
        exit(context, true);
    }

    /**
     * exit System
     *
     * @param
     */
    public void exitOtherActivity(Class<?> cls) {
        finishOtherActivity(cls);
    }

    private void finishOtherActivity(Class<?> cls) {
        try {
            List<Activity> lista = new ArrayList<>();
            for (int i = 0; i < activityStack.size(); i++) {
                L.e(".....关闭的页面..." + cls.getName() + "——————————" + activityStack.get(i).getClass().getName());
                if (!activityStack.get(i).getClass().getName().equals(cls.getName())) {
                    L.e(".....关闭的页面2..." + cls.getName() + "——————————" + activityStack.get(i).getClass().getName());
                    try {
                        if (activityStack.get(i) != null) {
                            lista.add(activityStack.get(i));
                            activityStack.get(i).finish();
                        }
                    } catch (Exception e) {
                    }
                }
            }

            for (Activity activity : lista) {
                for (Activity act : activityStack) {
                    if (activity.getClass().getName().equals(act.getClass().getName())) {
                        activityStack.remove(act);
                    }
                }
            }
            lista.clear();
        } catch (Exception e) {

        }


    }




    /**
     * exit System
     *
     * @param context
     * @param isClearCache
     */
    @SuppressWarnings("deprecation")
    public void exit(Context context, boolean isClearCache) {
        try {
            LCSharedPreferencesHelper   sharedPreferencesHelper = new LCSharedPreferencesHelper(context,
                    LCSharedPreferencesHelper.SHARED_PATH);
            finishAllActivity();
            /*if(context != null){
                ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
				activityMgr.restartPackage(context.getPackageName());
			}*/
            /*if(isClearCache){
                LruCacheManager.getInstance().evictAll();
				CacheManager.clearAll();
			}*/
//			System.exit(0);
            if (sharedPreferencesHelper.getBoolean("isRestart", false)) {
                sharedPreferencesHelper.putBoolean("isRestart", false);
                android.os.Process.killProcess(android.os.Process.myPid());
            }

        } catch (Exception e) {

        }
    }
}
