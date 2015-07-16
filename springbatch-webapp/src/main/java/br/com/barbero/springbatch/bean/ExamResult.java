package br.com.barbero.springbatch.bean;
 
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.LocalDate;

import br.com.barbero.springbatch.util.LocalDateAdapter;
 
@XmlRootElement(name = "ExamResult")
public class ExamResult {
 
    private String studentName;
 
    private LocalDate dob;
 
    private double percentage;
 
    @XmlElement(name = "studentName")
    public String getStudentName() {
        return studentName;
    }
 
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
 
    @XmlElement(name = "dob")
    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
    public LocalDate getDob() {
        return dob;
    }
 
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
 
    @XmlElement(name = "percentage")
    public double getPercentage() {
        return percentage;
    }
 
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
 
    @Override
    public String toString() {
        return "ExamResult [studentName=" + studentName + ", dob=" + dob
                + ", percentage=" + percentage + "]";
    }
 
}