package com.qs.util;

import java.io.*;

public class SerializeUtils {



    /**
     * 序列化
     * @param obj
     * @return
     */
    public static byte[] serialize(Object obj){

        if(obj == null) return  null;

        ByteArrayOutputStream out = null;
        byte [] result = null;

        try {
            //创建一个字节数组输出流
            out  = new ByteArrayOutputStream(128);
            //将字节数组输出流放入对象输出流
            ObjectOutputStream temp = new ObjectOutputStream(out);
            //将obj写入到流中
            temp.writeObject(obj);
            temp.flush();

            result = out.toByteArray();
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;

    }

    /**
     * 反序列化
     * @param args
     */
    public static Object deserialize(byte [] args){
        if(args == null || args.length == 0) return null;

        Object obj = null;

        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream(new ByteArrayInputStream(args));

            obj = inputStream.readObject();

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return obj;

    }


//    public static void main(String[] args) {
//        System.out.println(serialize("test"));
//        System.out.println("test".getBytes());
//        System.out.println(deserialize(serialize("test")));
//    }



}
