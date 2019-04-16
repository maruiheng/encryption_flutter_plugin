package com.mrh.encryption.encryption_flutter_plugin.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
public class AesEncryptUtil {
    //密钥 (需要前端和后端保持一致)
    private static final String KEY =  "jlt@oilchain!201";
    //算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * aes加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content) throws Exception {
        return aesEncrypt(content, KEY);
    }


    public static String uRLEncode(String str) {
        try {
            return URLEncoder.encode(aesEncrypt(str), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
//        return Base64.encodeToString(bytes, Base64.NO_WRAP);
        return Base64Encoder.encode(bytes);
    }


    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }


    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }


    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {

        //加密前：654321
        //加密后：WI+csUxXJ20fXMz9CHztXw==
        //encode编码后：WI%2BcsUxXJ20fXMz9CHztXw%3D%3D
        String content = "654321111";
        System.out.println("加密前：" + content);
        content = aesEncrypt(content);
        System.out.println("加密后：" + content);
        content = URLEncoder.encode(content, "utf-8");
        System.out.println("encode编码后：" + content);
        content = URLDecoder.decode(content, "utf-8");
        System.out.println("encode解码后：" + content);
    }
}
