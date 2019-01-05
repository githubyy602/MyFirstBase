package test.bak.service;


import test.bak.model.SnatchTreasureConfig;

import java.util.Map;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: com.qs.webside.activity.service
 * @Author: Yangy
 * @CreateTime: 2018-08-30 18:35
 * @Description:
 **/
public interface ISnatchTreasureConfigService {

    int deleteByPrimaryKey(Integer id);

    int insert(SnatchTreasureConfig record);

    int insertSelective(SnatchTreasureConfig record);

    SnatchTreasureConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SnatchTreasureConfig record);

    int updateByPrimaryKeyWithBLOBs(SnatchTreasureConfig record);

    int updateByPrimaryKey(SnatchTreasureConfig record);

    /**
     * @Author Yangy
     * @Description 查询开启状态的夺宝配置
     * @Date 2018/8/30 19:05
     * @Param []
     * @return test.bak.model.SnatchTreasureConfig
     **/
    SnatchTreasureConfig selectTreasureConfigOfOpen();

    /**
     * @Author Yangy
     * @Description 获取夺宝页面信息
     * @Date 2018/8/30 18:50
     * @Param [mid]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> getTearsurePageInfo(int mid);

}
