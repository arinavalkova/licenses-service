package ru.shift.baldezh.licenses.service.model.forms.license;

public class NewLicenseForm {
    private int userId;

    private String mail;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
