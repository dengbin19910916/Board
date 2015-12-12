package com.wrox.entities;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;

/**
 * @author dengb
 */
public class User {
    private long id;

    @Length(min = 2, max = 50, message = "User name length range = 2-50")
    private String name;

    @Future(message = "时间不能小于今天")
    private Date createTime;

//    @NotEmpty(message = "Customer不能为空")
//    private List<Customer> customers;

    @NotNull(message = "Girl不能为空")
    private boolean girl;

    private String[] cbx;

    @NotNull(message = "Age can NOT be Null")
    @Min(value = 18, message = "最小18岁")
    @Max(value = 100, message = "最大100岁")
    private int age;

    @Email(message = "Email格式不正确")
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

//    public List<Customer> getCustomers() {
//        return customers;
//    }

//    public void setCustomers(List<Customer> customers) {
//        this.customers = customers;
//    }

    public boolean isGirl() {
        return girl;
    }

    public void setGirl(boolean girl) {
        this.girl = girl;
    }

    public String[] getCbx() {
        return cbx;
    }

    public void setCbx(String[] cbx) {
        this.cbx = cbx;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", girl=" + girl +
                ", cbx=" + Arrays.toString(cbx) +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
