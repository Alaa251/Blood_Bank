package com.example.blood_bank_2022.Model;

public class User {
    String name,bloodGroup,email,id,idnumber,phoneNumber,profillepictureur1,search,type;

    public User() {
    }

    public User(String name, String bloodGroup, String email, String id, String idnumber, String phoneNumber, String profillepictureur1, String search, String type) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.email = email;
        this.id = id;
        this.idnumber = idnumber;
        this.phoneNumber = phoneNumber;
        this.profillepictureur1 = profillepictureur1;
        this.search = search;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfillepictureur1() {
        return profillepictureur1;
    }

    public void setProfillepictureur1(String profillepictureur1) {
        this.profillepictureur1 = profillepictureur1;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
