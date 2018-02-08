package com.example.svnikitin.vkauthtestapp;

import android.app.Activity;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class MainActivity extends AppCompatActivity {

    public static final int AUTH_VK = 777;

    private String hash;
    private String token;
    private String uid;

    Button loginButton;
    ListView lvlitems ;
    Button logoutButton;

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), VkLoginActivity.class), AUTH_VK);
            }
        });


        lvlitems = findViewById(R.id.lvlitems);

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VkLoginActivity.logout();
                setButtonVisibility("logout");
            }
        });


        dbHandler = new MyDBHandler(this, null, null, 1);

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

                    dbHandler.getWritableDatabase().delete(MyDBHandler.TABLE_FRIENDS,null,null);

                    try {
                        loadFriends();
                    } catch (JSONException e) {
                        Log.e("Load friends error",e.getMessage());
                        e.printStackTrace();
                    }

                    showFriendsFromDb();

                    setButtonVisibility("login");
                }
                break;
            default:
                setButtonVisibility("logout");

        }
    }

    public void loadFriends() throws JSONException {
        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl("https://api.vk.com/method/friends.get?user_id="+uid+"&order=name&fields=domain,sex,bdate,city,country,timezone,photo_50");
        //JSONObject jsonObject = jsonParser.getJSONFromUrl("https://api.vk.com/method/friends.get?user_id=168934373&order=name&count=10&fields=domain,sex,bdate,city,country,timezone,photo_50");

        Log.i("jsonObject", jsonObject.toString());

        JSONArray jsonArray = (JSONArray) jsonObject.get("response");


        if (jsonArray != null && jsonArray.length() > 0) {
            for (int n = 0; n < jsonArray.length(); n++) {

                JSONObject object = jsonArray.getJSONObject(n);
                Log.i("friend : ",object.toString());

                if (!object.toString().contains("deleted")&& !object.toString().contains("DELETED")) {
                    Friend friend = new Friend(object.getString("first_name"), object.getString("last_name"), object.getString("photo_50"));
                    dbHandler.addFriend(friend);
                }

            }
        }
    }

    public void showFriendsFromDb(){
        MyDBHandler dbHandler;
        dbHandler = new MyDBHandler(this, null, null, 1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();


        Cursor cursor = dbHandler.getFriendList();

        ListView lvlitems = (ListView) findViewById(R.id.lvlitems);
        lvlitems.setTextFilterEnabled(true);
        final TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, cursor);
        lvlitems.setAdapter(todoAdapter);
    }


    private void setButtonVisibility(String state) {
        switch (state) {
            case "start":
                loginButton.setVisibility(View.VISIBLE);
                lvlitems.setVisibility(View.GONE);
                logoutButton.setVisibility(View.GONE);
                break;
            case "login":
                loginButton.setVisibility(View.GONE);
                lvlitems.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.VISIBLE);
                break;
            case "logout":
                loginButton.setVisibility(View.VISIBLE);
                lvlitems.setVisibility(View.GONE);
                logoutButton.setVisibility(View.GONE);
                break;
        }
    }
}
