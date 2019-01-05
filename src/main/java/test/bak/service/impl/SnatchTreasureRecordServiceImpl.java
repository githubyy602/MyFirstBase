package test.bak.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import test.bak.mapper.SnatchTreasureRecordMapper;
import test.bak.model.SnatchTreasureRecord;
import test.bak.service.ISnatchTreasureRecordService;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: com.qs.webside.activity.service.impl
 * @Author: Yangy
 * @CreateTime: 2018-08-30 18:38
 * @Description:
 **/
public class SnatchTreasureRecordServiceImpl implements ISnatchTreasureRecordService {

    @Autowired
    private SnatchTreasureRecordMapper snatchTreasureRecordMapper;

//    @Override
//    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,allEntries = true)
//    public int deleteByPrimaryKey(Integer id) {
//        return snatchTreasureRecordMapper.deleteByPrimaryKey(id);
//    }
//
//    @Override
//    @Caching(
//            evict = {
//                    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectRecordCountsByMid:'+#record.mid+#record.dateno" ),
//                    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectPresentTreasureRecords:'+#record.dateno" ),
//                    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectRecordCountsByRange:'+#record.dateno+#record.range" )
//            }
//    )
//    public int insert(SnatchTreasureRecord record) {
//        return snatchTreasureRecordMapper.insert(record);
//    }
//
//    @Override
//    @Caching(
//            evict = {
//                    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectRecordCountsByMid:'+#record.mid+#record.dateno" ),
//                    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectPresentTreasureRecords:'+#record.dateno" ),
//                    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectRecordCountsByRange:'+#record.dateno+#record.range" )
//            }
//    )
//    public int insertSelective(SnatchTreasureRecord record) {
//        return snatchTreasureRecordMapper.insertSelective(record);
//    }
//
//    @Override
//    public SnatchTreasureRecord selectByPrimaryKey(Integer id) {
//        return snatchTreasureRecordMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    @Caching(
//            evict = {
//                    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectRecordCountsByMid:'+#record.mid+#record.dateno" ),
//                    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectPresentTreasureRecords:'+#record.dateno" ),
//                    @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectRecordCountsByRange:'+#record.dateno+#record.range" )
//            }
//    )
//    public int updateByPrimaryKeySelective(SnatchTreasureRecord record) {
//        return snatchTreasureRecordMapper.updateByPrimaryKeySelective(record);
//    }
//
//    @Override
//    @Caching(
//        evict = {
//                @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectRecordCountsByMid:'+#record.mid+#record.dateno" ),
//                @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectPresentTreasureRecords:'+#record.dateno" ),
//                @CacheEvict(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "'selectRecordCountsByRange:'+#record.dateno+#record.range" )
//        }
//    )
//    public int updateByPrimaryKey(SnatchTreasureRecord record) {
//        return snatchTreasureRecordMapper.updateByPrimaryKey(record);
//    }
//
//    @Override
//    @Cacheable(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "#root.methodName+':'+#root.args[0]+':'+#root.args[1]")
//    public List<Map<String, Object>> selectRecordCountsByMid(int mid, String dateNo) {
//        return snatchTreasureRecordMapper.selectRecordCountsByMid(mid,dateNo);
//    }
//
//    @Override
//    @Cacheable(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "#root.methodName+':'+#root.args[0]")
//    public List<Map<String, Object>> selectPresentTreasureRecords(String dateNo) {
//        return snatchTreasureRecordMapper.selectPresentTreasureRecords(dateNo);
//    }
//
//    @Override
//    @Cacheable(value = CacheConstan.SNATCH_TREASURE_OF_USER_RECORD_CACHE,key = "#root.methodName+':'+#root.args[0]+':'+#root.args[1]")
//    public Map<String, Object> selectRecordCountsByRange(String dateNo, int range) {
//        return snatchTreasureRecordMapper.selectRecordCountsByRange(dateNo,range);
//    }
}
