package com.qs.serviceImpl;

import com.qs.dao.PlayerMapper;
import com.qs.entity.Player;
import com.qs.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: com.qs.serviceImpl
 * @Author: Yangy
 * @CreateTime: 2018-08-29 16:02
 * @Description:
 **/
@Service
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    private PlayerMapper playerMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return playerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Player record) {
        return playerMapper.insert(record);
    }

    @Override
    public int insertSelective(Player record) {
        return playerMapper.insertSelective(record);
    }

    @Override
    public Player selectByPrimaryKey(Integer id) {
        return playerMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Player record) {
        return playerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Player record) {
        return playerMapper.updateByPrimaryKey(record);
    }

    @Override
    @Cacheable(value = "allDatas",key = "#root.methodName")
    public List<Player> selectAll() {
        return playerMapper.selectAll();
    }
}
