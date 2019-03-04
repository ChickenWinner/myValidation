package com.imp.dto;

import com.imp.annotations.IsEmail;
import com.imp.annotations.NotEmpty;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/4 14:34
 */
public class Person {

    @NotEmpty(value = "123")
    @IsEmail
    private String email;

    private String tt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", tt='" + tt + '\'' +
                '}';
    }
}
