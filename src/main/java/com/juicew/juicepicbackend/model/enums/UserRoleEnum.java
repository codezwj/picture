package com.juicew.juicepicbackend.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

@Getter
public enum UserRoleEnum {

    USER("用户","user"),
    ADMIN("管理员","admin");

    public final String text;

    public final String value;

    UserRoleEnum(String test, String value){
        this.text = test;
        this.value = value;
    }

    /**
     * 根据value获得枚举值
     *
     * @param value
     * @return
     */
    public static UserRoleEnum getEnumByValue(String value){
        if(ObjUtil.isEmpty(value)){
            return null;
        }
       for(UserRoleEnum userRoleEnum : UserRoleEnum.values()){
           if(userRoleEnum.value.equals(value)){
               return userRoleEnum;
           }
       }
       return null;
    }

}
