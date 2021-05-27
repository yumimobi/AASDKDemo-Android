# 1. Get Started

This guide is intended for publishers who want to integrate the AntiAddictionSystem.

# 1.1 Prerequisites

OS: Windows, Mac, Linux <br/>
Android SDK: > 4.4(API level 19)<br/>
IDE: Eclipse with ADT (ADT version 23.0.4) OR Android-Studio<br/>
Java: > JDK 7
  

# 2. Import the AntiAddictionSystem SDK
## 2.1 Android studio 

add AntiAddictionSystem SDK adapters dependencies.

```groovy
dependencies {
    // AntiAddictionSDK
    implementation "io.github.yumimobi:antiaddiction:1.0.0"
｝
```

add maven central in project build.gradle

```groovy
allprojects {
    repositories {
        mavenCentral()
        maven { url 'https://repo1.maven.org/maven2/' }
    }
}
```


## 2.3 Proguard

If your project turn on minifyEnabled, add the following to the proguard file.

```groovy
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,Synthetic,EnclosingMethod
-keep class com.android.antiaddiction.system.** { *;}
```


# 3. Configure the parameters required for AntiAddictionSystem SDK

## 3.1 Download ZplayConfig.xml and add to you project assets

<img src="resources\antiAddiction-ZplayConfig.jpg" alt="antiAddiction-ZplayConfig">

>[Download ZplayConfig.xml](https://github.com/yumimobi/AntiAddictionSystemDemo-Android/blob/master/app/src/main/assets/ZplayConfig.xml)

## 3.2 Configure the ZplayConfig.xml
<img src="resources\antiAddiction-ZplayConfig1.png" alt="antiAddiction-ZplayConfig1">

<div style="background-color:rgb(228,244,253);padding:10px;">
<span style="color:rgb(62,113,167);">For the GameID, ChannelID parameters in the ZplayConfig.xml file, please contact Zplay products</span></div>
<br/>


# 4 Integration
## 4.1 Init AntiAddictionSDK 

```java

 AntiAddictionSystemSDK.init(Activity, new AntiAddictionCallback() {
            @Override
            public void onTouristsModeLoginSuccess(String touristsID) {
                //tourist login result (Automatic login is implemented by SDK)
                //tourist login success
            }

            @Override
            public void onTouristsModeLoginFailed() {
                //tourist login result (Automatic login is implemented by SDK)
                //tourist login failed
            }

            @Override
            public void realNameAuthenticateResult(boolean isSuccess) {
                //real name auth result
            }

            @Override
            public void noTimeLeftWithTouristsMode() {
                // tourist's time is ran out
                // will display real name auth controller after 3 seconds
            }

            @Override
            public void noTimeLeftWithNonageMode() {
                // game time is ran out
                // will display the game time is ran out alert-controller after 3 
            }
        });
```  

## 4.2 Real-name authentication in the tourist mode 
If the user clicks on the real-name authentication function on the main interface of the game，You can show the Real-name auth view by following interface.

```java
AntiAddictionSystemSDK.showRealNameDialog(Activity);
```


## 4.3 Other API

#### 4.3.1 Application will enter background（Required）
When the user presses the home button to exit the game to the background, please call the following interface

<span style="color:rgb(255,0,0);">
<b>Warning:</b>  
Call the following api when application will enter background.Not calling will cause the anti-addiction SDK to calculate the game time error
</span>

```java
AntiAddictionSystemSDK.onPause();
```

#### 4.3.2 Application will enter foreground（Required）

<span style="color:rgb(255,0,0);">
<b>Warning:</b>  
Call the following api when application will enter foreground.Not calling will cause the anti-addiction SDK to calculate the game time error
</span>

```java
AntiAddictionSystemSDK.onResume();
```

## 4.3.3 Check user authentication status（Optional）

```java
//get authentication status
//true: authentication success
//false: not authentication
boolean isAuthenticated = AntiAddictionSystemSDK.isAuthenticated(Activity);
```


## 4.3.4 Check left time of the current user（Optional）
The left time will update every minute or application resign to backend.(except off-line)

```java
long leftTimeOfCurrentUser = AntiAddictionSystemSDK.leftTimeOfCurrentUser(Activity)
```
