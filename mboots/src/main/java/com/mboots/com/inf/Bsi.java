package com.mboots.com.inf;

/**
 * 所有的模型对象都序列化
 * @author BUJUN
 *
 */
public interface Bsi extends java.io.Serializable{
	/**
	 * 返回每一个对象的唯一标识。如果是复合主键，自动创建一个uuid
	 * @return
	 */
	String getId();//每
}