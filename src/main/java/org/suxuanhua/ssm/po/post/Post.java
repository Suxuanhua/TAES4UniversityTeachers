package org.suxuanhua.ssm.po.post;

public class Post {
    private String postID;

    private String uName;

    private String uID;

    private String uSex;

    private String uHeader_default;

    private String postTitle;

    private String postContentAddr;

    private String postDate;

    private Long postReplies;

    private Long postVisits;

    public Post() {
        super();
    }

    public Post(String postID, String uName, String uID, String uSex,
                String uHeader_default, String postTitle, String postContentAddr, String postDate, Long postReplies, Long postVisits) {
        this.postID = postID;
        this.uName = uName;
        this.uID = uID;
        this.uSex = uSex;
        this.uHeader_default = uHeader_default;
        this.postTitle = postTitle;
        this.postContentAddr = postContentAddr;
        this.postDate = postDate;
        this.postReplies = postReplies;
        this.postVisits = postVisits;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public String getuHeader_default() {
        return uHeader_default;
    }

    public void setuHeader_default(String uHeader_default) {
        this.uHeader_default = uHeader_default;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContentAddr() {
        return postContentAddr;
    }

    public void setPostContentAddr(String postContentAddr) {
        this.postContentAddr = postContentAddr;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Long getPostReplies() {
        return postReplies;
    }

    public void setPostReplies(Long postReplies) {
        this.postReplies = postReplies;
    }

    public Long getPostVisits() {
        return postVisits;
    }

    public void setPostVisits(Long postVisits) {
        this.postVisits = postVisits;
    }
}