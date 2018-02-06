package com.example.svnikitin.vkauthtestapp;

import android.app.Activity;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    public static final int AUTH_VK = 777;

    private String hash;
    private String token;
    private String uid;

    Button loginButton;
    Button friendListButton;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), VkLoginActivity.class), AUTH_VK);
            }
        });

        friendListButton = findViewById(R.id.friendListButton);

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VkLoginActivity.logout();
                setButtonVisibility("logout");
            }
        });

        setButtonVisibility("start");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AUTH_VK:
                if (resultCode == Activity.RESULT_OK) {
                    //получаем токен и id пользователя
                    token = data.getStringExtra("token");
                    uid = data.getStringExtra("uid");
                    hash = data.getStringExtra("hash");

                    setButtonVisibility("login");
                }
                break;
            default:
                setButtonVisibility("logout");

        }
    }


    private void setButtonVisibility(String state) {
        switch (state) {
            case "start":
                loginButton.setVisibility(View.VISIBLE);
                friendListButton.setVisibility(View.GONE);
                logoutButton.setVisibility(View.GONE);
                break;
            case "login":
                loginButton.setVisibility(View.GONE);
                friendListButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.VISIBLE);
                break;
            case "logout":
                loginButton.setVisibility(View.VISIBLE);
                friendListButton.setVisibility(View.GONE);
                logoutButton.setVisibility(View.GONE);
                break;
        }
    }
}
