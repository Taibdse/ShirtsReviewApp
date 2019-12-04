/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.common;

import java.util.HashMap;

/**
 *
 * @author HOME
 */
public class MyErrors {
    HashMap<String, String> errors = new HashMap<>();

    public MyErrors() {
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
    
    public void addErrors(String key, String value){
        errors.put(key, value);
    }
    
    public boolean isEmpty(){
        return errors.isEmpty();
    }
}
