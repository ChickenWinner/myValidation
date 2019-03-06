package com.imp.bean;

import com.imp.annotations.IsEmail;
import com.imp.annotations.Length;
import com.imp.annotations.Max;
import com.imp.annotations.NotEmpty;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/5 14:52
 */
public class Student {

    private String name;

    private String email;

    @Max(value = 12, msg = "age最大为12")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
