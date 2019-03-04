# myValidation

#### 介绍
参数检验器v1.0

#### 软件架构
基于springboot搭建
利用注解+反射+springAop实现

#### 使用说明

1. 在所需要检验参数的方法上加上@NeedVerify注解
2. 在所需要检验的参数前加上注解 如@IsEamil注解 
3. 可对对象中的属性进行检验 在所需检验的对象加上@ObjVerify注解（被检验对象所在包可在配置文件config/setting.properties中配置）

