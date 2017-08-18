# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Library/Android/sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/xiehao/Library/Android/sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-ignorewarnings
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-optimizations !code/simplification/cast,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View

-keep class android.support.** {*;}

-keep class **.R$* {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep public class * implements java.io.Serializable {*;}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class com.swensun.swdesign.data.** {*;}
-keep class com.swensun.swdesign.database.entity.** {*;}

-keepclasseswithmembernames class * {
	native <methods>;
}

-keepclassmembers enum * {
	public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepnames enum * {
	public <fields>;
}

-keep class protocol.** { *; }

-keep class com.duowan.jni.** { *; }

-keep class com.yysec.notify { *; }

-keep class com.yy.sdk.crashreport.** { *; }

-keep public class com.yy.hiidostatis.defs.handler.CrashHandler{ *; }

######## Android Architecture rules begin ########
######## Android Architecture rules end ########

######## Room rules begin ########
-keep class android.arch.persistence.room.Entity
-keep @android.arch.persistence.room.Entity class * { *; }
######## Room rules end ########

######## Wire rules begin ########
-keepclassmembers class * implements com.squareup.wire.Message {
  public static final com.squareup.wire.ProtoAdapter ADAPTER;
  }
-keepclassmembers class * implements com.squareup.wire.WireEnum {
  public static *** fromValue(int);
}
######## Wire rules end ########

######## Gson rules begin ########
# Gson specific classes
-keep class com.google.gson.** { *; }
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# keep the model classes that will be serialized/deserialized over Gson
######## Gson rules end ########

######## Okhttp rules begin ########
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
######## Okhttp rules end ########

######## Retrofit rules begin ########
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
######## Retrofit rules end ########