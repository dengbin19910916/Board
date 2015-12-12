package com.wrox.entities;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author dengb
 */
public class Person {

    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "年龄不能为空")
    private int age;
    @NotNull(message = "性别不能为空")
    private String gender;
    @Future(message = "时间不能小于今天")
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

//    public String getBirthday() {
//        return new SimpleDateFormat("yyyy-MM-dd").format(birthday);
//    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    private class Age {
        private int birthday;

        public Age() {
            super();
        }

        public Age(int birthday) {
            this.birthday = birthday;
        }

        public int getBirthday() {
            return birthday;
        }

        public void setBirthday(int birthday) {
            this.birthday = birthday;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
