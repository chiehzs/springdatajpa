package com.itheima.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_role")
public class SysRole implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    //维护用户关系
    /**
     *  @ManyToMany  多对多
     *     targetEntity： 对方类型
     *   @JoinTable： 映射中间表
     *      name: 中间表表名称
     *      joinColumns: 当前方在中间表定义外键字段名称
     *      inverseJoinColumns: 对方在中间表定义外键字段名称
     *      mappedBy: 把外键维护权交给对方，对方的属性名称
     *      cascade: 级联操作
     *        CascadeType.PERSIST： 级联添加
     *        CascadeType.REMOVE: 级联删除
     *        CascadeType.ALL： 所有级联操作
     */
    @ManyToMany(targetEntity = SysUser.class , mappedBy = "roles" , fetch = FetchType.EAGER)
    /*
     * 注释了这段代码,在@ManyToMany处加上mappedBy = "roles " , 放弃对中间表的维护,解决重复维护中间表的异常:
     * Caused by: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '1-1' for key 'PRIMARY'
     *          @JoinTable(name = "user_role" ,
     *              joinColumns = @JoinColumn(name = "role_id") ,
     *              inverseJoinColumns = @JoinColumn(name = "user_id"))*/
    private Set<SysUser> users = new HashSet<>(0);

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<SysUser> getUsers() {
        return users;
    }

    public void setUsers(Set<SysUser> users) {
        this.users = users;
    }

}
