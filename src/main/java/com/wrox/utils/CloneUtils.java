package com.wrox.utils;

import java.io.*;

/**
 * Created by Dengbin on 2015/10/5
 */
public class CloneUtils {

    @SuppressWarnings("unchecked")
    public static <T> T clone(T obj) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            oos.flush();
            ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
        return (T) ois.readObject();
    }

}
