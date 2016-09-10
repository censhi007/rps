package com.webcqs.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.webcqs.svs.inf.PI;

public class Wto extends PI{
	private static final long serialVersionUID = -6273370216106449928L;
	private StringBuffer sb = new StringBuffer();
	public void put(String k,Object v){
		append(sb,k,v);
	}
	@Override
	public void fromJSON(String arg0) {
		
	}

	@Override
	public void loadFrom(InputStream arg0) throws IOException {
		
	}

	@Override
	public String toJSON() {
		return "{"+sb+"}";		
	}

	@Override
	public void writeTo(OutputStream arg0) throws IOException {
		
	}

}
