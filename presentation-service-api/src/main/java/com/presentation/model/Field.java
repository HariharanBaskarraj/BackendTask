package com.presentation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * This class is curated for persistence of field properties.
 * 
 * @author HariharanB
 *
 */
@Entity
public class Field {
	@Id
	@GeneratedValue(generator = "field_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "field_gen", sequenceName = "field_gen", initialValue = 201, allocationSize = 1)
	private Integer fieldId;
	@Column(length = 25)
	private String fieldName;
	@Transient
//	@JsonIgnore
	private String value;

	/**
	 * 
	 */
	public Field() {
		super();
	}

	/**
	 * @param fieldName
	 */
	public Field(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	/**
	 * @param fieldName
	 * @param value
	 */
	public Field(String fieldName, String value) {
		super();
		this.fieldName = fieldName;
		this.value = value;
	}

	/**
	 * @return the field id
	 */
	public Integer getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId
	 */
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return "Field [fieldId=" + fieldId + ", fieldName=" + fieldName + "]";
	}

}