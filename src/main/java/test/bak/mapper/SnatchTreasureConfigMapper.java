package test.bak.mapper;


import test.bak.model.SnatchTreasureConfig;

public interface SnatchTreasureConfigMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(SnatchTreasureConfig record);

    int insertSelective(SnatchTreasureConfig record);

    SnatchTreasureConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SnatchTreasureConfig record);

    int updateByPrimaryKeyWithBLOBs(SnatchTreasureConfig record);

    int updateByPrimaryKey(SnatchTreasureConfig record);

    SnatchTreasureConfig selectTreasureConfigOfOpen();
}