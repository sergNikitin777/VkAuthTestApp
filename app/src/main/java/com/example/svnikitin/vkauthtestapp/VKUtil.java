package com.example.svnikitin.vkauthtestapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by svnikitin on 06.02.2018.
 */

public class VKUtil {
    public static String[] parseRedirectUrl(String url) throws Exception {
        String access_token = extractPattern(url, "access_token=(.*?)&");
        String hash = extractPattern(url, "direct_hash=(.*?)&");
        String user_id = extractPattern(url, "user_id=(\\d*)");
        if (user_id == null || user_id.length() == 0 || access_token == null || access_token.length() == 0) {
            throw new Exception("Failed to parse redirect url " + url);
        }
        return new String[]{access_token, user_id, hash};
    }

    public static String[] parseRedirectUrlHash(String url) throws Exception {
        String hash = extractPattern(url, "hash=(.*?)&");
        if (hash == null ) {
            throw new Exception("Failed to parse redirect url " + url);
        }
        return new String[]{hash};
    }

    public static String extractPattern(String string, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(string);
        if (!m.find())
            return null;
        return m.toMatchResult().group(1);
    }
}
