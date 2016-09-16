package com.founder.cdr.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

@Entity
@Table(name = "USER_FEEDBACK")
public class UserFeedback implements Serializable
{
    private BigDecimal feedbackSn;

    private String userId;

    private String content;

    private String userName;

    private BigDecimal parentFeedbackSn;

    private BigDecimal replyCount;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_USER_FEEDBACK") })
    public BigDecimal getFeedbackSn()
    {
        return feedbackSn;
    }

    public void setFeedbackSn(BigDecimal feedbackSn)
    {
        this.feedbackSn = feedbackSn;
    }

    @Column(name = "USER_ID", nullable = false, length = 12)
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Column(name = "CONTENT")
    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Column(name = "USER_NAME", length = 56)
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Column(name = "PARENT_FEEDBACK_SN", precision = 22, scale = 0)
    public BigDecimal getParentFeedbackSn()
    {
        return parentFeedbackSn;
    }

    public void setParentFeedbackSn(BigDecimal parentFeedbackSn)
    {
        this.parentFeedbackSn = parentFeedbackSn;
    }

    @Column(name = "REPLY_COUNT", precision = 10, scale = 0)
    public BigDecimal getReplyCount()
    {
        return replyCount;
    }

    public void setReplyCount(BigDecimal replyCount)
    {
        this.replyCount = replyCount;
    }

}
