package com.imp.bean;

import com.imp.annotations.IsEmail;
import com.imp.annotations.Length;
import com.imp.annotations.NotEmpty;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/5 14:52
 */
public class Student {

    @Length(max = 3, min = 1, msg = "name的长度必须大于1小于3")
    private String name;

    private String email;

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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
