package com;  
  
import java.io.Serializable;  
  
public class Message implements Serializable {  
    private static final long serialVersionUID = 1L;  
    public String name;   
    public String type;

    public String getName() {  
        return name;  
    }
    public String getType(){
        return type;
    }  
}  