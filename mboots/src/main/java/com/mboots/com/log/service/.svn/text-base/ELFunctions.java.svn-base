package com.mboots.com.log.service;

/**
 * 自定义的EL函数
 * @author BUJUN
 *
 */
public class ELFunctions {
	/*private static Method $if;
	 *
	 * 用于进行if判断的函数。<br/>
	 * 格式如下：<br/>
	 * $if(boolean[,String][[,Boolean][,String]])<br/>
	 * 第一个参数是必须的boolean型。<br/>
	 * 当第一个参数为真时，返回第二个参数<br/>
	 * 当第一个参数为假时，判断第三个参数，<br/>
	 * 如果第三个参数是Boolean型，那么判断其是否为真，如真取第四个参数。假取第五个参数<nr/>
	 * 如果第三个参数是其他类型，调用toString方法后直接返回。<br/>
	 * 从第二个参数起。所有的参数都是可选的、<br/>
	 * eg:$if(true,1,2);返回"1"<br/>
	 * &nbsp;&nbsp;&nbsp;$if(false,"1","2");返回"2";<br/>
	 * &nbsp;&nbsp;&nbsp;$if(false,"1",false,"2",true,3,"4");返回"3";
	 * @param b
	 * @param objs
	 * @return
	 *
	public static String $if(Boolean b,Object...objs){
		int n = objs.length;
		if(n==0)return "";
		if(b)return objs[0]==null?"":objs[0].toString();
		if(n==1)return "";
		if(objs[1]==null || objs[1] instanceof String)return objs[1]==null?null:objs[1].toString();
		if(n==2)return objs[1].toString();//当只有两个可选结果时，直接返回//即使它是boolean型。
		if(objs[1] instanceof Boolean){
			Boolean nb = (Boolean)objs[1];
			if($if == null){
				try {
					$if = ELFunctions.class.getMethod("$if",Boolean.class,Object[].class);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
			if($if==null)return objs[1].toString();
			Object[] os=null;
			if(n==2)os=new Object[0];
			else os=Arrays.copyOfRange(objs, 2, n);
			objs=null;
			Object rs=null;
			try {
				rs = $if.invoke(null,nb,os);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return rs==null?null:rs.toString();
		}
		return objs[1].toString();
	}
	*/
	public static String $if(Boolean b,String o1,String o2){
		return b ? o1 :o2;
	}
}
