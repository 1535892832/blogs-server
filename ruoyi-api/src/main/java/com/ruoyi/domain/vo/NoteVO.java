package com.ruoyi.domain.vo;

/**
 * @author hubi
 * @date 2021/8/10
 */
public class NoteVO {

    private String title;

    private String description;

    private String coverPicture;

    private Long pageViewNum;

    private Long collectNum;

    private String content;

    private String userName;

    private String type;

    private Integer isOfficialRecommend;

    private Long belittleNum;

    private Long likeNum;

    public Long getBelittleNum() {
        return belittleNum;
    }

    public void setBelittleNum(Long belittleNum) {
        this.belittleNum = belittleNum;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Long likeNum) {
        this.likeNum = likeNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public Long getPageViewNum() {
        return pageViewNum;
    }

    public void setPageViewNum(Long pageViewNum) {
        this.pageViewNum = pageViewNum;
    }

    public Long getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Long collectNum) {
        this.collectNum = collectNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsOfficialRecommend() {
        return isOfficialRecommend;
    }

    public void setIsOfficialRecommend(Integer isOfficialRecommend) {
        this.isOfficialRecommend = isOfficialRecommend;
    }
}
