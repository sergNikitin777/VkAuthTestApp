package com.example.svnikitin.vkauthtestapp;

/**
 * Created by svnikitin on 07.02.2018.
 */
public class Friend {

    private int id;
    private String firstName;
    private String lastName;
    private String imageLink;

    public Friend(){
    }
    public Friend(String first_name, String lastName, String imageLink) {
        this.firstName = first_name;
        this.lastName = lastName;
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
