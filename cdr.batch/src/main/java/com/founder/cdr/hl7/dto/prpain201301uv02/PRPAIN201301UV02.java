package com.founder.cdr.hl7.dto.prpain201301uv02;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;

/**
 * 患者基本信息Dto
 * 
 * @author wen_ruichao
 */
public class PRPAIN201301UV02 extends BaseDto
{

    private String patientSn;

    @NotEmpty(message = "{PRPAIN201301UV02.patientName}")
    private String patientName;

    private String hAddrSta;

    private String hAddrCty;

    private String hAddrCnt;

    private String hAddrPre;

    private String hAddrStb;

    private String hAddrZip;

    public String getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(String patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String gethAddrSta()
    {
        return hAddrSta;
    }

    public void sethAddrSta(String hAddrSta)
    {
        this.hAddrSta = hAddrSta;
    }

    public String gethAddrCty()
    {
        return hAddrCty;
    }

    public void sethAddrCty(String hAddrCty)
    {
        this.hAddrCty = hAddrCty;
    }

    public String gethAddrCnt()
    {
        return hAddrCnt;
    }

    public void sethAddrCnt(String hAddrCnt)
    {
        this.hAddrCnt = hAddrCnt;
    }

    public String gethAddrPre()
    {
        return hAddrPre;
    }

    public void sethAddrPre(String hAddrPre)
    {
        this.hAddrPre = hAddrPre;
    }

    public String gethAddrStb()
    {
        return hAddrStb;
    }

    public void sethAddrStb(String hAddrStb)
    {
        this.hAddrStb = hAddrStb;
    }

    public String gethAddrZip()
    {
        return hAddrZip;
    }

    public void sethAddrZip(String hAddrZip)
    {
        this.hAddrZip = hAddrZip;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("{\n");
        sb.append("message content:").append(getMessage().getContent()).append(
                "\n");
        sb.append("patientName:").append(getPatientName()).append("\n");
        sb.append("hAddrSta:").append(gethAddrSta()).append("\n");
        sb.append("hAddrCty:").append(gethAddrCty()).append("\n");
        sb.append("hAddrCnt:").append(gethAddrCnt()).append("\n");
        sb.append("hAddrPre:").append(gethAddrPre()).append("\n");
        sb.append("hAddrStb:").append(gethAddrStb()).append("\n");
        sb.append("hAddrZip:").append(gethAddrZip()).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
