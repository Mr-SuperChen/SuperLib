package superlib.cjt.co.openlibrary.utils;

import android.app.Dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wupeitao on 2017/12/8.
 */

public class DialogManger {

    private static DialogManger instance;

    /**
     * get the AppManager instance, the AppManager is singleton.
     */
    public static DialogManger getInstance() {
        if (instance == null) {
            instance = new DialogManger();
        }
        return instance;
    }

    List<Dialog> listDialogs = new ArrayList<>();

    /***
     * 将对话框对象对象，添加到list
     * @param dialog
     */
    public void addDialogs(Dialog dialog) {
//        listDialogs.add(dialog);
    }

    /***
     * 移除所有对话框，并且关闭
     */
    public void cancelsDialogs() {
        for (int i = 0; i < listDialogs.size(); i++) {
            if (listDialogs.get(i) != null && listDialogs.get(i).isShowing()) {
                listDialogs.get(i).dismiss();
            }
        }
        listDialogs.clear();
    }

}
