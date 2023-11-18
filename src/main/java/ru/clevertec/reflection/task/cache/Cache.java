package ru.clevertec.reflection.task.cache;

public interface Cache<T> {

    T save(Long id, T o);

    T getById(Long id);

    void delete(Long id);

    void setSizeCache(Integer sizeCache);

    void saveCache();

    void loadCache();

}
