package org.suxuanhua.ssm.po.teacher;

import java.util.Date;

/**
 * @author XuanhuaSu
 * @version 2018/4/28
 */
public class Teacher {
    private Integer teacherID;

    private String teacherName;

    private Integer teacherSex;

    private String teacherTitle;//教师职称

    private String teacherPhoneNumber;

    private String teacherEMail;
    //总课时//总授课人数//开课门数 应该通过 查询的时候在通过查询对应的表进行统计返回，而不是记录在这里

    private String teacherNote;

    private Date teacherUpdateTime;

    private String teacherHeader_default;

    public Teacher() {
        super ();
    }

    public Teacher(Integer teacherID, String teacherName, Integer teacherSex, String teacherTitle,
                   String teacherPhoneNumber, String teacherEMail, String teacherNote,
                   Date teacherUpdateTime, String teacherHeader_default) {

        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.teacherSex = teacherSex;
        this.teacherTitle = teacherTitle;
        this.teacherPhoneNumber = teacherPhoneNumber;
        this.teacherEMail = teacherEMail;
        this.teacherNote = teacherNote;
        this.teacherUpdateTime = teacherUpdateTime;
        this.teacherHeader_default = teacherHeader_default;
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

    public String getTeacherTitle() {
        return teacherTitle;
    }

    public void setTeacherTitle(String teacherTitle) {
        this.teacherTitle = teacherTitle;
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

    public String getTeacherNote() {
        return teacherNote;
    }

    public void setTeacherNote(String teacherNote) {
        this.teacherNote = teacherNote;
    }

    public Date getTeacherUpdateTime() {
        return teacherUpdateTime;
    }

    public void setTeacherUpdateTime(Date teacherUpdateTime) {
        this.teacherUpdateTime = teacherUpdateTime;
    }

    public String getTeacherHeader_default() {
        return teacherHeader_default;
    }

    public void setTeacherHeader_default(String teacherHeader_default) {
        this.teacherHeader_default = teacherHeader_default;
    }
}
