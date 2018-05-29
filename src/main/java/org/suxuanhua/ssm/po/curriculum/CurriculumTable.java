package org.suxuanhua.ssm.po.curriculum;

import java.util.Date;

/**
 * @author XuanhuaSu
 * @version 2018/4/28
 */
public class CurriculumTable {
    private Integer curriculumID;//课程ID

    private String curriculumName;//课程名称

    private Integer grade;//年级

    private String className;//班级

    private Integer teacherID;

    private String teacherName;

    private Integer teacherSex;

    private String teacherPhoneNumber;

    private String teacherEMail;

    private Integer classHours;//课时

    private Integer teachingNumber;//授课人数，班级人数

    private String curriculumNote;

    private Date curriculumUpdateTime;

    public CurriculumTable() {
        super ();
    }

    public CurriculumTable(Integer curriculumID, String curriculumName, Integer grade, String className,
                           Integer teacherID, String teacherName, Integer teacherSex,
                           String teacherPhoneNumber, String teacherEMail, Integer classHours,
                           Integer teachingNumber, String curriculumNote, Date curriculumUpdateTime) {

        this.curriculumID = curriculumID;
        this.curriculumName = curriculumName;
        this.grade = grade;
        this.className = className;
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.teacherSex = teacherSex;
        this.teacherPhoneNumber = teacherPhoneNumber;
        this.teacherEMail = teacherEMail;
        this.classHours = classHours;
        this.teachingNumber = teachingNumber;
        this.curriculumNote = curriculumNote;
        this.curriculumUpdateTime = curriculumUpdateTime;
    }

    public Integer getCurriculumID() {
        return curriculumID;
    }

    public void setCurriculumID(Integer curriculumID) {
        this.curriculumID = curriculumID;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Integer teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getTeacherSex() {
        return teacherSex;
    }

    public void setTeacherSex(Integer teacherSex) {
        this.teacherSex = teacherSex;
    }

    public String getTeacherPhoneNumber() {
        return teacherPhoneNumber;
    }

    public void setTeacherPhoneNumber(String teacherPhoneNumber) {
        this.teacherPhoneNumber = teacherPhoneNumber;
    }

    public String getTeacherEMail() {
        return teacherEMail;
    }

    public void setTeacherEMail(String teacherEMail) {
        this.teacherEMail = teacherEMail;
    }

    public Integer getClassHours() {
        return classHours;
    }

    public void setClassHours(Integer classHours) {
        this.classHours = classHours;
    }

    public Integer getTeachingNumber() {
        return teachingNumber;
    }

    public void setTeachingNumber(Integer teachingNumber) {
        this.teachingNumber = teachingNumber;
    }

    public String getCurriculumNote() {
        return curriculumNote;
    }

    public void setCurriculumNote(String curriculumNote) {
        this.curriculumNote = curriculumNote;
    }

    public Date getCurriculumUpdateTime() {
        return curriculumUpdateTime;
    }

    public void setCurriculumUpdateTime(Date curriculumUpdateTime) {
        this.curriculumUpdateTime = curriculumUpdateTime;
    }
}
