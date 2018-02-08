package com.example.svnikitin.vkauthtestapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.URLEncoder;

/**
 * Created by svnikitin on 06.02.2018.
 */

public class VkLoginActivity extends Activity {

    WebView webview;
    ProgressBar progress;
    private Intent intent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        //Получаем элементы
        webview = findViewById(R.id.web);
        progress = findViewById(R.id.progress);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
        webview.clearCache(true);

        //Чтобы получать уведомления об окончании загрузки страницы
        webview.setWebViewClient(new VkWebViewClient());

        String url = "http://oauth.vk.com/authorize?client_id=" + AppConstats.vk_api_id + "&scope=wall,offline&redirect_uri=" + URLEncoder.encode(AppConstats.vk_redirect_url) + "&response_type=token";
        webview.loadUrl(url);
        webview.setVisibility(View.VISIBLE);

        intent = new Intent();
    }

    class VkWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progress.setVisibility(View.VISIBLE);
            parseUrl(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.startsWith("http://oauth.vk.com/authorize") || url.startsWith("http://oauth.vk.com/oauth/authorize") || url.startsWith("https://oauth.vk.com/authorize")) {
                progress.setVisibility(View.GONE);
            }
        }
    }

    private void parseUrl(String url) {
        try {
            if (url == null) {
                return;
            }
            if (url.startsWith(AppConstats.vk_redirect_url)) {
                if (!url.contains("error")) {
                    String[] auth = VKUtil.parseRedirectUrl(url);
                    webview.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);

                    //Строим данные

                    intent.putExtra("token", auth[0]);
                    intent.putExtra("uid", auth[1]);

                    //Возвращаем данные
                    intent.putExtra("requestCode", MainActivity.AUTH_VK);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    setResult(RESULT_CANCELED);
                    finish();
                }

            } else if (url.startsWith(AppConstats.vk_grant_access_url)) {
                if (!url.contains("error")) {
                    String[] auth = VKUtil.parseRedirectUrlHash(url);

                    intent.putExtra("hash", auth[0]);
                }

            } else if (url.contains("error?err")) {
                setResult(RESULT_CANCELED);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @SuppressLint("NewApi")
    public static void logout() {
        CookieManager.getInstance().removeAllCookies(null);
    }
}