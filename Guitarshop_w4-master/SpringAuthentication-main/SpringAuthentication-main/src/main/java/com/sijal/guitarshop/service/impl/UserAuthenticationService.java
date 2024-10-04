package com.sijal.guitarshop.service.impl;

import com.sijal.guitarshop.entity.UserEnt;

public interface UserAuthenticationService {
    public boolean checkAuthDetails(UserEnt user);
    void saveUser(UserEnt user);
}
