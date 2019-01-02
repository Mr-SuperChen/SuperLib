
package superlib.cjt.co.openlibrary.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PMD5Util {

    /**
     * MD5加密
     * 
     * @param val
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5(String val) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] m = md5.digest();// 加密
            return getString(m);
        } catch (NoSuchAlgorithmException e) {
            return val;
        }

    }

    private static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }



    public static String md5(String txt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(txt.getBytes("UTF-8"));
            StringBuffer buf = new StringBuffer();
            for (byte b : md.digest()) {
                buf.append(String.format("%02x", b & 0xff));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    public static void main(String args[]) {
        String s = new String("T8xkWw+E0P0AApU".toLowerCase());

        System.out.println("MD5:" + md5(s));

    }
}
