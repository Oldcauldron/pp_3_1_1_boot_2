package jm.homework.pp_3_1_1_boot_2.service;


import jm.homework.pp_3_1_1_boot_2.model.User;

public interface SecurityService {
    void autoLogin(User user);
    public User getAuthUserName();

}
