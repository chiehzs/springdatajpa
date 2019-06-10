package com.itcast;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.LinkManDao;
import com.itheima.dao.SysRoleDao;
import com.itheima.dao.SysUserDao;
import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import com.itheima.domain.SysRole;
import com.itheima.domain.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo1 {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    @Test
    @Transactional
    @Rollback(false)
    public void test1(){

        //1.创建1个客户，创建两个联系人
        Customer c = new Customer();
        c.setCustName("黑马科技");

        LinkMan lkm1 = new LinkMan();
        lkm1.setLkmName("小红");

        LinkMan lkm2 = new LinkMan();
        lkm2.setLkmName("小翠");

        //2.建立客户与联系人关系
        //客户->联系人
        c.getLinkmans().add(lkm1);
        c.getLinkmans().add(lkm2);

        //联系人->客户
        lkm1.setCustomer(c);
        lkm2.setCustomer(c);

        //3.保存客户，保存联系人
        customerDao.save(c);
        linkManDao.save(lkm1);
        linkManDao.save(lkm2);

    }

        /**
         * 删除
         */
        @Test
        @Transactional
        @Rollback(false)
        public void test2(){
            //多方对应的表，叫从表; 1方对应的表，叫主表

            //删除从表    没有问题
            //linkManDao.deleteById(8L);

            /**
             * 删除主表
             *     1 没有被从表引用   直接删除
             *     2 被从表引用
             *        2.1 主表方没有外键维护权    不能直接删除
             *            这种情况下，还是想把主表数据删除，就会用到级联删除啦！！！    应该把级联删除设置在1方（主表方），效果是：删除主表数据的时候，先删除从表数据
             *
             *        2.2 主表方拥有外键维护权  (要在实体类那里设置外键的维护权)  从表的外键值设置为null，然后主表被删除,直接执行customerDao.deleteById(100L);这行语句,从表的值就会自动设置为null,然后删除了主表的id为100的数据（从表数据还在） （推荐使用）
             *
             */
            customerDao.deleteById(99L);
    }


    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;

    /**
     * 多对多添加
     */
    @Test
    @Transactional
    @Rollback(false)
    public void test3(){
        //1.创建2个用户，2个角色
        SysUser u1 = new SysUser();
        u1.setUserName("张三");

        SysUser u2 = new SysUser();
        u2.setUserName("李四");

        SysRole r1 = new SysRole();
        r1.setRoleName("动作指导");

        SysRole r2 = new SysRole();
        r2.setRoleName("视觉总监");


        //2.建立用户和角色关系
        // 小苍 是 动作指导 和 视觉总监， 小泽是 视觉总监
        //用户->角色
        u1.getRoles().add(r1);
        u1.getRoles().add(r2);
        u2.getRoles().add(r2);

        //角色->用户
        r1.getUsers().add(u1);
        r2.getUsers().add(u1);
        r2.getUsers().add(u2);

        //3.保存用户和角色
        sysUserDao.save(u1);
        sysUserDao.save(u2);
        sysRoleDao.save(r1);
        sysRoleDao.save(r2);
    }

    /** 删除 */
    @Test
    @Transactional
    @Rollback(false)
    public void test4(){

        /**
         * 删除用户（删除角色）
         *     1 用户方有中间表维护权     直接删除（ 先把该用户在中间表对应的数据先删除，再把该用户删除  ） （推荐）
         *     2 用户方没有中间维护权    不能直接删除
         *        这种情况下，必须删除用户，就是使用级联删除 (删除用户的时候，同时把中间表和角色同时删除啊！！！)  （尽量不要使用，很危险！！！）
         */
        sysUserDao.deleteById(1L);

    }


    /**
     * 一对多
     *      客户->联系人
     *  */
    @Test
    @Transactional
    @Rollback(false)
    public void test5(){

        Customer customer = customerDao.findById(1L).get();
        System.out.println("客户的名字:" + customer.getCustName());

        /** 查询客户下的联系人 */
        Set<LinkMan> linkmans = customer.getLinkmans();
        for (LinkMan linkman : linkmans) {
            System.out.println(linkman);
        }

    }

    @Test
    @Transactional
    @Rollback(false)
    public void test6(){

        SysUser sysUser = sysUserDao.findById(1L).get();
        System.out.println("====="+sysUser+"===========");
        Set<SysRole> roles = sysUser.getRoles();
        for (SysRole role : roles) {
            System.out.println(role.getRoleName());
        }

    }

}
