package com.qs.service;

import com.qs.entity.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> selectRolesByUserId(Integer userid);;

    public List<String> selectRoleNames(Integer userid);
}
