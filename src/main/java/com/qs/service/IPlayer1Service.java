package com.qs.service;

import com.qs.entity.Player;
import com.qs.entity.Player1;

import java.util.List;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: com.qs.service
 * @Author: Yangy
 * @CreateTime: 2018-08-29 16:01
 * @Description:
 **/
public interface IPlayer1Service {
    int insert(Player1 record);

    int insertSelective(Player1 record);

    int batchInsert(List<Player> list);
}
