package com.qs.filter;

import com.qs.entity.Permission;
import com.qs.entity.Role;
import com.qs.entity.User;
import com.qs.service.IPermissionService;
import com.qs.service.IRoleService;
import com.qs.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MyRealm extends AuthorizingRealm {

    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IPermissionService permissionService;

    /**
     * 为当前登录的Subject授予角色和权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        String userName = subject.getPrincipal().toString();
        //根据用户名查询
        User user = userService.selectUserByName(userName);

        if(user != null){
            List<Role> roleList = roleService.selectRolesByUserId(user.getId());
            List<String> roleNameList = roleService.selectRoleNames(user.getId());

            Set<String> permSet = new TreeSet<String>();

            for (int i = 0 ; i < roleList.size() ; i++){
                List<String> list = permissionService.selectPermissionName(roleList.get(i).getRoleid());

                for (int j = 0; j < list.size(); j++) {
                    permSet.add(list.get(j));
                }
            }

            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRoles(roleNameList);
            simpleAuthorizationInfo.addStringPermissions(permSet);

            return  simpleAuthorizationInfo;
        }

        return null;
    }

    /**
     * 验证当前登录的Subject
     * 本例中该方法的调用时机为TouristController.login()方法中执行Subject.login()的时候
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //authenticationToken即是TouristController.login()中Subject.login(token)方法传过来的参数
        AuthenticationInfo authenticationInfo = null;
        //获取当前用户名（两种方式）
//        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
//        String userName = token.getUsername();
        String userName = (String) authenticationToken.getPrincipal();
        //根据用户名查询
        User user = userService.selectUserByName(userName);
        if (user != null){
            authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
            this.setAuthenticationSession(user);
        }else {
            //账号不存在
            throw new UnknownAccountException();
        }

        return authenticationInfo;
    }

    /**
     * 设置session时效和存入用户信息到session
     * @param value
     */
    private void setAuthenticationSession(User value){
        //获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //获取session
        Session session = subject.getSession();
        //设置session时效
        session.setTimeout(30*60*1000);
        //添加用户信息到session
        session.setAttribute("currentUser",value);
    }
}
