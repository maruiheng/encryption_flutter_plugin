#import "EncryptionFlutterPlugin.h"
#import <encryption_flutter_plugin/encryption_flutter_plugin-Swift.h>

@implementation EncryptionFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftEncryptionFlutterPlugin registerWithRegistrar:registrar];
}
@end
