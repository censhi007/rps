package com.bj.inf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "out" })
@XmlRootElement(name = "fetchDataResponse")
public class Return1 implements PI{	
	private static final long serialVersionUID = -1988400206428715241L;
	@XmlElement(required = true, nillable = true)
	protected String out;

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
	
}
