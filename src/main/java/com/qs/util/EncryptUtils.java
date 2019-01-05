package com.qs.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;

public class EncryptUtils {

    /**
     * 使用base64加密
     * @param pwd
     * @return
     */
    public static String encryptByBase64(String pwd){
        //CodecSupport支持string和byte之间的互换
        // byte [] bytes = pwd.getBytes();
        return Base64.encodeToString(CodecSupport.toBytes(pwd));
    }

    /**
     * 使用base64解密
     */
    public  static  String decryptByBase64(String pwd){
        return Base64.decodeToString(pwd);
    }

    /**
     * 使用Hex进行16进制加密
     * @param pwd
     * @return
     */
    public  static  String encryptByHex(String pwd){
        return Hex.encodeToString(pwd.getBytes());
    }

    /**
     * 使用Hex进行解密
     * @param pwd
     * @return
     */
    public  static  String decryptByHex(String pwd){
        return  Hex.encodeToString(pwd.getBytes());
    }

    /**
     * 使用MD5加密
     * @param pwd
     */
    public static  String encryptByMD5(String pwd){
        //使用用户名生成salt
//        String cridentialsSalt = ByteSource.Util.bytes(username).toString();
        //使用UUID生成盐
        String cridentialsSalt = UUID.randomUUID().toString();
        //SimpleHash是shiro可以自定义指定加密类型的，如MD5,SHA-1，SHA-512等。下面两种加密格式一样
        //String encPwd = new SimpleHash("MD5",pwd,cridentialsSalt,2).toBase64();
        String encPwd = new Md5Hash(pwd,cridentialsSalt,2).toBase64();
        return encPwd;
    }


    public  static  void  main(String [] args){
        String str = "test";
        String hexStr = encryptByHex(str);
        System.out.println("Hex加密后："+hexStr);
        System.out.println("Hex解密后："+decryptByHex(hexStr));
        String baseStr = encryptByBase64(str);
        System.out.println("Base64加密后："+baseStr);
        System.out.println("Base64解密后："+decryptByBase64(baseStr));
        System.out.println("ByteSource生成盐："+ByteSource.Util.bytes("admin").toString());
        System.out.println("UUID生成盐："+UUID.randomUUID().toString());
        System.out.println("MD5加密后："+encryptByMD5("test"));
    }

}
