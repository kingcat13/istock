package com.kingzoo.kingcat.framework.common.search;

/**
 */
public class SortCondition { 
	private String property;
	private SortDirection direction;
	
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
	
	 * @return the direction */
	public SortDirection getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(SortDirection direction) {
		this.direction = direction;
	}
	public void setDirectionASC() {
		this.direction = SortDirection.ASC;
	}
	public void setDirectionDESC() {
		this.direction = SortDirection.DESC;
	}

	public static enum SortDirection {
		ASC,DESC
	}
}

