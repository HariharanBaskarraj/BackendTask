package com.presentation.model;

import java.util.List;

/**
 * This class is curated for the format of the incoming WebServiceRequest in the
 * JSON format.
 * 
 * @author HariharanB
 *
 */
public class PresentationRequest {

	private String templateName;
	private List<Field> fields;

	/**
	 * 
	 */
	public PresentationRequest() {
		super();
	}

	/**
	 * @param templateName
	 * @param fields
	 */
	public PresentationRequest(String templateName, List<Field> fields) {
		super();
		this.templateName = templateName;
		this.fields = fields;
	}

	/**
	 * @return the template name
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the list of field
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return "PresentationRequest [templateName=" + templateName + ", fields=" + fields + "]";
	}

}
