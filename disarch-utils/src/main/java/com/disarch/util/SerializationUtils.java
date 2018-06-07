package com.disarch.util;

import java.io.*;

public class SerializationUtils {
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
            } finally {
                if (oos != null) {
                    oos.close();
                }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }

    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return ois.readObject();
            } finally {
                if (ois != null) {
                    ois.close();
                }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to deserialize object", ex);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Failed to deserialize object type", ex);
        }
    }
}
