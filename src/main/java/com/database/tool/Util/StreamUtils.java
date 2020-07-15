package com.database.tool.Util;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Serialization tool class
 */
public class StreamUtils {

    public static <T> boolean writeObjectList(List<T> list, File file) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(list);
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Serialization, List
     */
    public static <T> boolean writeObject(List<T> list, File file) {
        T[] array = (T[]) list.toArray();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(array);
            out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deserialization,List
     */
    public static <E> List<E> readObjectForList(File file) {
        E[] object;
        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file))) {
            object = (E[]) out.readObject();
            return Arrays.asList(object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <E> List<E> readObjectList(File file) {
        List<E> objectList = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            objectList = (List<E>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectList;
    }


    /**
     * Make a deep copy of the collection
     * Note that generic classes need to be serialized (implementing Serializable)
     *
     * @param src
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<T> deepCopy(List<T> src) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(byteOut);
        ) {
            outputStream.writeObject(src);
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
                 ObjectInputStream inputStream = new ObjectInputStream(byteIn);
            ) {
                return (List<T>) inputStream.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}