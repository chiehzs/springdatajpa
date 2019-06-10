package com.itheima.dao;

import com.itheima.domain.LinkMan;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 编写符合SpringDataJpa规范的dao接口：
 * 1） 接口所在的包已经在核心配置文件中指定
 * 2）我们编写的接口要继承两个接口：
 * JpaRepository<要操作的实体类,主键类型>
 * JpaSpecificationExecutor<要操作的实体类>
 */
public interface LinkManDao extends PagingAndSortingRepository<LinkMan, Long> , JpaSpecificationExecutor<LinkMan>{
}
