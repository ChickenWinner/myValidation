package com.imp.controller;

import com.imp.annotations.IsEmail;
import com.imp.annotations.NeedVerify;
import com.imp.annotations.NotEmpty;
import com.imp.annotations.ObjVerify;
import com.imp.dto.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/2/27 20:29
 */

@RestController
public class TestController {

    @NeedVerify
    @RequestMapping("hello.do")
    public String test(@NotEmpty(msg = "i不能为空") Integer i) {
        // 测试
        // System.out.println(person);
        return "hello";
    }
}
