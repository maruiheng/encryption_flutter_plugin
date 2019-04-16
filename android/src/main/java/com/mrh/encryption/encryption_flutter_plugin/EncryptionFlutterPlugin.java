package com.mrh.encryption.encryption_flutter_plugin;

import android.util.Log;

import com.mrh.encryption.encryption_flutter_plugin.util.AesEncryptUtil;
import com.mrh.encryption.encryption_flutter_plugin.util.DESCodec;
import com.mrh.encryption.encryption_flutter_plugin.util.Hex;

import java.security.Key;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * EncryptionFlutterPlugin
 */
public class EncryptionFlutterPlugin implements MethodCallHandler {
    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "encryption_flutter_plugin");
        channel.setMethodCallHandler(new EncryptionFlutterPlugin());
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("uRLEncode")) {
            result.success(AesEncryptUtil.uRLEncode(call.<String>argument("pwd")));
        } else if (call.method.equals("forsave")) {
            String pwd = call.<String>argument("pwd");
            String ks = call.<String>argument("key");
            Log.e("aaa   forsave  pwd= " ,pwd+"key= "+ks);
            //初始化密钥
            try {
                byte[] key = DESCodec.initSecretKey(ks);
                //秘钥转换
                Key k = DESCodec.toKey(key);
                //加密
                byte[] encryptData = DESCodec.encrypt(pwd.getBytes(), k);
                String hexData = Hex.encode(encryptData);// 二进制转换成16进制数据 保存的数据
                Log.e("aaa   pwd= ",hexData);
                result.success(hexData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (call.method.equals("forlogin")){

            String pwd = call.<String>argument("pwd");
            String ks = call.<String>argument("key");

            Log.e("aaa   forlogin  pwd= " ,pwd+"key= "+ks);
            //初始化密钥
            byte[] key;
            try {
                key = DESCodec.initSecretKey(ks);
                //秘钥转换
                Key k = DESCodec.toKey(key);
                // 解码
                byte[] hexByte = Hex.decode(pwd);// 将加密后转化成16进制的数据转化成二进制 准备进行解码
                // 对称秘钥解密
                byte[] decryptData = DESCodec.decrypt(hexByte, k);
                String passWord = new String(decryptData);
                result.success(AesEncryptUtil.uRLEncode(passWord));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            result.notImplemented();
        }
    }
}
