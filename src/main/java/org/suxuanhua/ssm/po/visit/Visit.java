package org.suxuanhua.ssm.po.visit;

import java.util.Date;

/**
 * 访问类
 *
 * @author XuanhuaSu
 * @version 2018/5/21
 */
public class Visit {
    private String vid;
    private Integer comeToVisitId;
    private String requestMethod;
    private String requestURI;
    private String comeToVisitIp;
    private Date requestTime;
    private String requestUserAgent;
    private String requestAccept;
    private String acceptLanguage;
    private String acceptCharset;
    private Integer requestNumber;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public Integer getComeToVisitId() {
        return comeToVisitId;
    }

    public void setComeToVisitId(Integer comeToVisitId) {
        this.comeToVisitId = comeToVisitId;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getComeToVisitIp() {
        return comeToVisitIp;
    }

    public void setComeToVisitIp(String comeToVisitIp) {
        this.comeToVisitIp = comeToVisitIp;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestUserAgent() {
        return requestUserAgent;
    }

    public void setRequestUserAgent(String requestUserAgent) {
        this.requestUserAgent = requestUserAgent;
    }

    public String getRequestAccept() {
        return requestAccept;
    }

    public void setRequestAccept(String requestAccept) {
        this.requestAccept = requestAccept;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getAcceptCharset() {
        return acceptCharset;
    }

    public void setAcceptCharset(String acceptCharset) {
        this.acceptCharset = acceptCharset;
    }

    public Integer getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(Integer requestNumber) {
        this.requestNumber = requestNumber;
    }
}
