package com.imp.dto;

import com.imp.annotations.IsEmail;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/4 14:34
 */
public class Person {

    @IsEmail(keyName = "wh")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "email='" + email + '\'' +
                '}';
    }
}
