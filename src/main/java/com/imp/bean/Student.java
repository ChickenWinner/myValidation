package com.imp.bean;

import com.imp.annotations.IsEmail;
import com.imp.annotations.NotEmpty;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/5 14:52
 */
public class Student {

    @NotEmpty(msg = "名称不能为空")
    private String name;

    @IsEmail(msg = "邮箱格式不正确")
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
