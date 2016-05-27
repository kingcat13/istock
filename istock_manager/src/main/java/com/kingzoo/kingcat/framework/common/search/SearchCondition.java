package com.kingzoo.kingcat.framework.common.search;


/**
 */
public class SearchCondition { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString
	
	protected String property;
	
	protected String compare;
	
	protected String value;

	/**
	
	 * @return the property */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	
	 * @return the compareMethod */
	public String getCompare() {
		return compare;
	}

	/**
	
	 * @param compare String
	 */
	public void setCompare(String compare) {
		this.compare = compare;
	}

	/**
	
	 * @return the value */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		
		this.value = value;
		
	}
	
	
	
}
