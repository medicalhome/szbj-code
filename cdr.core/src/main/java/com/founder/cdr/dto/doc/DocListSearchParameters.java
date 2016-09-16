package com.founder.cdr.dto.doc;

import java.util.List;

import com.founder.cdr.dto.CommonParameters;

public class DocListSearchParameters extends CommonParameters
{
    private String documentType;

    private String documentName;

    private String documentAuthor;

    private String submitTimeFrom;

    private String submitTimeTo;
    
    private String orgCode;

    private List<String> inDocumentTypes;
    
    private String  visitTimes;
    
    private List<String> portalDocumentTypes;
    
    private String portalViewId;

    public List<String> getInDocumentTypes()
    {
        return inDocumentTypes;
    }

    public void setInDocumentTypes(List<String> inDocumentTypes)
    {
        this.inDocumentTypes = inDocumentTypes;
    }

    public String getDocumentType()
    {
        return documentType;
    }

    public void setDocumentType(String documentType)
    {
        this.documentType = documentType;
    }

    public String getDocumentName()
    {
        return documentName;
    }

    public void setDocumentName(String documentName)
    {
        this.documentName = documentName;
    }

    public String getDocumentAuthor()
    {
        return documentAuthor;
    }

    public void setDocumentAuthor(String documentAuthor)
    {
        this.documentAuthor = documentAuthor;
    }

    public String getSubmitTimeFrom()
    {
        return submitTimeFrom;
    }

    public void setSubmitTimeFrom(String submitTimeFrom)
    {
        this.submitTimeFrom = submitTimeFrom;
    }

    public String getSubmitTimeTo()
    {
        return submitTimeTo;
    }

    public void setSubmitTimeTo(String submitTimeTo)
    {
        this.submitTimeTo = submitTimeTo;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

	public String getVisitTimes() {
		return visitTimes;
	}

	public void setVisitTimes(String visitTimes) {
		this.visitTimes = visitTimes;
	}

	public List<String> getPortalDocumentTypes() {
		return portalDocumentTypes;
	}

	public void setPortalDocumentTypes(List<String> portalDocumentTypes) {
		this.portalDocumentTypes = portalDocumentTypes;
	}

	public String getPortalViewId() {
		return portalViewId;
	}

	public void setPortalViewId(String portalViewId) {
		this.portalViewId = portalViewId;
	}

}
