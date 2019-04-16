import 'dart:async';

import 'package:flutter/services.dart';
import 'dart:io';
import 'package:device_info/device_info.dart';

class EncryptionFlutterPlugin {
  static const MethodChannel _channel =
      const MethodChannel('encryption_flutter_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  //登录密码加密（登录页面点击登录）
  static Future<String> uRLEncode(String pwd) async {
    final String newpwd = await _channel.invokeMethod('uRLEncode',{"pwd":"$pwd"});
    return newpwd;
  }
  //登录密码加密存储（登录成功保存密码）
  static Future<String> forSave(String pwd) async {
    DeviceInfoPlugin deviceInfo = DeviceInfoPlugin();
    String key = "";
    if(Platform.isIOS){
      IosDeviceInfo info = await deviceInfo.iosInfo;
      key = info.systemName;
    }else if(Platform.isAndroid){
      AndroidDeviceInfo androidInfo = await deviceInfo.androidInfo;
      key = androidInfo.device;
    }
//    key = '123456789';
    final String newpwd = await _channel.invokeMethod('forsave',{"pwd":"$pwd","key":"$key"});
    return newpwd;
  }
  //登录密码解密（闪屏页自动登录）
  static Future<String> forlogin(String pwd) async {
    DeviceInfoPlugin deviceInfo = DeviceInfoPlugin();
    String key = "";
    if(Platform.isIOS){
      IosDeviceInfo info = await deviceInfo.iosInfo;
      key = info.systemName;
    }else if(Platform.isAndroid){
      AndroidDeviceInfo androidInfo = await deviceInfo.androidInfo;
      key = androidInfo.device;
    }
//    key = '123456789';
    final String newpwd = await _channel.invokeMethod('forlogin',{"pwd":"$pwd","key":"$key"});
    return newpwd;
  }
}
