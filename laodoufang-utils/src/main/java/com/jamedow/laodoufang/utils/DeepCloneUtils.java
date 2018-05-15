package com.jamedow.laodoufang.utils;

import java.io.*;

/**
 * 深度克隆
 *
 * @author jamedow
 * @version [版本号, 2018年03月01日]
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class DeepCloneUtils implements Serializable {
    private static final long serialVersionUID = 3747630134243407984L;

    public <T> T cloneBean(T t) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            // 将sourceObj对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。
            // 所以利用这个特性可以实现对象的深拷贝
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(t);
            // 将流序列化成对象
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object targetOjb = objectInputStream.readObject();
            return (T) targetOjb;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                byteArrayOutputStream = null;
                byteArrayInputStream = null;
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
