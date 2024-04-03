package com.example.blood_bank_2022.Model;

public class Notification {

    String receierId , senderId ,text ,date ;

    public Notification() {
    }

    public Notification(String receierId, String senderId, String text, String date) {
        this.receierId = receierId;
        this.senderId = senderId;
        this.text = text;
        this.date = date;
    }

    public String getReceierId() {
        return receierId;
    }

    public void setReceierId(String receierId) {
        this.receierId = receierId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
