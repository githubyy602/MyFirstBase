package com.qs.dao;

import com.qs.entity.Player;
import com.qs.entity.Player1;

import java.util.List;

public interface Player1Mapper {
    int insert(Player1 record);

    int insertSelective(Player1 record);

    int batchInsert(List<Player> list);
}