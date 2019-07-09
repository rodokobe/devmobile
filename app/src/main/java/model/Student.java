package model;

public class Student {


    private String idCourse;
    private String idDiscipline;
    private String idStudent;
    private String studentFirstName;
    private String studentLastName;
    private String studentEmail;
    private String studentDelegate;

    public Student() {

    }

    public Student(String idCourseP, String idDisciplineP,
                   String idStudentP, String studentFirstNameP,
                   String studentLastNameP, String studentEmailP,
                   String studentDelegateP) {

        this.idCourse = idCourseP;
        this.idDiscipline = idDisciplineP;
        this.idStudent = idStudentP;
        this.studentFirstName = studentFirstNameP;
        this.studentLastName = studentLastNameP;
        this.studentEmail = studentEmailP;
        this.studentDelegate = studentDelegateP;

    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getIdDiscipline() {
        return idDiscipline;
    }

    public void setIdDiscipline(String idDiscipline) {
        this.idDiscipline = idDiscipline;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentDelegate() {
        return studentDelegate;
    }

    public void setStudentDelegate(String studentDelegate) {
        this.studentDelegate = studentDelegate;
    }
}