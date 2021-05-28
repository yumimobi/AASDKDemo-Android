[See the English Guide](https://github.com/yumimobi/AASDKDemo-Android/wiki)

# 1. 概述

## 1.1 面向人群

当前文档面向需要在 Android 产品中接入中宣部防沉迷 SDK 的开发人员。
   
## 1.2 开发环境

OS: Windows, Mac, Linux <br/>
Android SDK: > 4.4(API level 19)<br/>
IDE: Eclipse with ADT (ADT version 23.0.4) OR Android-Studio<br/>
Java: > JDK 7
  

# 2. 开发环境配置

## 2.1 Android-studio 接入

在 app 下的 build.gradle 中添加依赖相关依赖

```groovy
dependencies {
    // AntiAddictionSDK
    implementation "io.github.yumimobi:antiaddiction:1.0.0"
｝
```

在工程的 build.gradle 中添加maven central

```groovy
allprojects {
    repositories {
        mavenCentral()
        maven { url 'https://repo1.maven.org/maven2/' }
    }
}
```


 ## 2.3 防混淆配置
 如果您的工程需要混淆编译， 请在混淆文件内增加以下内容

 ```groovy
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,Synthetic,EnclosingMethod
-keep class com.android.antiaddiction.system.** { *;}
```


# 3. 配置防沉迷SDK需要的参数

## 3.1 下载ZplayConfig.xml文件,并添加到工程的assets目录下

<img src="resources\antiAddiction-ZplayConfig.jpg" alt="antiAddiction-ZplayConfig">

>[ZplayConfig.xml 下载](https://github.com/yumimobi/AASDKDemo-Android/blob/master/app/src/main/assets/ZplayConfig.xml)

## 3.2 修改ZplayConfig.xml文件中的配置
<img src="resources\antiAddiction-ZplayConfig1.png" alt="antiAddiction-ZplayConfig1">

<div style="background-color:rgb(228,244,253);padding:10px;">
<span style="color:rgb(62,113,167);">ZplayConfig.xml文件中的GameID, ChannelID参数，请联系掌游产品获取</span></div>
<br/>


# 4.快速接入 
## 4.1初始化

```java
 AntiAddictionSystemSDK.init(Activity, new AntiAddictionCallback() {
            @Override
            public void onTouristsModeLoginSuccess(String touristsID) {
                //游客登录成功，游客id:touristsID
            }

            @Override
            public void onTouristsModeLoginFailed() {
                //游客登录失败
            }

            @Override
            public void realNameAuthenticateResult(boolean isSuccess) {
                //实名认证状态：isSuccess
            }

            @Override
            public void noTimeLeftWithTouristsMode() {
                // 游客时长已用尽(1h/15 days)
                // 收到此回调 3s 后，会展示游客时长已用尽弹窗
                // 游戏请在收到回调 3s 内处理未尽事宜
            }

            @Override
            public void noTimeLeftWithNonageMode() {
                // 未成年时长已用尽(2h/1 day)
                // 收到此回调 3s 后，会展示未成年时长已用尽弹窗
                // 游戏请在收到回调 3s 内处理未尽事宜
            }
        });
```  

## 4.2 展示实名认证接口
如果游戏主界面上提供了用户点击实名认证的功能，可调用下面的接口，展示防沉迷SDK的实名认证界面，为用户提供实名认证功能
```java
AntiAddictionSystemSDK.showRealNameDialog(Activity);
```  

## 4.3 其他接口

### 4.3.1 游戏退到后台接口(必选)
当用户按home键，将游戏退出到后台时，请调用下面的接口

<span style="color:rgb(255,0,0);">
<b>重要提示：</b> 游戏退到后台接口必须调用，不调用会导致防沉迷SDK计算游戏时长错误
</span>

```java
AntiAddictionSystemSDK.onPause();
```

### 4.3.2 游戏恢复前台接口(必选)
当用户将游戏恢复到前台时，请调用下面的接口

<span style="color:rgb(255,0,0);">
<b>重要提示：</b> 游戏恢复前台接口必须调用，不调用会导致防沉迷SDK计算游戏时长错误
</span>

```java
AntiAddictionSystemSDK.onResume();
```


## 4.3.3 获取游客登录状态接口(可选)
```java
//获取游客登录状态
//true:登录成功
//false:登录失败
boolean isLogined = AntiAddictionSystemSDK.isLogined(Activity);
```

## 4.3.4 获取实名认证状态接口(可选)
```java
//获取实名认证状态
//true:认证成功
//false:认证失败
boolean isAuthenticated = AntiAddictionSystemSDK.isAuthenticated(Activity);
```


## 4.3.4 获取用户剩余可玩时长接口(可选)
```java
//获取用户剩余可玩时长
//-1:表示用户为成年人，不受限制
//大于0的数:用户的剩余可玩时长，单位秒
long leftTimeOfCurrentUser = AntiAddictionSystemSDK.leftTimeOfCurrentUser(Activity)
```
