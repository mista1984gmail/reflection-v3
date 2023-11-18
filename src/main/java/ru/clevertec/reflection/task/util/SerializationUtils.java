package ru.clevertec.reflection.task.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerializationUtils {

    private static final Logger logger = LogManager.getLogger(SerializationUtils.class);
    private static final String FILENAME = "cache.txt";

    public static void serialize(Object objectToSerialize) {
        try (OutputStream out = new FileOutputStream(FILENAME);
             ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(objectToSerialize);
            logger.info("Cache was saved.");
        } catch (IOException e) {
            logger.error("Cache was not saved.", e);
        }
    }

    public static Object deserialize() {
        Object object = null;
        try (InputStream in = new FileInputStream(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(in)) {
            object = ois.readObject();
            logger.info("Cache is loaded.");
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Cache can not be loaded.", e);
        }
        return object;
    }

}