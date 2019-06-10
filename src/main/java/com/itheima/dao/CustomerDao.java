package com.itheima.dao;

import com.itheima.domain.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 编写符合SpringDataJpa规范的dao接口：
 * 1） 接口所在的包已经在核心配置文件中指定
 * 2）我们编写的接口要继承两个接口：
 * JpaRepository<要操作的实体类,主键类型>
 * JpaSpecificationExecutor<要操作的实体类>
 */
public interface CustomerDao extends PagingAndSortingRepository<Customer , Long> , JpaSpecificationExecutor<Customer>{

    List<Customer> findByCustId(Long id);

    @Query(value = "from Customer where custName = ?1")
    List<Customer> queryByCustName(String custName);

    @Modifying
    @Query(value = "update Customer set custAge = ?1 where custId = ?2")
    void updateCustAge(Integer age , Long id);

    @Modifying
    @Query(value = "delete from cst_customer where cust_name = ?1" , nativeQuery = true)
    void deleteByName(String name);
}
