package com.android.antiaddiction.system.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.android.antiaddiction.callback.AntiAddictionCallback;
import com.android.antiaddiction.demo.R;
import com.android.antiaddiction.system.AntiAddictionSystemSDK;
import com.android.antiaddiction.utils.ToastUtils;


public class MainActivity extends AppCompatActivity {
    TextView showToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToast = findViewById(R.id.showToast);

        AntiAddictionSystemSDK.init(this, new AntiAddictionCallback() {
            @Override
            public void onTouristsModeLoginSuccess(String touristsID) {
                showToast.setText("游客登录成功，游客id:" + touristsID);
            }

            @Override
            public void onTouristsModeLoginFailed() {
                showToast.setText("游客登录失败");
            }

            @Override
            public void realNameAuthenticateResult(boolean isSuccess) {
                showToast.setText("实名认证状态：" + isSuccess);
            }

            @Override
            public void noTimeLeftWithTouristsMode() {
                // 游客时长已用尽(1h/15 days)
                // 收到此回调 3s 后，会展示游客时长已用尽弹窗
                // 游戏请在收到回调 3s 内处理未尽事宜
                showToast.setText("游客时长已用尽");
            }

            @Override
            public void noTimeLeftWithNonageMode() {
                // 未成年时长已用尽(2h/1 day)
                // 收到此回调 3s 后，会展示未成年时长已用尽弹窗
                // 游戏请在收到回调 3s 内处理未尽事宜
                showToast.setText("未成年时长已用尽");
            }
        });

    }

    // 实名认证界面
    // 游戏可主动调用
    // 如果游戏无主动调用，游客游戏时长用尽后由SDK主动触发
    public void showUserIdCard(View view) {
        AntiAddictionSystemSDK.showRealNameDialog(this);
    }

    public void isLogined(View view) {
        ToastUtils.showToast(MainActivity.this, "登录状态：" + AntiAddictionSystemSDK.isLogined(this));
    }

    public void isAuthenticated(View view) {
        ToastUtils.showToast(MainActivity.this, "实名认证状态：" + AntiAddictionSystemSDK.isAuthenticated(this));
    }

    public void leftTimeOfCurrentUser(View view) {
        ToastUtils.showToast(MainActivity.this, "剩余游戏时长（-1为无限制，单位秒）：" + AntiAddictionSystemSDK.leftTimeOfCurrentUser(this));
    }


    @Override
    protected void onPause() {
        super.onPause();
        //游戏退到后台接口必须调用，不调用会导致防沉迷SDK计算游戏时长错误
        AntiAddictionSystemSDK.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //游戏恢复前台接口必须调用，不调用会导致防沉迷SDK计算游戏时长错误
        AntiAddictionSystemSDK.onResume();
    }
}