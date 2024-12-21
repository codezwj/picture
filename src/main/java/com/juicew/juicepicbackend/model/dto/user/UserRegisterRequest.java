package com.juicew.juicepicbackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册接口
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -8543310119793558524L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

}
