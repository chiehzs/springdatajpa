package com.itcast;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class test {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void test1(){
        List<Customer> customerDaoByCustId = customerDao.findByCustId(1L);
        for (Customer customer : customerDaoByCustId) {
            System.out.println(customer);
        }
    }

    @Test
    public void test2(){
        List<Customer> customerDaoByCustId = customerDao.queryByCustName("北大");
        for (Customer customer : customerDaoByCustId) {
            System.out.println(customer);
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    public void test3(){
        customerDao.updateCustAge(18 , 1L);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void test4(){
        customerDao.deleteByName("北大");
    }

    @Test
    public void test5(){
        int page = 1;
        int size = 2;
        //page默认从0开始，所以传入参数时要减一
        Pageable pageable = PageRequest.of(page - 1 , size);
        Page<Customer> customers = customerDao.findAll(pageable);
        List<Customer> content = customers.getContent();
        for (Customer customer : content) {
            System.out.println(customer);
        }
    }

    @Test
    public void test6(){
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC , "custId") , new Sort.Order(Sort.Direction.ASC , "custAge"));
        List<Customer> all = (List<Customer>) customerDao.findAll(sort);
        for (Customer customer : all) {
            System.out.println(customer);
        }
    }

    @Test
    public void test7(){
        //分页
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC , "custId"));
        //排序
        int page = 1;
        int size = 2;
        //page默认从0开始，所以传入参数时要减一
        Pageable pageable = PageRequest.of(page - 1 , size , sort);
        Page<Customer> customers = customerDao.findAll(pageable);
        List<Customer> content = customers.getContent();
        for (Customer customer : content) {
            System.out.println(customer);
        }
    }

    @Test
    public void test8(){
        List<Customer> customers = customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Expression<Object> custName = root.get("custName");
                Predicate predicate = criteriaBuilder.equal(custName, "中山大学");
                return predicate;
            }
        });

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void test9(){
        List<Customer> customers = customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                Predicate like = criteriaBuilder.like(custName.as(String.class), "上%");
                return like;
            }
        });

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
    /**
     * String custName = "custName";
     String custAge = "custAge";
     List<Predicate> predicateList = new ArrayList<>();
     * */
    @Test
    public void test10(){
        List<Customer> customers = customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                String custName = "中";
                String custLevel = "VIP客户";
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.like(root.get("custName").as(String.class) ,  custName + "%"));
                predicateList.add(criteriaBuilder.equal(root.get("custLevel").as(String.class) , custLevel));
                Predicate[] predicates = new Predicate[predicateList.size()];
                Predicate predicate = criteriaBuilder.and(predicateList.toArray(predicates));
                return predicate;
            }
        });

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void test11(){
        /** 排序 */
        Sort sort = new Sort(Sort.Direction.DESC , "custId");
        /** 分页 */
        Pageable pageable = PageRequest.of(0 , 2 , sort);
        /** 动态查询 */
        Page<Customer> customers = customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                String custName = "学";
                String custLevel = "VIP客户";
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.like(root.get("custName").as(String.class) ,  "%" + custName + "%"));
                predicateList.add(criteriaBuilder.equal(root.get("custLevel").as(String.class) , custLevel));
                Predicate[] predicates = new Predicate[predicateList.size()];
                Predicate predicate = criteriaBuilder.and(predicateList.toArray(predicates));
                return predicate;
            }
        } , pageable);

        for (Customer customer : customers.getContent()) {
            System.out.println(customer);
        }
    }

}
