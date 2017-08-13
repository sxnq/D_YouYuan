# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidSdk\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


#-dontskipnonpubliclibraryclassmembers
## 指定代码的压缩级别
#-optimizationpasses 5
#-dontusemixedcaseclassnames
## 是否混淆第三方jar
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-keepattributes SourceFile,LineNumberTable
#-verbose
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#-dontoptimize
#
#-libraryjars libs/gesture-imageview.jar
#-libraryjars libs/MobCommons-2017.0321.1624.jar
#-libraryjars libs/MobTools-2017.0321.1624.jar
#-libraryjars libs/SMSSDK-2.1.4.jar
#
##根据我的经验，一般model最好避免混淆（model无关紧要，不混淆也没多大关系）如：
#-keep class com.bank.pingan.model.** { *; }
#
#
## webview + js
## keep 使用 webview 的类
#-keepclassmembers class com.goldnet.mobile.activity.InfoDetailActivity {
#   public *;
#}
## keep 使用 webview 的类的所有的内部类
#-keepclassmembers   class com.goldnet.mobile.activity.InfoDetailActivity$*{
#    *;
#}
#
#-keep class android.** {*; }
#-keep public class * extends android.view
#-keep public class * extends android.content.pm
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class com.android.vending.licensing.ILicensingService
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.app.Fragment
#-keep public class * extends android.support.annotation.**
#-keep public class * extends android.support.v7.**
#
#
##友盟统计
#-keepclassmembers class * {
#   public <init> (org.json.JSONObject);
#}
#
##友盟推送
#-dontwarn com.taobao.**
#-dontwarn anet.channel.**
#-dontwarn anetwork.channel.**
#-dontwarn org.android.**
#-dontwarn org.apache.thrift.**
#-dontwarn com.xiaomi.**
#-dontwarn com.huawei.**
#-dontwarn com.ta.**
#
#
#-keep class com.taobao.** {*;}
#-keep class org.android.** {*;}
#-keep class anet.channel.** {*;}
#-keep class com.umeng.** {*;}
#-keep class com.xiaomi.** {*;}
#-keep class com.huawei.** {*;}
#-keep class org.apache.thrift.** {*;}
#
#-keep class com.alibaba.sdk.android.**{*;}
#-keep class com.ut.**{*;}
#-keep class com.ta.**{*;}
#
#
#-keep public class * extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
#-keep public class * extends com.j256.ormlite.android.apptools.OpenHelperManager
#
## 保持 native 方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
#-keepclasseswithmembers class * {                                               # 保持自定义控件类不被混淆
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);     # 保持自定义控件类不被混淆
#}
#
#-keepclassmembers class * extends android.app.Activity {                        # 保持自定义控件类不被混淆
#   public void *(android.view.View);
#}
#
#-keepclassmembers enum * {                                                      # 保持枚举 enum 类不被混淆
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
#-keep class * implements android.os.Parcelable {                                # 保持 Parcelable 不被混淆
#  public static final android.os.Parcelable$Creator *;
#}
#
#-keep class MyClass;                                                            # 保持自己定义的类不被混淆
#
#
#
#
##RxJava /RxAndroid
#-dontwarn sun.misc.**
#-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
#   long producerIndex;
#   long consumerIndex;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode producerNode;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode consumerNode;
#}
#
##butterknife
#-keep class butterknife.** { *; }
#-dontwarn butterknife.internal.**
#-keep class **$$ViewBinder { *; }
#-keepclasseswithmembernames class * {
#    @butterknife.* <fields>;
#}
#-keepclasseswithmembernames class * {
#    @butterknife.* <methods>;
#}
#
#
##GreenDao
#-keep class de.greenrobot.dao.** {*;}
#-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
#public static java.lang.String TABLENAME;
#}
#-keep class **$Properties
#
#
##Glide
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#
##Gson
#-keepattributes Signature-keepattributes *Annotation*
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
## Application classes that will be serialized/deserialized over Gson 下面替换成自己的实体类
#-keep class com.example.bean.** { *; }
#
#
##retrofit2
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
#-keepattributes Signature
#-keepattributes Exceptions
#
#
#
#
#proguard.config=${sdk.dir}/tools/proguard/proguard-android.txt:proguard-project.txt
