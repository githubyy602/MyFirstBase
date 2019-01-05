package com.qs.service;

import com.qs.entity.Player;

import java.util.List;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: com.qs.service
 * @Author: Yangy
 * @CreateTime: 2018-08-29 16:01
 * @Description:
 **/
public interface IPlayerService {

    int deleteByPrimaryKey(Integer id);

    int insert(Player record);

    int insertSelective(Player record);

    Player selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);

    List<Player> selectAll();
}
