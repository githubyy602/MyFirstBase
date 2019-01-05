package test;

import java.io.*;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: test
 * @Author: Yangy
 * @CreateTime: 2018-12-10 11:00
 * @Description:
 **/
public class IOTest {

    public static void printContent(File file){
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//            byte [] bytes = new byte[128];
//
//            while (fis.read(bytes) != -1){
//                    System.out.println(new String(bytes,"utf-8"));
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(fis != null){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        Reader reader = null;

        try {
            reader = new FileReader(file);
            char [] chars = new char[128];

            while (reader.read(chars) != -1){
                System.out.println(chars);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("==============begin=============");
        File file = new File("E:\\CompanyFile\\ProjectFile\\返回值记录.txt");
        printContent(file);
        System.out.println("===============end==============");
    }

}
