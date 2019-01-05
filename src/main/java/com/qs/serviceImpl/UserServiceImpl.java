package com.qs.serviceImpl;

import com.qs.constans.CacheConstans;
import com.qs.dao.UserMapper;
import com.qs.entity.User;
import com.qs.service.IUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User selectUserByName(String username){
        return userMapper.selectUserByName(username);
    };

    /**
     * @Author Yangy
     * @Description //TODO
     * @Date 2018/7/10 17:17
     * @Param [username, sex]
     * @return java.util.List<com.qs.entity.User>
     **/
    @Cacheable(value = {CacheConstans.USER_SELECT_QUERY},key = "#root.method+':'+#root.args[0]")
    public List<User> selectUserBySelective(String username, int sex) {
        return userMapper.selectUserBySelective(username,sex);
    }
}
