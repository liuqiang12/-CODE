package com.idc.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by mylove on 2017/6/27.
 */
public class GuavaCache {
    public static Cache getCache() {
        return GCache.getInstance().getCache();
    }
}

class GCache {
    private static GCache ourInstance = new GCache();

    public static GCache getInstance() {
        return ourInstance;
    }

    private Cache<String, String> cache = null;

    private GCache() {

    }

    public Cache<String, String> getCache() {
        if (cache == null) {
            synchronized (GuavaCache.class) {
                if (cache == null) {
                    cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(3, TimeUnit.MINUTES).build();
                }
            }
        }
        return cache;
    }
}
