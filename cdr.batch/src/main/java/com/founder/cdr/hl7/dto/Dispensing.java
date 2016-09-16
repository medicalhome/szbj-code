package com.founder.cdr.hl7.dto;


public class Dispensing extends BaseDto
{
    /**
     * 请领人编码
     */
    private String receivePersonId;
    /**
     * 请领人姓名
     */
    private String receivePersonName;
    /**
     * 发药科室编码
     */
    private String execDeptId;
    /**
     * 发药科室名称
     */
    private String execDeptName;
    /**
     * 请领科室编码
     */
    private String receiveDeptId;
    /**
     * 请领科室名称
     */
    private String receiveDeptName;

    public String getReceivePersonId()
    {
        return receivePersonId;
    }

    public String getReceivePersonName()
    {
        return receivePersonName;
    }

    public String getExecDeptId()
    {
        return execDeptId;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public String getReceiveDeptId()
    {
        return receiveDeptId;
    }

    public String getReceiveDeptName()
    {
        return receiveDeptName;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Dispensing [receivePersonId=");
        builder.append(receivePersonId);
        builder.append(", execDeptId=");
        builder.append(execDeptId);
        builder.append(", receiveDeptId=");
        builder.append(receiveDeptId);
        builder.append("]");
        return builder.toString();
    }
    
    
}
