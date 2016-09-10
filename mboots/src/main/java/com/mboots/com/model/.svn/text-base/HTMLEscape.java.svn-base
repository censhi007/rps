package com.mboots.com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * 将所有HTML相关的标签都进行转义
 * @author 卜军
 *
 */
public class HTMLEscape extends HttpServletRequestWrapper{
	private HttpServletRequest request;
	public HTMLEscape(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
    @Override
    public String[] getParameterValues(String name){
    	String[] values = this.request.getParameterValues(name);
    	if(values==null)return null;
    	for(int i=0;i<values.length;i++){
    		values[i]=filter(values[i]);
    	}
    	return values;
    }
   
    public String filter(String message) {
        if (message == null){
            return null;
        }
        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuffer result = new StringBuffer(content.length + 50);
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
            case '<':
                result.append("&lt;");
                break;
            case '>':
                result.append("&gt;");
                break;
            case '&':
                result.append("&amp;");
                break;
            case '"':
                result.append("&quot;");
                break;
            default:
                result.append(content[i]);
            }
        }
        return result.toString();
    }
}
