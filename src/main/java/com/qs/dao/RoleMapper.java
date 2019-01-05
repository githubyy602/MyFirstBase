package com.qs.dao;

import com.qs.entity.Role;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {
    int countByExample(Role example);

    int deleteByExample(Role example);

    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(Role example);

    Role selectByPrimaryKey(Integer roleid);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") Role example);

    int updateByExample(@Param("record") Role record, @Param("example") Role example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 根据用户id查询其所拥有的角色
     */
    List<Role> selectRolesByUserId(Integer userid);

}