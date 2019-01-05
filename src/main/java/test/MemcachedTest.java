package test;

import com.qs.util.MemcachedUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemcachedTest {

    private static List<Object> list;


    private static void init(){
        list = new ArrayList<>();
        list.add("aaaaaaaaaa");
        list.add("bbbbbbbbb");
        list.add("cccccccc");
        list.add("ddddddd");
        list.add("eeeeee");
        list.add("fffff");
    }

    public static void main(String[] args) {

        for (Object obj : list) {
            System.out.println(obj.toString());
        }
    }

}
