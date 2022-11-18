package com.presentation.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class is curated for persistence of template properties.
 * 
 * @author HariharanB
 *
 */
@Entity
public class Template {
	@Id
	@GeneratedValue(generator = "template_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "template_gen", sequenceName = "template_seq", initialValue = 101, allocationSize = 1)
	private Integer templateId;
	@Column(length = 25)
	private String templateName;
	private String source;
	private String destination;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "temp_id")
	@JsonIgnore
	private List<Field> fields;

	/**
	 * 
	 */
	public Template() {
		super();
	}

	/**
	 * @param templateName
	 * @param fields
	 */
	public Template(String templateName, String source, String destination, List<Field> fields) {
		super();
		this.templateName = templateName;
		this.source = source;
		this.destination = destination;
		this.fields = fields;
	}

	/**
	 * @return the template id
	 */
	public Integer getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 */
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
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
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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
		return "Template [templateId=" + templateId + ", templateName=" + templateName + ", fields=" + fields + "]";
	}

}
