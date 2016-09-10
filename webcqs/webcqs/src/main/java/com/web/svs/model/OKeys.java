package com.web.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.webcqs.svs.inf.PI;
/**
 * original keys
 * @author BUJUn
 *
 */
public class OKeys extends PI{

	private String id;
	private String keys;
	private static final long serialVersionUID = 7200290709355360159L;

	@Override
	public void fromJSON(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadFrom(InputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeTo(OutputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

}
