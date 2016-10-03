package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class LabImageContent extends BaseDto
{
    /**
     * 影像二进制信息
     */
 //   @NotEmpty(message = "{LabImageContent.imageText}")
    private String imageText;
    /**
     * 提示信息
     */
    private String prompt;
    /**
     * 影像后缀名
     */
 //  @NotEmpty(message = "{LabImageContent.imageExtension}")
    private String imageExtension;

    public String getImageText()
    {
        return imageText;
    }

    public String getPrompt()
    {
        return prompt;
    }

	public String getImageExtension() {
		return imageExtension;
	}
    
    
}
