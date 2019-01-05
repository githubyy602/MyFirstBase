package com.qs.service;

import com.qs.entity.Permission;

import java.util.List;

public interface IPermissionService {

    public List<Permission> selectPermissionByRoleId(Integer roleid);

    public List<String> selectPermissionName(Integer roleid);
}
