package superlib.cjt.co.openlibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2019/1/2.
 */

public class ToastUtils {
    public static void showMyToast(Context context, String content){
        Toast.makeText(context, "么么哒"+content, Toast.LENGTH_SHORT).show();
    }
}
