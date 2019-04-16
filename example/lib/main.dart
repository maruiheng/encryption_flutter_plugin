import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:encryption_flutter_plugin/encryption_flutter_plugin.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _pwd = "";
  String _la = "";

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    String pwd;
    String la ;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await EncryptionFlutterPlugin.platformVersion;
      pwd = await EncryptionFlutterPlugin.uRLEncode("654321111");
      String a = await EncryptionFlutterPlugin.forSave("654321111");
      la = await EncryptionFlutterPlugin.forlogin(a);
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
      _pwd = pwd;
      _la = la;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(children: <Widget>[
            Text('Running on: $_platformVersion\n'),
            Text('pwd: $_pwd'),
            Text('repwd: $_la'),
          ],),
        ),
      ),
    );
  }
}
