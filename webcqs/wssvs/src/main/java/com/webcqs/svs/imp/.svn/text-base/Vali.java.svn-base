package com.webcqs.svs.imp;

import com.webcqs.common.ComFactory;
import com.webcqs.svs.inf.CIp;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.Ti;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;
/***
 * 对权限的验证
 * @author BUJUN
 *
 */
public class Vali implements Ti{

	@Override
	public IPMO fetch(IParam param) {
		CIp cip = ComFactory.getCip();
		boolean ok = cip.CKIP(param.getIp());
		if(ok) return null;//说明验证通过了
		MPO ipo = MPO.copyFrom(param);
		ipo.setDescription("@vali_failed");
		return ipo;
	}

}
