package test;

import com.alibaba.fastjson.JSON;
import com.qs.entity.Player;
import com.qs.entity.Player1;
import com.qs.service.IPlayer1Service;
import com.qs.service.IPlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: test
 * @Author: Yangy
 * @CreateTime: 2018-08-29 16:09
 * @Description:
 **/
@Controller
@RequestMapping("/batch")
public class BantchController {

    @Resource
    private IPlayerService playerService;

    @Resource
    private IPlayer1Service player1Service;

    @ResponseBody
    @RequestMapping(value = "/batchAddData.do",method = RequestMethod.POST)
    public Object batchAddData(){
        Map<String,Object> resultMap = new HashMap<>();
        /*====================================使用mybatis自带批量插入=========================================*/
        //开始时间
//        try {
//            long startTime = System.currentTimeMillis();
//            System.out.println("开始时间：" + startTime);
//            //查询数据
//            List<Player> oldList = playerService.selectAll();
//            //查询消耗时间
//            System.out.println("查询结束时间："+System.currentTimeMillis()+",查询所花费时间:"+Double.parseDouble((System.currentTimeMillis()-startTime)/1000+"")+"秒");
//
//            ExecutorService executorService = Executors.newFixedThreadPool(30);
//
//            int n = oldList.size()/100000+1;
//            for (int i = 0 ; i< n; i++) {
//                List<Player> temp;
//
//                if(i == n-1){
//                    temp = oldList.subList(i*100000,oldList.size());
//                }else{
//                    temp = oldList.subList(i*100000,(i+1)*100000);
//                }
//
//                executorService.execute(new Thread(){
//                    @Override
//                    public void run() {
//                        player1Service.batchInsert(temp);
//                    }
//                });
//            }
////            player1Service.batchInsert(oldList);
//
//            System.out.println("批量插入结束时间："+System.currentTimeMillis()+",插入所花费时间:"+Double.parseDouble((System.currentTimeMillis()-startTime)/1000+"")+"秒");
//            resultMap.put("msg","success");
//        } catch (NumberFormatException e) {
//            resultMap.put("msg","fail");
//            e.printStackTrace();
//        }
        /*====================================使用mybatis自带批量插入=========================================*/

        /*====================================使用jdbc批量插入=========================================*/

        String url = "jdbc:mysql://localhost:3306/my_data_source?useUnicode=true&amp;characterEncoding=utf-8";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "123456";

        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);// 获取连接
            conn.setAutoCommit(false);// 关闭自动提交，不然conn.commit()运行到这句会报错
        } catch (Exception  e) {
            e.printStackTrace();
        }

        long startTime,selectEndTime=0;
        List<Player> oldList=null;

        // SQL前缀
        String prefix = "INSERT INTO player1 (day_time, playing,gp, type, lookon, gametype) VALUES";
        // 保存SQL后缀
        StringBuffer suffix;
        try {

            // 开始时间
            startTime = System.currentTimeMillis();
            System.out.println("开始时间：" + startTime);
            PreparedStatement pst = conn.prepareStatement("select * from player");
            oldList = playerService.selectAll();
//            oldList = pst.executeQuery();
            selectEndTime = System.currentTimeMillis();
            System.out.println("查询结束时间："+System.currentTimeMillis()+",查询所花费时间:"+Double.parseDouble((selectEndTime-startTime)/1000+"")+"秒");

            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // 比起st，pst会更好些
            pst = conn.prepareStatement("");//准备执行语句
            // 外层循环，总提交事务次数
            int out = oldList.size()/100000+1;
            for (int i = 0; i < out; i++) {
                suffix = new StringBuffer();
                // 第j次提交步长
                int size;

                if(i == out -1){
                    size = oldList.size();
                }else{
                    size = (i+1)*100000;
                }

                for (int j = i*100000; j < size; j++) {
                    // 构建SQL后缀
                    suffix.append("('" + oldList.get(j).getDayTime() + "', " + oldList.get(j).getPlaying().intValue() + ", "+oldList.get(j).getGp().intValue()+", '"+oldList.get(j).getType()+"', "+oldList.get(j).getLookon().intValue()+", "+oldList.get(j).getGametype().intValue()+"),");
                }
                // 构建完整SQL
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                // 添加执行SQL
                pst.addBatch(sql);
                // 执行操作
                pst.executeBatch();
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 关闭连接
            pst.close();
            conn.close();

            resultMap.put("msg","success");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            resultMap.put("msg","fail");
            e.printStackTrace();
        }
        // 耗时
        System.out.println(oldList.size()+"条数据插入花费时间 : " + (System.currentTimeMillis() - selectEndTime) / 1000 + " s"+"  插入完成");

        /*====================================使用jdbc批量插入=========================================*/

        return JSON.toJSON(resultMap);
    }

}
