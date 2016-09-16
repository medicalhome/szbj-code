package com.founder.cdr.hl7.dto.pocdhd000040uv01;

public class ReviewPersonDto
{
    /**
     * 文档审核者ID
     */
    private String reviewPerson;

    /**
     * 文档审核者名称
     */
    private String reviewPersonName;

    /**
     * 文档审核时间
     */
    private String reviewTime;

    public String getReviewPerson()
    {
        return reviewPerson;
    }

    public String getReviewPersonName()
    {
        return reviewPersonName;
    }

    public String getReviewTime()
    {
        return reviewTime;
    }

    // $Author :chang_xuewen
    // $Date : 2013/06/05 16:00$
    // [BUG]0033373 MODIFY BEGIN
	@Override
	public String toString() {
		return "ReviewPersonDto [reviewPerson=" + reviewPerson
				+ ", reviewPersonName=" + reviewPersonName + ", reviewTime="
				+ reviewTime + "]";
	}
	// [BUG]0033373 MODIFY END
}
