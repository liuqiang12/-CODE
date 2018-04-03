package com.idc.action.cap;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by mylove on 2018/1/22.
 */
public class DataCache {
    private volatile static DataCache singleton;
    private LoadingCache<String, Map> loadingCache;

    private DataCache() {
        loadingCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).maximumSize(10)
                .build(new CacheLoader<String, Map>() {
                    @Override
                    public HashMap load(String name) throws Exception {
                        return null;
                    }
                });
    }

    public static DataCache getSingleton() {
        if (singleton == null) {
            synchronized (DataCache.class) {
                if (singleton == null) {
                    singleton = new DataCache();
                }
            }
        }
        return singleton;
    }

    public LoadingCache getCache() {
        return this.loadingCache;
    }
}
