package com.example.store.validation;

public abstract class AbstractUserValidator {

    protected String addMessageIfUsernameIsEmpty(String username, String response){
        if(username == null || username.equals("")){
            response += "Username is empty ";
        }
        return response;
    }

    protected String addMessageIfPasswordIsEmpty(String password, String response){
        if(password == null || password.equals("")){
            response += "Password is empty ";
        }
        return response;
    }

    protected String addMessageIfPasswordsMismatch(String password, String passwordRepeat, String response){
        if(!password.equals(passwordRepeat)){
            response += "Passwords mismatch ";
        }
        return response;
    }

}
