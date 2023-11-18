package ru.clevertec.reflection.task.util;

import ru.clevertec.reflection.task.cache.Cache;
import ru.clevertec.reflection.task.cache.factory.CacheFactory;
import ru.clevertec.reflection.task.config.LoadProperties;

public class Constants {

    public static final String CACHE_SIZE = "cache_size";
    public static final String CACHE_TYPE = "cache_type";
    public static final String CACHE_TYPE_LRU = "LRU";
    public static final String CACHE_TYPE_LFU = "LFU";
    public static final String PROPERTIES_FILE_NAME = "src/main/resources/application.yml";
    public static final String URL = "database_url";
    public static final String USERNAME = "database_username";
    public static final String PASSWORD = "database_password";

    public static final Cache CACHE = new CacheFactory().createCache(LoadProperties.getProperty()
                                                                                   .get(Constants.CACHE_TYPE));

}
