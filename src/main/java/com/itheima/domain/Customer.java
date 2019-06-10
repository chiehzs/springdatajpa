package com.itheima.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/** 一对多 */
@Entity
@Table(name = "cst_customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //配置主键的生成策略
    @Column(name="cust_id") //指定和表中cust_id字段的映射关系
    private Long custId;
    @Column(name="cust_name") //指定和表中cust_name字段的映射关系
    private String custName;
    @Column(name="cust_source")
    private String custSource;
    @Column(name="cust_industry")
    private String custIndustry;
    @Column(name="cust_level")
    private String custLevel;
    @Column(name="cust_address")
    private String custAddress;
    @Column(name="cust_phone")
    private String custPhone;


    /** 维护联系人关系
     * 注意：
     *  1) Set集合不能为null，必须初始化
     *  2) 初始化元素为0
     */
    /**
     * @OneToMany
     *    targetEntity: 对方的类型
     * @JoinColumn： 映射外键
     *    name: 外键字段名称（不能省略）
     *    referencedColumnName: 外键参考的主键字段名称（可以省略）
     *    mappedBy: 把外键维护权交给对方，对方的属性名称
     *     cascade: 级联操作
     *        CascadeType.PERSIST： 级联添加
     *        CascadeType.REMOVE: 级联删除
     *        CascadeType.ALL： 所有级联操作
     */
    @OneToMany(targetEntity = LinkMan.class,mappedBy = "customer" , cascade = CascadeType.REMOVE)
    //@OneToMany(targetEntity = LinkMan.class)
    /*@JoinColumn(name = "lkm_cust_id",
        referencedColumnName = "cust_id")*/
    private Set<LinkMan> linkmans = new HashSet<>(0);

    public Set<LinkMan> getLinkmans() {
        return linkmans;
    }

    public void setLinkmans(Set<LinkMan> linkmen) {
        this.linkmans = linkmen;
    }

    public Customer(String custName, String custAddress) {
        this.custName = custName;
        this.custAddress = custAddress;
    }

    public Customer() {
    }

    public Long getCustId() {
        return custId;
    }
    public void
    setCustId(Long custId) {
        this.custId = custId;
    }
    public String getCustName() {
        return custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public String getCustSource() {
        return custSource;
    }
    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }
    public String getCustIndustry() {
        return custIndustry;
    }
    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }
    public String getCustLevel() {
        return custLevel;
    }
    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }
    public String getCustAddress() {
        return custAddress;
    }
    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }
    public String getCustPhone() {
        return custPhone;
    }
    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custPhone='" + custPhone + '\'' +
                '}';
    }
}
