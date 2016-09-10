package com.webcqs.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.webcqs.svs.inf.Cache;
import com.webcqs.svs.inf.WI;

/**
 * 原数据<br/>
 * 实现普通的object
 * @author BUJUN
 *
 */
public class TMO extends WI implements Cache<String,Object>{	
	private static final long serialVersionUID = 8356301399501436018L;
	private Map<String,Object> map =new HashMap<String,Object>();
	@Override
	public void clear() {
		map.clear();		
	}

	@Override
	public Object get(String key) {		
		return map.get(key);
	}

	@Override
	public void put(String key, Object value) {
		map.put(key, value);
	}

	@Override
	public void remove(String arg0) {		
		map.remove(arg0);
	}

	@Override
	public void fromJSON(String arg0) {
		
	}

	@Override
	public void loadFrom(InputStream arg0) throws IOException {
	}

	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer("{");
		Set<String> ks = map.keySet();
		for(String k : ks){
			append(sb,k,map.get(k));
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public void writeTo(OutputStream arg0) throws IOException {
		
	}
	
}
