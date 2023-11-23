package com.book.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    @Autowired
    private CacheManager cacheManager;

    public void clearCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) throw new NullPointerException(String.format("Cache %s not found", cacheName));
        cache.clear();
    }
}
