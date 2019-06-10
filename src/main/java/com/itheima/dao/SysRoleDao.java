package com.itheima.dao;

import com.itheima.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysRoleDao extends JpaRepository<SysRole, Long> , JpaSpecificationExecutor<SysRole>{
}
