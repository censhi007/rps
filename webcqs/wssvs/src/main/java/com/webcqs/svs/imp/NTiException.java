package com.webcqs.svs.imp;

public class NTiException extends Exception{
	private static final long serialVersionUID = -6823493815610103412L;
	public NTiException(Exception e){
		super("需要Ti或者Ti的子类",e);
	}
	public NTiException(String msg,Exception e){
		super(msg,e);
	}
}
