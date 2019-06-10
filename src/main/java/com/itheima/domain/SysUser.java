package com.itheima.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sys_id")
    private Long userId;

    @Column(name = "sys_name")
    private String userName;
    private String aaa;
    private Integer age;

    //维护用户关系
    /**
     *  @ManyToMany  多对多
     *     targetEntity： 对方类型
     *   @JoinTable： 映射中间表
     *      name: 中间表表名称
     *      joinColumns: 当前方在中间表定义外键字段名称
     *      inverseJoinColumns: 对方在中间表定义外键字段名称
     */
    @ManyToMany(targetEntity = SysRole.class)
    @JoinTable(name = "user_role" ,
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<SysRole> roles = new HashSet<>(0);

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
