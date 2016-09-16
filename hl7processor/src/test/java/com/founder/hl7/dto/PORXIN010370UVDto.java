package com.founder.hl7.dto;

import java.util.List;

import com.founder.hl7.annotation.ChildType;
import com.founder.hl7.annotation.XmlTarget;

public class PORXIN010370UVDto
{

    @XmlTarget("/subject/combinedMedicationRequest/subject/patient/addr/item[@use='WP']/part[@type='STA']/@value")
    private String wpAddrSta;

    @XmlTarget("/subject/combinedMedicationRequest/subject/patient/addr/item[@use='WP']/part[@type='CTY']/@value")
    private String wpAddrCty;

    @XmlTarget("/subject/combinedMedicationRequest/subject/patient/addr/item[@use='WP']/part[@type='CNT']/@value")
    private String wpAddrCnt;

    @XmlTarget("/subject/combinedMedicationRequest/subject/patient/addr/item[@use='WP']/part[@type='PRE']/@value")
    private String wpAddrPre;

    @XmlTarget("/subject/combinedMedicationRequest/subject/patient/addr/item[@use='WP']/part[@type='STB']/@value")
    private String wpAddrStb;

    @XmlTarget("/subject/combinedMedicationRequest/subject/patient/addr/item[@use='WP']/part[@type='ZIP']/@value")
    private String wpAddrZip;

    private String inPatientId;

    @XmlTarget("/subject/combinedMedicationRequest/subject/patient/id/item[@root='2.16.886.111.100000.100000.1']/@extension")
    private String outPatientId;

    @XmlTarget("/subject/combinedMedicationRequest/componentOf/encounter/pertinentInformation1[s:observationDx/code/@codeSystem='2.16.840.1.113883.2.23.11.5502.20']")
    @ChildType(Diagnosis.class)
    private List<Diagnosis> child;

    public String getWpAddrSta()
    {
        return wpAddrSta;
    }

    public void setWpAddrSta(String wpAddrSta)
    {
        this.wpAddrSta = wpAddrSta;
    }

    public String getWpAddrCty()
    {
        return wpAddrCty;
    }

    public void setWpAddrCty(String wpAddrCty)
    {
        this.wpAddrCty = wpAddrCty;
    }

    public String getWpAddrCnt()
    {
        return wpAddrCnt;
    }

    public void setWpAddrCnt(String wpAddrCnt)
    {
        this.wpAddrCnt = wpAddrCnt;
    }

    public String getWpAddrPre()
    {
        return wpAddrPre;
    }

    public void setWpAddrPre(String wpAddrPre)
    {
        this.wpAddrPre = wpAddrPre;
    }

    public String getWpAddrStb()
    {
        return wpAddrStb;
    }

    public void setWpAddrStb(String wpAddrStb)
    {
        this.wpAddrStb = wpAddrStb;
    }

    public String getWpAddrZip()
    {
        return wpAddrZip;
    }

    public void setWpAddrZip(String wpAddrZip)
    {
        this.wpAddrZip = wpAddrZip;
    }

    public String getInPatientId()
    {
        return inPatientId;
    }

    public void setInPatientId(String inPatientId)
    {
        this.inPatientId = inPatientId;
    }

    public String getOutPatientId()
    {
        return outPatientId;
    }

    public void setOutPatientId(String outPatientId)
    {
        this.outPatientId = outPatientId;
    }

    public List<Diagnosis> getChild()
    {
        return child;
    }

    public void setChild(List<Diagnosis> child)
    {
        this.child = child;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("地址：");
        sb.append(this.wpAddrSta);
        sb.append(this.wpAddrCty);
        sb.append(this.wpAddrCnt);
        sb.append(this.wpAddrPre);
        sb.append(this.wpAddrStb);
        sb.append("\r\n邮政编码：");
        sb.append(this.wpAddrZip);
        sb.append("\r\n患者门诊号：");
        sb.append(this.outPatientId);
        sb.append("\r\n患者住院号：");
        sb.append(this.inPatientId);
        sb.append("\r\n诊断信息：");
        int max = (this.child == null ? -1 : this.child.size());
        for (int i = 0; i < max; i++)
        {
            Diagnosis d = this.child.get(i);
            sb.append("\r\n");
            sb.append(d.toString());
        }
        return sb.toString();
    }
}
