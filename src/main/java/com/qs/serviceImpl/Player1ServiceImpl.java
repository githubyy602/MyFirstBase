package com.qs.serviceImpl;

import com.qs.dao.Player1Mapper;
import com.qs.entity.Player;
import com.qs.entity.Player1;
import com.qs.service.IPlayer1Service;
import org.springframework.beans.factory.annotation.Autowired;
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
public class Player1ServiceImpl implements IPlayer1Service {

    @Autowired
    private Player1Mapper player1Mapper;

    @Override
    public int insert(Player1 record) {
        return player1Mapper.insert(record);
    }

    @Override
    public int insertSelective(Player1 record) {
        return player1Mapper.insertSelective(record);
    }

    @Override
    public int batchInsert(List<Player> list) {
        return player1Mapper.batchInsert(list);
    }
}
