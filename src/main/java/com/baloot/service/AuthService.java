package com.baloot.service;

import com.baloot.exception.*;
import com.baloot.info.LoginInfo;
import com.baloot.info.RegisterInfo;
import com.baloot.model.*;

public class AuthService {
    public static void authenticateUser(LoginInfo login) throws Exception {
        if (login.getUsername() == null)
            throw new InValidInputException("Username field cannot be empty");
        BalootSystem baloot = BalootSystem.getInstance();
        String name = login.getUsername();
        String password = login.getPassword();
        if (baloot.isUserValid(name))
            baloot.loginInUser(name, password);
        System.out.println(baloot.getLoggedInUser().getUsername() + " logged in");
    }

    public static void logoutUser() throws Exception {
        BalootSystem.getInstance().logOutUser();
    }
    public static void registerUser(RegisterInfo signUpData) throws Exception {
        User user = new User(signUpData.getUsername(), signUpData.getPassword(), signUpData.getEmail(), signUpData.getBirthDate(), signUpData.getAddress(), 0);
        BalootSystem.getInstance().getDataBase().addUser(user);
    }


}
