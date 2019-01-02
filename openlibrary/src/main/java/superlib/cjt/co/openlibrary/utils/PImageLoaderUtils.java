package superlib.cjt.co.openlibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import superlib.cjt.co.openlibrary.R;


/**
 * UIL 工具类
 * Created by sky on 15/7/26.
 */
public class PImageLoaderUtils {


    public static PImageLoaderUtils pImageLoaderUtils;

    public static PImageLoaderUtils getInstance() {
        if (pImageLoaderUtils == null) {
            pImageLoaderUtils = new PImageLoaderUtils();
        }
        return pImageLoaderUtils;
    }


    public void displayIMG(String uri, ImageView imageView, Context context) {
        try {
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);
            options.centerCrop();
            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }


    }

    public void displayIMG(int uri, ImageView imageView, Context context) {
        try {
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);
            options.centerCrop();
            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }


    }

}
