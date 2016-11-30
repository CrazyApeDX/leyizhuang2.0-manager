package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 用户评论
 * 
 * @author Sharon
 *
 */

@Entity
public class TdUserComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 评论标题
    @Column
    private String title;
    
    // 评论内容
    @Column
    private String content;
    
    // 评论标签
    @Column
    private String tags;
    
    // 评论星级
    @Column
    private Long stars;
    
    // 商品星级
    @Column
    private Long goodsStar;
    
    // 专业技能星级
    @Column
    private Long skillStar;
    
    // 服务星级
    @Column
    private Long serviceStar;
    
    // 评论被点击“有用”的数量
    @Column
    private Long positiveNumber;
    
    // 评论被点击“无用”的数量
    @Column
    private Long negativeNumber;
    
    // 评论时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date commentTime;
    
    // 是否已回复
    @Column
    private Boolean isReplied;
    
    // 评论回复
    @Column
    private String reply;
    
    // 回复时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date replyTime;
    
    // 评论的商品ID
    @Column
    private Long goodsId;
    
    // 评论的商品标题
    @Column
    private String goodsTitle;
    
    // 评论的商品封面图
    @Column
    private String goodsCoverImageUri;
    
    // 订单编号
    @Column
    private String orderNumber;
    
    // 发表评论的用户
    @Column
    private String username;
    
    // 用户头像
    @Column
    private String userHeadUri;
    
    // 显示状态
    @Column
    private Long statusId;
    
    // 排序号
    @Column
    private Double sortId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoodsCoverImageUri() {
        return goodsCoverImageUri;
    }

    public void setGoodsCoverImageUri(String goodsCoverImageUri) {
        this.goodsCoverImageUri = goodsCoverImageUri;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getStars() {
        return stars;
    }

    public void setStars(Long stars) {
        this.stars = stars;
    }

    public Long getPositiveNumber() {
        return positiveNumber;
    }

    public void setPositiveNumber(Long positiveNumber) {
        this.positiveNumber = positiveNumber;
    }

    public Long getNegativeNumber() {
        return negativeNumber;
    }

    public void setNegativeNumber(Long negativeNumber) {
        this.negativeNumber = negativeNumber;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    public Boolean getIsReplied() {
        return isReplied;
    }

    public void setIsReplied(Boolean isReplied) {
        this.isReplied = isReplied;
    }

    public String getUserHeadUri() {
        return userHeadUri;
    }

    public void setUserHeadUri(String userHeadUri) {
        this.userHeadUri = userHeadUri;
    }

    public Long getGoodsStar() {
        return goodsStar;
    }

    public void setGoodsStar(Long goodsStar) {
        this.goodsStar = goodsStar;
    }

    public Long getSkillStar() {
        return skillStar;
    }

    public void setSkillStar(Long skillStar) {
        this.skillStar = skillStar;
    }

    public Long getServiceStar() {
        return serviceStar;
    }

    public void setServiceStar(Long serviceStar) {
        this.serviceStar = serviceStar;
    }
}
