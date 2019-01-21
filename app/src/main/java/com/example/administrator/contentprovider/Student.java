package com.example.administrator.contentprovider;

public class Student {
    private String name;
    private int age;
    private String introduce;

    public Student(String name,int age,String introduce){
        super();
        this.name = name;
        this.age = age;
        this.introduce = introduce;
    }

    public String getName(){
        return name;
    }
    public void setName(){
        this.name = name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(){
        this.age = age;
    }
    public String getIntroduce(){
        return introduce;
    }
    public void setIntroduce(){
        this.introduce = introduce;
    }
}
