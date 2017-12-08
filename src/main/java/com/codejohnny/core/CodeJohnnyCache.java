package com.codejohnny.core;


import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.ElementAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.Serializable;


public final class CodeJohnnyCache {

    private static final Logger logger = LoggerFactory.getLogger(CodeJohnnyCache.class);

    private static CodeJohnnyCache instance;
    private static JCS codeJohnnyCache;
    private CodeJohnnyCache() {
        try {
        	codeJohnnyCache = JCS.getInstance("default");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static CodeJohnnyCache getInstance() {
        synchronized (CodeJohnnyCache.class) {
            if (instance == null) {
                instance = new CodeJohnnyCache();
            }
        }

        return instance;
    }

    public Object get(Serializable key) {
        return codeJohnnyCache.get(key);
    }

    public void put(Serializable key, Serializable object, double maxLifeMinutes)
    {
        ElementAttributes attributes = new ElementAttributes();
        attributes.setIsEternal(false);
        long maxLifeSeconds = (long)(maxLifeMinutes * 60D);
        attributes.setMaxLifeSeconds(maxLifeSeconds);
        try {
        	codeJohnnyCache.put(key, object, attributes);
        } catch (CacheException e) {
        }
    }

    public void put(Serializable key, Serializable object) {
        try {
        	codeJohnnyCache.put(key, object);
        } catch (CacheException e) {
        }
    }

    public void remove(Serializable key) {
        try {
        	codeJohnnyCache.remove(key);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
        	codeJohnnyCache.clear();
        }
        catch (CacheException e) {
            String msg = "Failure to clear cache:" + e.getMessage();
            logger.error(msg);
        }
    }
}