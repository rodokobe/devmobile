package com.projeto.academicplanner.model;

public class AdminPeople {

    private String idCourse;
    private String idAdminPerson;
    private String adminPersonFirstName;
    private String adminPersonLastName;
    private String adminPersonEmail;
    private String adminPersonCourse;

    public AdminPeople() {

    }

    public AdminPeople(String idCourseP, String idAdminPersonP,
                       String adminPersonFirstNameP, String adminPersonLastNameP,
                       String adminPersonEmailP, String adminPersonCourseP){

        this.idCourse = idCourseP;
        this.idAdminPerson = idAdminPersonP;
        this.adminPersonFirstName = adminPersonFirstNameP;
        this.adminPersonLastName = adminPersonLastNameP;
        this.adminPersonEmail = adminPersonEmailP;
        this.adminPersonCourse = adminPersonCourseP;

    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getIdAdminPerson() {
        return idAdminPerson;
    }

    public void setIdAdminPerson(String idAdminPerson) {
        this.idAdminPerson = idAdminPerson;
    }

    public String getAdminPersonFirstName() {
        return adminPersonFirstName;
    }

    public void setAdminPersonFirstName(String adminPersonFirstName) {
        this.adminPersonFirstName = adminPersonFirstName;
    }

    public String getAdminPersonLastName() {
        return adminPersonLastName;
    }

    public void setAdminPersonLastName(String adminPersonLastName) {
        this.adminPersonLastName = adminPersonLastName;
    }

    public String getAdminPersonEmail() {
        return adminPersonEmail;
    }

    public void setAdminPersonEmail(String adminPersonEmail) {
        this.adminPersonEmail = adminPersonEmail;
    }

    public String getAdminPersonCourse() {
        return adminPersonCourse;
    }

    public void setAdminPersonCourse(String adminPersonCourse) {
        this.adminPersonCourse = adminPersonCourse;
    }
}
