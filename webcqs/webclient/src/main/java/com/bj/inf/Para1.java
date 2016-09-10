package com.bj.inf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "in0" })
@XmlRootElement(name = "fetchData")
public class Para1 implements PI{
	
	private static final long serialVersionUID = -6464274742296397585L;
	@XmlElement(required = true, nillable = true)
	protected String in0;

	public String getIn0() {
		return in0;
	}
	public void setIn0(String in0) {
		this.in0 = in0;
	}
	
}
