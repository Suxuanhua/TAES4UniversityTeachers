package org.suxuanhua.ssm.po.admin;

import java.util.Date;

/**
 * @author XuanhuaSu
 * @version 2018/4/22
 */
public class Administrator {
    private Integer adminID;
    //管理员权限
    private String adminName;

    private String adminPermissions;

    private String adminPassword;

    private String adminPhoneNumber;

    private String adminEMail;

    private String adminNote;

    private Date adminLoginTime;

    private String adminLoginIp;

    private String adminHeader_default;

    public Administrator() {
        super ();
    }

    public Administrator(Integer adminID, String adminName, String adminPermissions, String adminPassword, String adminPhoneNumber, String adminEMail, String adminNote, Date adminLoginTime, String adminLoginIp, String adminHeader_default) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.adminPermissions = adminPermissions;
        this.adminPassword = adminPassword;
        this.adminPhoneNumber = adminPhoneNumber;
        this.adminEMail = adminEMail;
        this.adminNote = adminNote;
        this.adminLoginTime = adminLoginTime;
        this.adminLoginIp = adminLoginIp;
        this.adminHeader_default = adminHeader_default;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPermissions() {
        return adminPermissions;
    }

    public void setAdminPermissions(String adminPermissions) {
        this.adminPermissions = adminPermissions;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPhoneNumber() {
        return adminPhoneNumber;
    }

    public void setAdminPhoneNumber(String adminPhoneNumber) {
        this.adminPhoneNumber = adminPhoneNumber;
    }

    public String getAdminEMail() {
        return adminEMail;
    }

    public void setAdminEMail(String adminEMail) {
        this.adminEMail = adminEMail;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public Date getAdminLoginTime() {
        return adminLoginTime;
    }

    public void setAdminLoginTime(Date adminLoginTime) {
        this.adminLoginTime = adminLoginTime;
    }

    public String getAdminLoginIp() {
        return adminLoginIp;
    }

    public void setAdminLoginIp(String adminLoginIp) {
        this.adminLoginIp = adminLoginIp;
    }

    public String getAdminHeader_default() {
        return adminHeader_default;
    }

    public void setAdminHeader_default(String adminHeader_default) {
        this.adminHeader_default = adminHeader_default;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "adminID=" + adminID +
                ", adminName='" + adminName + '\'' +
                ", AdminPermissions='" + adminPermissions + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminPhoneNumber='" + adminPhoneNumber + '\'' +
                ", adminEMail='" + adminEMail + '\'' +
                ", adminNote='" + adminNote + '\'' +
                ", adminLoginTime=" + adminLoginTime +
                ", adminLoginIp='" + adminLoginIp + '\'' +
                ", adminHeader_default='" + adminHeader_default + '\'' +
                '}';
    }
}
