# myValidation

#### 介绍
参数检验器v1.0
目前实现的注解有：
1. 验证邮箱格式
2. 限制数字的最大最小值
3. 限制字符串的最大长度
4. 不允许字段为空
5. 后续将会更新更多的检验注解

![实现的注解](https://images.gitee.com/uploads/images/2019/0320/092010_970d2da4_2065443.png "屏幕截图.png")

#### 用到了哪些技术
1. springboot + maven作为项目的搭建环境
2. Java注解，利用注解来标明参数需要满足的条件
3. Java反射，使用反射动态获取参数值
4. spring AOP，利用AOP对所有待检验方法进行检验

#### 使用说明
1. 在所需要检验参数的controller方法上加上@NeedVerify注解
2. 在所需要检验的参数前加上注解 如@NotEmpty注解, 限制字段不能为空 
![输入图片说明](https://images.gitee.com/uploads/images/2019/0319/233047_63a05d58_2065443.png)

3. 传入空参数，检验不通过，返回检验信息；参数不为空，检验通过，正常执行代码逻辑
![检验不通过](https://images.gitee.com/uploads/images/2019/0319/233328_36a42c94_2065443.png)

3. 参数支持是对象类型，如果是对象，在对象的属性上加注解即可
![给对象加上注解](https://images.gitee.com/uploads/images/2019/0320/091924_8d60302b_2065443.png "屏幕截图.png")
