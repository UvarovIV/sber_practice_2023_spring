package ru.sber.models;

public class Client {

    private long uid;
    private String name;
    private String surname;
    private String patronymic;
    private String phone;

    public Client(long uid, String name, String surname, String patronymic, String phone) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String pathronymic) {
        this.patronymic = pathronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
