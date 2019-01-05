package com.qs.serviceImpl;

import com.qs.dao.RoleMapper;
import com.qs.entity.Role;
import com.qs.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectRolesByUserId(Integer userid) {
        return roleMapper.selectRolesByUserId(userid);
    }

    /**
     * 根据用户id获取所有角色名
     * @param userid
     * @return
     */
    public List<String> selectRoleNames(Integer userid){
        List<Role> list = this.selectRolesByUserId(userid);
        List<String> roleNameList = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            roleNameList.add(list.get(i).getRolename());
        }

        return  roleNameList;
    };
}
