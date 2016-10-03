package com.yly.cdr.hl7.dto;


public class MorphologyImageContent extends BaseDto
{
    /**
     * 影像二进制信息
     */
    private String imageText;
    /**
     * 影像后缀名
     */
    private String imageExtension;
    /**
     * 提示信息
     */
    private String prompt;

    public String getImageText()
    {
        return imageText;
    }

    public String getImageExtension()
    {
        return imageExtension;
    }

    public String getPrompt()
    {
        return prompt;
    }
}
