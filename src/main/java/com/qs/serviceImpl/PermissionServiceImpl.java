package com.qs.serviceImpl;

import com.qs.dao.PermissionMapper;
import com.qs.entity.Permission;
import com.qs.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> selectPermissionByRoleId(Integer roleid) {
        return permissionMapper.selectPermissionByRoleId(roleid);
    }

    public List<String> selectPermissionName(Integer roleid){
        List<Permission> list = this.selectPermissionByRoleId(roleid);
        List<String>  permNameList = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            permNameList.add(list.get(i).getPermissions());
        }

        return permNameList;
    };
}
