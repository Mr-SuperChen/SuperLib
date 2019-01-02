package superlib.cjt.co.openlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;

import superlib.cjt.co.openlibrary.BuildConfig;


public class PFileUtils {

    private static String BASE_PATH;
    private static String STICKER_BASE_PATH;

    private static PFileUtils mInstance;

    public static PFileUtils getInst() {
        if (mInstance == null) {
            synchronized (PFileUtils.class) {
                if (mInstance == null) {
                    mInstance = new PFileUtils();
                }
            }
        }
        return mInstance;
    }

    public File getExtFile(String path) {
        return new File(BASE_PATH + path);
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long 单位为K
     * @throws Exception
     */
    public static long size = 0;

//    public static long getFolderSize(File file) {
//        try {
//
//            if (!file.exists()) {
//                return size = size;
//            } else if (!file.isDirectory()) {
//                return size = size + file.length();
//            }
//            File[] fileList = file.listFiles();
//            for (int i = 0; i < fileList.length; i++) {
//                if (fileList[i].isDirectory()) {
//                    if (fileList[i].getName().equals(BuildConfig.folderdatabase)) {
//
//                    } else {
//                        getFolderSize(fileList[i]);
//                    }
//                } else {
//                    size = size + fileList[i].length();
//                }
//            }
//            System.out.println("KP:" + size);
//            return size;
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//
//    public static void getRemoveFolder(File file) {
//        try {
//            if (!file.exists()) {
//                return;
//            } else if (!file.isDirectory()) {
//                file.delete();
//            }
//            File[] fileList = file.listFiles();
//            for (int i = 0; i < fileList.length; i++) {
//                if (fileList[i].isDirectory()) {
//
//                    if (fileList[i].getName().equals(BuildConfig.folderdatabase) || fileList[i].getName().equals(BuildConfig.foldercer)) {
//
//                    } else {
//                        getRemoveFolder(fileList[i]);
//                    }
//                } else {
//                    fileList[i].delete();
//                }
//            }
//
//        } catch (Exception e) {
//        }
//    }


    public String getBasePath(int packageId) {
        return STICKER_BASE_PATH + packageId + "/";
    }

    private String getImageFilePath(int packageId, String imageUrl) {
        String md5Str = PMD5Util.getMD5(imageUrl).replace("-", "mm");
        return getBasePath(packageId) + md5Str;
    }

    public void removeAddonFolder(int packageId) {
        String filename = getBasePath(packageId);
        File file = new File(filename);
        if (file.exists()) {
            delete(file);
        }
    }

    public void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }


//    private PFileUtils() {
//        String sdcardState = Environment.getExternalStorageState();
//        //如果没SD卡则放缓存
//        if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
//            BASE_PATH = getAppPath() + "/stickercamera/";
//        } else {
//            BASE_PATH = AppController.getInstance().getCacheDirPath();
//        }
//
//        STICKER_BASE_PATH = BASE_PATH + "/stickers/";
//    }

    public boolean createFile(File file) {
        try {
            if (!file.getParentFile().exists()) {
                mkdir(file.getParentFile());
            }
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean mkdir(File file) {
        while (!file.getParentFile().exists()) {
            mkdir(file.getParentFile());
        }
        return file.mkdir();
    }


    public boolean renameDir(String oldDir, String newDir) {
        File of = new File(oldDir);
        File nf = new File(newDir);
        return of.exists() && !nf.exists() && of.renameTo(nf);
    }


//    public File getCacheDir() {
//        return AppController.getInstance().getContext().getCacheDir();
//    }


//    public static String getAppPath() {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        } else {
//            return AppController.getInstance().getCacheDirPath();
//        }
//        String replace = AppController.getInstance().getContext().getPackageName();
//        String path = sdDir.toString() + "/Android/data/" + replace + "/";
//        File dbFolder = new File(path);
//        // 目录不存在则自动创建目录
//        if (!dbFolder.exists()) {
//            dbFolder.mkdirs();
//        }
//        return path;
//    }

//    public static boolean isSDFree() {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        } else {
//            String cacheDirPath = AppController.getInstance().getCacheDirPath();
//            sdDir = new File(cacheDirPath);
//        }
//
//        StatFs sf = new StatFs(sdDir.getPath());
//        //获取单个数据块的大小(Byte)
//        long blockSize = sf.getBlockSize();
//        //空闲的数据块的数量
//        long freeBlocks = sf.getAvailableBlocks();
//        //返回SD卡空闲大小
//        //return freeBlocks * blockSize;  //单位Byte
//        //return (freeBlocks * blockSize)/1024;   //单位KB
//        long freeMemory = (freeBlocks * blockSize) / 1024 / 1024;//单位MB
//        L.e("手机剩余内存（MB）：" + freeMemory);
//        if (freeMemory > 50) {
//            /**
//             * 可用
//             */
//            return true;
//        } else {
//            /**
//             * 已满
//             */
//            return false;
//        }
//    }


//    public static String getAppPathImg() {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        } else {
//            return AppController.getInstance().getCacheDirPath();
//        }
//        String replace = AppController.getInstance().getContext().getPackageName();
//        String path = sdDir.toString() + "/Android/data/" + replace + "/" + BuildConfig.folderimg + "/";
//        File dbFolder = new File(path);
//        // 目录不存在则自动创建目录
//        if (!dbFolder.exists()) {
//            dbFolder.mkdirs();
//        }
//        return path;
//    }
//
//    public static String getSDPathVideo() {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        } else {
//            return AppController.getInstance().getCacheDirPath();
//        }
//        String replace = AppController.getInstance().getContext().getPackageName();
//        String path = sdDir.toString() + "/Android/data/" + replace + "/" + BuildConfig.foldervideo + "/";
//        File dbFolder = new File(path);
//        // 目录不存在则自动创建目录
//        if (!dbFolder.exists()) {
//            dbFolder.mkdirs();
//        }
//        return path;
//    }
//
//    public static String getSDPath() {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        } else {
//            return AppController.getInstance().getCacheDirPath();
//        }
//        String replace = AppController.getInstance().getContext().getPackageName();
//        String path = sdDir.toString() + "/Android/data/" + replace + "/" + BuildConfig.folderdatabase + "/";
//        File dbFolder = new File(path);
//        // 目录不存在则自动创建目录
//        if (!dbFolder.exists()) {
//            dbFolder.mkdirs();
//        }
//        return path;
//    }
//
//    public static String getSDPathFILE() {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        } else {
//            return AppController.getInstance().getCacheDirPath();
//        }
//        String replace = AppController.getInstance().getContext().getPackageName();
//        String path = sdDir.toString() + "/Android/data/" + replace + "/" + BuildConfig.folderfile + "/";
//        File dbFolder = new File(path);
//        // 目录不存在则自动创建目录
//        if (!dbFolder.exists()) {
//            dbFolder.mkdirs();
//        }
//        return path;
//    }
//
//    public static String getSDCerFILE() {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        } else {
//            return AppController.getInstance().getCacheDirPath();
//        }
//        String replace = AppController.getInstance().getContext().getPackageName();
//        String path = sdDir.toString() + "/Android/data/" + replace + "/" + BuildConfig.foldercer + "/";
//        File dbFolder = new File(path);
//        // 目录不存在则自动创建目录
//        if (!dbFolder.exists()) {
//            dbFolder.mkdirs();
//        }
//        return path;
//    }


//    public static String getSize() {
//
//        return getFolderSize(new File(getAppPath()));
//    }

        /**
         * 获取缓存大小
         *
         * @return
         * @throws Exception
         */
//    public static String getTotalCacheSize() throws Exception {
//        File file = new File(getAppPath());
//        L.e(getAppPath() + "?" + file.length());
//        size = 0;
//        long cacheSize = getFolderSize(new File(getAppPath()));
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            cacheSize += getFolderSize(new File(getAppPath()));
//        }
//        L.e(cacheSize + "");
//        return getFormatSize(cacheSize);
//    }


//    public static void clearCache() {
//        getRemoveFolder(new File(getAppPath()));
//    }


    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /***
     * 写文件
     *
     * @param write_str 文件内容
     * @param filename  文件名
     * @throws IOException
     */
//    public static void writeSDFile(String write_str, String filename) throws IOException {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        }
//        String replace = AppController.getInstance().getContext().getPackageName();
//        String path = getAppPath() + BuildConfig.folderdocument + "/";
//        File dbFolder = new File(path);
//        // 目录不存在则自动创建目录
//        if (!dbFolder.exists()) {
//            dbFolder.mkdirs();
//        }
//
//        File file = new File(path + filename + ".txt");
//        if (!file.exists()) {
//            file.createNewFile();
//        } else {
//            file.delete();
//            file = new File(path + filename + ".txt");
//            file.createNewFile();
//        }
//
//        BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
//        fw.append(write_str);
//        fw.newLine();
//        fw.flush(); // 全部写入缓存中的内容
//
//        fw.close();
//    }

    /***
     * 读文件
     *
     * @param filename 文件名
     * @return
     * @throws IOException
     */
//    public static String readSDFile(String filename) throws IOException {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        }
//        String replace = AppController.getInstance().getContext().getPackageName();
//        String path = getAppPath() + BuildConfig.folderdocument + "/";
//        File dbFolder = new File(path);
//        // 目录不存在则自动创建目录
//        if (!dbFolder.exists()) {
//            dbFolder.mkdirs();
//        }
//
//        File file = new File(path + filename + ".txt");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//
//
//        InputStreamReader read = new InputStreamReader(
//                new FileInputStream(file), "UTF-8");//考虑到编码格式
//        BufferedReader bufferedReader = new BufferedReader(read);
//        String lineTxt = null;
////        while ((lineTxt = bufferedReader.readLine()) != null) {
////            lineTxt = lineTxt + "\n";
////        }
//        StringBuffer sb = new StringBuffer();
//        FileInputStream fis = new FileInputStream(file);
//        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//        while ((lineTxt = br.readLine()) != null)
//            sb.append(lineTxt);
//        read.close();
//        return sb.toString();
//    }


    /** */
    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldname 原来的文件名
     * @param newname 新文件名
     */
    public File renameFile(String path, String oldname, String newname) {
        if (!oldname.equals(newname)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path + "/" + oldname);
            File newfile = new File(path + "/" + newname);
            if (!oldfile.exists()) {
                return newfile;//重命名文件不存在
            }
            if (newfile.exists()) {//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname + "已经存在！");
                newfile.delete();
                oldfile.renameTo(newfile);
            } else {
                oldfile.renameTo(newfile);
            }
            return newfile;
        } else {
            System.out.println("新文件名和旧文件名相同...");
        }
        return null;
    }


    /****
     * 文件重命名
     * @param oldpathname  旧文件地址
     * @param newpathname  新文件地址
     * @return
     */
    public static File renameFile(String oldpathname, String newpathname) {
        if (!oldpathname.equals(newpathname)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(oldpathname);
            File newfile = new File(newpathname);
            if (!oldfile.exists()) {
                return newfile;//重命名文件不存在
            }
            if (newfile.exists()) {//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                L.e(newpathname + "已经存在！");
                newfile.delete();
                oldfile.renameTo(newfile);
            } else {
                oldfile.renameTo(newfile);
            }
            return newfile;
        } else {
            return new File(newpathname);
        }
    }


//    public static String saveBitmapToSdCard(Context context, Bitmap bitmap) {
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 判断是否可以对SDcard进行操作
//        {      // 获取SDCard指定目录下
//            String sdCardDir = getAppPathImg();
//            File dirFile = new File(sdCardDir);  //目录转化成文件夹
//            if (!dirFile.exists()) {                //如果不存在，那就建立这个文件夹
//                dirFile.mkdirs();
//            }                            //文件夹有啦，就可以保存图片啦
//            File file = new File(sdCardDir, System.currentTimeMillis() + ".jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名
//            FileOutputStream out = null;
//            try {
//                out = new FileOutputStream(file);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//
//                try {
//                    Uri localUri = Uri.fromFile(file);
//                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
//                    context.sendBroadcast(localIntent);
//                } catch (Exception e) {
//
//                }
//            } catch (FileNotFoundException e) {
//                return "";
//            }
//            try {
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//                return "";
//            }
//            return file.getAbsolutePath();
//        } else {
//            return "";
//        }
//    }


}

