package org.example;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "ListOfStudents")
class StudentList {
    private ArrayList<Students> studentList;

    @XmlElement(name = "Student")
    public ArrayList<Students> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<Students> studentList) {
        this.studentList = studentList;
    }
}

public class Students {

    private String name;

    private String studentID;

    private String email;

    private String dateOfBirth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Student [" +
                "Name = '" + name + '\'' +
                ", Student ID = '" + studentID + '\'' +
                ", E-Mail = '" + email + '\'' +
                ", Date of Birth = '" + dateOfBirth + '\'' +
                ']';
    }
}
