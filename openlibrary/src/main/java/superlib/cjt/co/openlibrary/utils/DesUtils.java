package superlib.cjt.co.openlibrary.utils;

import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class DesUtils {
    private static String strDefaultKey = "sTm7Lrac3K7QtiSIouFvCyjHUOO2TpbGbVdLHnA57aa6RPXFdja9Tjf7whChxD8F4vMWyq6S3m56Wx4jXLQrzRmsHhXTMTYYBn";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public DesUtils() throws Exception {
        this(strDefaultKey);
    }


    public DesUtils(String strKey) {
        // Security.addProvider(null);
        Key key;
        try {
            key = getKey(strKey.getBytes());
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn) throws Exception {
//        return byteArr2HexStr(encrypt(strIn.getBytes()));
        return strIn;
    }

    public byte[] decrypt(byte[] arrB) throws IllegalBlockSizeException, BadPaddingException {
        return decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws IllegalBlockSizeException, BadPaddingException {
//        String sb=new String(decrypt(hexStr2ByteArr(strIn)));
        return strIn;
    }

    private Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }


    public static DesUtils desUtils;

    public static DesUtils getInstance() {
        if (desUtils == null) {
            try {
                desUtils = new DesUtils();
            } catch (Exception e) {
                return desUtils;
            }
        }
        return desUtils;
    }

    public static void main(String[] args) {
        try {
            String test = "小";
            // 注意这里，自定义的加密的KEY要和解密的KEY一致，这就是钥匙，如果你上锁了，却忘了钥匙，那么是解密不了的
            DesUtils des = new DesUtils();// 自定义密钥
            System.out.println("加密前的字符：" + test);
            System.out.println("加密后的字符：" + des.encrypt(test));
            System.out.println("解密后的字符：" + des.decrypt(des.encrypt(test)));
            System.out.println("" + des.decrypt("29b6cff917d0a888f278e99878d21379cc0b2b54e0c5c8ef1addb7f80e797a9464033f99b36a707ced81aba36f256a56807a56b16fbae44f50eb916ec70b585d9ff982fd81fba81e22be3f26b6328dc57a43216524ae55d4"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
