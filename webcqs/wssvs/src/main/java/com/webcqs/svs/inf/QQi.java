package com.webcqs.svs.inf;
/**
 * 用于提供Webservice接口
 * @author BUJUN
 *
 */
public interface QQi {
	/**
	 * 传入JSON字符串
	 * @param json
	 * @return
	 */
	public String fetchData(String json);
	/**
	 * 本方法返回的数据将通过Gzip压缩。<br/>
	 * 推荐使用本方法获取数据
	 * @param data 传入通过gzip压缩后的参数
	 * @return
	 */
	public byte[] fetchGzipData(byte[] data);
}
