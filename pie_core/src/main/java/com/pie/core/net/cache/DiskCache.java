package com.pie.core.net.cache;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jakewharton.disklrucache.DiskLruCache;
import com.pie.core.util.cipher.MD5;
import com.pie.core.util.system.AppUtil;
import com.pie.core.util.system.SDCardUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：磁盘缓存，KEY加密存储，可定制缓存时长
 */
public class DiskCache implements ICache{

    private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final int MAX_DISK_CACHE_SIZE = 20 * 1024 * 1024; // 20MB
    private static final int CACHE_NEVER_EXPIRE = -1;//永不过期
    public static final String CACHE_DISK_DIR = "disk_cache";//默认磁盘缓存目录

    private DiskLruCache cache;
    private Pattern compile;
    private long cacheTime = CACHE_NEVER_EXPIRE;

    public DiskCache(Context context) {
        this(context, SDCardUtils.getDiskCacheDir(context, CACHE_DISK_DIR), calculateDiskCacheSize(SDCardUtils.getDiskCacheDir(context, CACHE_DISK_DIR)));
    }

    public DiskCache(Context context, File diskDir, long diskMaxSize) {
        final String REGEX = "@createTime\\{(\\d+)\\}expireMills\\{((-)?\\d+)\\}@";
        compile = Pattern.compile(REGEX);
        try {
            cache = DiskLruCache.open(diskDir, AppUtil.getVersionCode(context), 1, diskMaxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) return;

        String name = getMd5Key(key);
        try {
            if (!TextUtils.isEmpty(get(name))) {
                cache.remove(name);
            }

            DiskLruCache.Editor editor = cache.edit(name);
            final String TAG_CACHE = "@createTime{createTime_v}expireMills{expireMills_v}@";
            String content = value + TAG_CACHE.replace("createTime_v", "" + Calendar.getInstance().getTimeInMillis())
                    .replace("expireMills_v", "" + cacheTime);
            editor.set(0, content);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(String key, Object value) {
        put(key, value != null ? new Gson().toJson(value) : null);
    }

    public String get(String key) {
        try {
            String md5Key = getMd5Key(key);
            DiskLruCache.Snapshot snapshot = cache.get(md5Key);
            if (snapshot != null) {
                String content = snapshot.getString(0);

                if (!TextUtils.isEmpty(content)) {
                    Matcher matcher = compile.matcher(content);
                    long createTime = 0;
                    long expireMills = 0;
                    while (matcher.find()) {
                        createTime = Long.parseLong(matcher.group(1));
                        expireMills = Long.parseLong(matcher.group(2));
                    }
                    int index = content.indexOf("@createTime");

                    if ((createTime + expireMills > Calendar.getInstance().getTimeInMillis())
                            || expireMills == CACHE_NEVER_EXPIRE) {
                        return content.substring(0, index);
                    } else {
                        cache.remove(md5Key);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String key) {
        try {
            cache.remove(getMd5Key(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isContains(String key) {
        try {
            DiskLruCache.Snapshot snapshot = cache.get(getMd5Key(key));
            return snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isClosed() {
        return cache.isClosed();
    }


    @Override
    public void clear() {
        try {
            cache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DiskCache setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
        return this;
    }

    private String getMd5Key(String key) {
        return MD5.getMessageDigest(key.getBytes());
    }


    private static long calculateDiskCacheSize(File dir) {
        long size = 0;
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            StatFs statFs = new StatFs(dir.getAbsolutePath());
            long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();
            size = available / 50;
        } catch (IllegalArgumentException ignored) {
        }
        return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
    }

}
