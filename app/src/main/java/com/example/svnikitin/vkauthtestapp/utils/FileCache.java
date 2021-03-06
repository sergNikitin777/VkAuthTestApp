package com.example.svnikitin.vkauthtestapp.utils;

/**
 * Created by snikitin on 25.01.16.
 */

import android.content.Context;

import java.io.File;

public class FileCache {

	public static final String DOWNLOAD_VKAUTHTESTAPP = "/Download/vkauthtestapp/";
	public static final String LAZY_LIST = "LazyList";

	private File cacheDir;

	public FileCache(Context context, String subDir) {
		//Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			if (subDir != null) {
				cacheDir = new File(android.os.Environment.getExternalStorageDirectory() + DOWNLOAD_VKAUTHTESTAPP + subDir, LAZY_LIST);
			} else {
				cacheDir = new File(android.os.Environment.getExternalStorageDirectory() + DOWNLOAD_VKAUTHTESTAPP, LAZY_LIST);
			}
		else {
			cacheDir = new File(context.getCacheDir().getAbsolutePath() + DOWNLOAD_VKAUTHTESTAPP + subDir + "/");
		}
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public File getFile(String url) {
		//I identify images by hashcode. Not a perfect solution, good for the demo.
		String filename = String.valueOf(url.hashCode());
		//Another possible solution (thanks to grantland)
		//String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}