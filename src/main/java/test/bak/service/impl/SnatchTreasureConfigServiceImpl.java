package test.bak.service.impl;

import com.sun.deploy.association.utility.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import test.bak.mapper.SnatchTreasureConfigMapper;
import test.bak.model.SnatchTreasureConfig;
import test.bak.service.ISnatchTreasureConfigService;
import test.bak.service.ISnatchTreasureRecordService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: com.qs.webside.activity.service.impl
 * @Author: Yangy
 * @CreateTime: 2018-08-30 18:35
 * @Description:
 **/
//@Service
//public class SnatchTreasureConfigServiceImpl implements ISnatchTreasureConfigService {
//
//    @Autowired
//    private SnatchTreasureConfigMapper snatchTreasureConfigMapper;
//
//    @Resource
//    private IActiAwardService actiAwardService;
//
//    @Resource
//    private ISnatchTreasureRecordService snatchTreasureRecordService;
//
//    @Override
//    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_CONFIG_CACHE,allEntries = true)
//    public int deleteByPrimaryKey(Integer id) {
//        return snatchTreasureConfigMapper.deleteByPrimaryKey(id);
//    }
//
//    @Override
//    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_CONFIG_CACHE,allEntries = true)
//    public int insert(SnatchTreasureConfig record) {
//        return snatchTreasureConfigMapper.insert(record);
//    }
//
//    @Override
//    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_CONFIG_CACHE,allEntries = true)
//    public int insertSelective(SnatchTreasureConfig record) {
//        return snatchTreasureConfigMapper.insertSelective(record);
//    }
//
//    @Override
//    public SnatchTreasureConfig selectByPrimaryKey(Integer id) {
//        return snatchTreasureConfigMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_CONFIG_CACHE,allEntries = true)
//    public int updateByPrimaryKeySelective(SnatchTreasureConfig record) {
//        return snatchTreasureConfigMapper.updateByPrimaryKeySelective(record);
//    }
//
//    @Override
//    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_CONFIG_CACHE,allEntries = true)
//    public int updateByPrimaryKeyWithBLOBs(SnatchTreasureConfig record) {
//        return snatchTreasureConfigMapper.updateByPrimaryKeyWithBLOBs(record);
//    }
//
//    @Override
//    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_CONFIG_CACHE,allEntries = true)
//    public int updateByPrimaryKey(SnatchTreasureConfig record) {
//        return snatchTreasureConfigMapper.updateByPrimaryKey(record);
//    }
//
//    @Override
//    @Cacheable(value = CacheConstan.SNATCH_TREASURE_CONFIG_CACHE,key = "#root.methodName")
//    public SnatchTreasureConfig selectTreasureConfigOfOpen() {
//        return snatchTreasureConfigMapper.selectTreasureConfigOfOpen();
//    }
//
//    @Override
//    public Map<String,Object> getTearsurePageInfo(int mid) {
//
//        Map<String,Object> resultMap = new HashMap<>();
//
//        //先获取开启状态的夺宝配置
//        SnatchTreasureConfig treasureConfig = this.selectTreasureConfigOfOpen();
//
//        if(ObjectUtils.isEmpty(treasureConfig)){
//            resultMap.put(CommonContants.SVFLAG,AppConstants.Result.FAILURE_1002);
//            resultMap.put(CommonContants.DATA,Constants.SnatchTreasureMessage.NO_SNATCH_TREASURE_CONFIG);
//            return resultMap;
//        }
//
//        //根据配置获取对应奖品配置
//        List<ActiAward> awardList = actiAwardService.selectByTypeKey(AppConstants.ActivityType.SNATCH_TREASURE_ACTIVITY_TYPE);
//        if(ObjectUtils.isEmpty(awardList)){
//            resultMap.put(CommonContants.SVFLAG,AppConstants.Result.FAILURE_1003);
//            resultMap.put(CommonContants.DATA,Constants.SnatchTreasureMessage.NO_SNATCH_TREASURE_AWARD_CONFIG);
//            return resultMap;
//        }
//
//        //获取当天期数的配置和下一期的
//        String nowDate = DateUtil.getNewDate();
//        String nextDate = DateUtil.getNextDay(new Date());
//        awardList = awardList.stream().filter(award -> treasureConfig.getId().intValue() == Integer.parseInt(award.getExt1())
//            && (nowDate.equals(award.getExt2().substring(0,8)) || nextDate.equals(award.getExt2().substring(0,8)))).collect(Collectors.toList());
//
//        //根据用户夺宝记录显示已参与的用户夺宝信息
//        List<Map<String,Object>> recordList = snatchTreasureRecordService.selectPresentTreasureRecords(awardList.get(0).getExt2());
//
//        //当前用户的夺宝码数量
//        List<Map<String,Object>> userRecords = snatchTreasureRecordService.selectRecordCountsByMid(mid,awardList.get(0).getExt2());
//        //创建两个数组存放产品的用户已抢夺份数([0])，所有用户已抢夺份数([1])及总份数([2])
//        int [] ary_one = new int[3];
//        int [] ary_two = new int[3];
//
//        if(ObjectUtils.isEmpty(userRecords)){
//            ary_one[0] = 0;
//            ary_two[0] = 0;
//        }else{
//            if("1".equals(userRecords.get(0).get("range").toString())){
//
//                ary_one[0] = Integer.parseInt(userRecords.get(0).get("num").toString());
//
//                if(ObjectUtils.isEmpty(userRecords.get(1))){
//                    ary_two[0] = 0;
//                }else {
//                    ary_two[0] = Integer.parseInt(userRecords.get(1).get("num").toString());
//                }
//
//            }else{
//                ary_one[0] = 0;
//                ary_two[0] = Integer.parseInt(userRecords.get(0).get("num").toString());
//            }
//
//        }
//
//        ary_one[1] = Integer.parseInt(snatchTreasureRecordService.selectRecordCountsByRange(awardList.get(0).getExt2(),1).get("num").toString());
//        ary_two[1] = Integer.parseInt(snatchTreasureRecordService.selectRecordCountsByRange(awardList.get(0).getExt2(),2).get("num").toString());
//        ary_one[2] = awardList.get(0).getAwardNum().intValue()/awardList.get(0).getIntegral().intValue();
//        ary_two[2] = awardList.get(1).getAwardNum().intValue()/awardList.get(1).getIntegral().intValue();
//
//        resultMap.put(CommonContants.SVFLAG,AppConstants.Result.SUCCESS);
//        resultMap.put(CommonContants.DATA,recordList);
//        resultMap.put("treasureInfo",awardList);   //夺宝的宝贝信息
//        resultMap.put("treasure1",ary_one);
//        resultMap.put("treasure2",ary_two);
//
//        return resultMap;
//    }
//}
