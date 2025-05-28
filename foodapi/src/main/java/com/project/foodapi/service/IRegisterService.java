package com.project.foodapi.service;

import com.project.foodapi.entity.RegisterEntity;

public interface IRegisterService
{
    public RegisterEntity userRegister(RegisterEntity entity);

    String findByUserId();
}
