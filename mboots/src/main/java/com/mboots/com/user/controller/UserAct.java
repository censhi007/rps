package com.mboots.com.user.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mboots.com.log.inf.Lga;
import com.mboots.com.user.inf.Usersvi;
import com.mboots.com.user.model.User;
import com.mboots.com.util.BAct;
import com.mboots.com.util.Msg;
import com.mboots.com.util.encypt.PwdEncoder;
/**
 * 用户信息的控制层<br/>
 * Controller标价在这里仅仅用于日志切片。<br/>
 * 一般的情况下，该标记用于spring自动扫描。但是本项目提倡所有需要配置的控制层都在xml中进行配置。以便维护<br/>
 * @author BUJUN
 *
 */
@Scope("session")
@Controller
@RequestMapping(value="/userinfo")
public class UserAct extends BAct{
	
	private static final long serialVersionUID = -6954699217236114655L;
	private Log log;
	private final long _OTime_ = 60000;//如果验证码是60秒前生成的，那么提示超时
	private Ehcache cache;
	private static final String vc="_vcode";
	private static final String vctime="vctime";
	public static final String logged="logged";
	/**用于存储用户本锁定的状态*/
	private static final String lockuser="locked.user.";
	/**用于存储用户上一次密码输入错误的时间*/
	private static final String errtime="time.user.logerr.";
	/**用于存储用户输入错误的次数*/
	private static final String errcount="count.user.logerr.";
	/**当用户连续六次密码输入错误时，锁定用户一个小时*/
	private static final int errtimes = 6;
	/**锁定用户一个小时*/
	private static final long locktime=3600000;
	private static final long timespace=300000;//间隔不超过5分钟的两次登陆算连续登陆
	public static final String REBUILDPASS="rebuildpassword";//修改密码的js方法。向前台传递本函数，让他调用//如果前台打算自己处理也可以
	private static final int l=4;
	/**
	 * 前台的控制器，通过表单提交的登陆功能
	 * @param name
	 * @param pwd
	 * @param vcod
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validation", method = RequestMethod.POST)
	@ResponseBody
	@Lga(value="用户${p0}执行登陆，结果:${ret.svalue}")
	public Msg validate(String name,String pwd,String vcod,HttpServletRequest request){
		Msg m = new Msg();
		HttpSession session = request.getSession();
		String nam = attr(session,"logname");
		Object o = attr(session,logged);
		if(Boolean.TRUE.equals(o) && name.equals(nam)){
			//已经登陆了。
			m.setSvalue("@{login.twice}");
			return m;
		}
		Long now  = System.currentTimeMillis();
		String lck = lockuser+name;
		String elk = errtime+name;
		String eck=errcount+name;
		Integer locked = get(lck);		//是否被锁定了
		Long elst = get(elk);	//上一次密码错误的时间
		elst=elst==null?now:elst;
		long spt = now-elst;//两次登陆间的间隔
		if(new Integer(1).equals(locked)){
			//用户被锁定了。检查锁定时间
			long e = locktime-spt;
			if(e > 0){
				m.setSvalue("@{user.locked}"+parseL2S(e));
				return m;
			}
			put(eck,0);
			put(lck,0);
		}else{
			if(spt >= timespace){
				//间隔超过了限定时间，重新开始计算登陆次数
				put(eck,0);
			}
		}
		removeAttr(session,logged);
		removeAttr(session,"userid");//再次调用登陆前将
		attr(session,"ip",getIp(request));
		attr(session,"username",name);
		Long lst = attr(session,vctime);
		String _vc = attr(session,vc);
		if(lst==null || _vc==null || (now-lst) > _OTime_){
			//验证码过期
			m.setSvalue("@{vcode.outdate}");
			return m;
		}
		lst = null;
		if(vcod==null || !_vc.equals(md5.encodePassword(vcod.toLowerCase()))){
			m.setSvalue("@{vcode.err}");
			return m;
		}
		User u = svi.findUser(name, pwd);
		if(u==null){
			put(elk,now);
			Integer c = get(eck);
			c=c==null?0:c;
			c++;
			if(c == errtimes){
				m.setSvalue("@{user.locked}"+parseL2S(locktime));
				put(eck,0);
				put(lck,1);
				return m;
			}
			put(eck,c);
			m.setSvalue("@{login.err}"+errtimes+"@{login.err2}"+(errtimes-c));
			return m;
		}
		put(eck,0);//登陆成功，情况错误次数
		
		//登陆成功。将当前用户的id存放如session
		removeAttr(session,"_vcode");
		removeAttr(session,"vctime");
		attr(session,logged,true);
		attr(session,"userid",u.getId());
		attr(session,"username",u.getName());
		attr(session,"logintime",now);
		User tmp = new User();
		tmp.setId(u.getId());
		tmp.setName(u.getName());
		tmp.setLogname(u.getLogname());
		tmp.setInit(u.getInit());
		attr(session,"_cuser",tmp);
		m.setOvalue(tmp);
		u=null;
		m.setState(1);
		m.setSvalue("@{login.success}");
		return m;
	}

	
	private static char [] vcs={'0','1','2','3','4','5','6','7','8','9','A','B','C',
		'D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	@RequestMapping(value = "/vcode", method = RequestMethod.GET)
	public String GerVCode(String name,HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		BufferedImage img = new BufferedImage(68, 22,BufferedImage.TYPE_INT_RGB);
		// 得到该图片的绘图对象  
		Graphics g = img.getGraphics();
		Random r = new Random();
		Color c = new Color(200, 150, 255);
		g.setColor(c);
		// 填充整个图片的颜色  
		g.fillRect(0, 0, 68, 22);
		// 向图片中输出数字和字母  
		StringBuffer sb = new StringBuffer();
		char[] ch =vcs;
		int index, len = ch.length;
		for (int i = 0; i < l; i++) {
			index = r.nextInt(len);
			while(index == len){
				index=r.nextInt(len);
			}
			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
			g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
			int _y = r.nextInt(3)-1;//-1,0,1
			// 输出的  字体和大小 
			g.drawString("" + ch[index], (i * 15) + 3, 18+_y);
			//写什么数字，在图片 的什么位置画 
			sb.append(ch[index]);
		}
		String _vc=md5.encodePassword(sb.toString().toLowerCase());	
		attr(session,vc,_vc);
		attr(session,vctime,System.currentTimeMillis());
		try {
			ImageIO.write(img, "JPG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回当前的登陆用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/currentuser", method = RequestMethod.GET)
	@ResponseBody
	public Msg _currentUser(HttpServletRequest request,HttpServletResponse response){
		Msg m = new Msg();
		User tmp = attr(request.getSession(),"_cuser");
		m.setOvalue(tmp);
		m.setState(1);
		return m;
	}
	/**
	 * 重置密码。<br/>
	 * 根据用户名，密码，查询对应的用户，并将密码设置为npwd
	 * @param name
	 * @param pwd
	 * @param npwd
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rebuildpass", method = RequestMethod.POST)
	@ResponseBody
	@Lga(value="用户${p0}修改密码，结果:${$if(ret.state==1,'修改成功',ret.svalue)}")
	public Msg _rebuild(String name,String pwd,String npwd,HttpServletRequest request,HttpServletResponse response){
		Msg m = new Msg();
		User u = svi.findUser(name, pwd);
		if(u==null){
			m.setSvalue("@{rebuildpass.olderr}");
			return m;
		}
		if(npwd!=null && npwd.length()<32){
			npwd = md5.encodePassword(npwd);
		}
		if(pwd.equals(npwd)){
			m.setSvalue("@{rebuildpass.thesame}");
			return m;
		}
		u.setPwd(npwd);
		u.setInit(0);
		svi.updateUser(u);
		m.setState(1);
		HttpSession session = request.getSession();
		User tmp = attr(session,"_cuser");
		if(tmp!=null){
			if(name.equals(tmp.getLogname())){//如果当前的登陆用户与修改密码的用户相同。那么将初始状态设置否
				tmp.setInit(1);
			}
		}
		put(lockuser+name,0);
		return m;
	}
	private Log log(){
		if(log==null)log = LogFactory.getLog(UserAct.class);
		return log;
	}
	
	public Ehcache getCache() {
		return cache;
	}
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}


	
	private Usersvi svi;
	
	private PwdEncoder md5;
	@Autowired
	public void setSvi(Usersvi svi) {
		this.svi = svi;
	}
	@Autowired
	public void setMd5(PwdEncoder md5) {
		this.md5 = md5;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T get(String key){
		if(cache==null)return null;
		Element e = cache.get(key);	
		if(e==null)return null;		
		return (T)e.getObjectValue();
	}
	
	private void put(String key,Object value){
		if(cache==null)return ;
		//cache.remove(key);
		cache.put(new Element(key,value));
	}
	/**
	 * 将long数据转化为时间
	 * @param l
	 * @return
	 */
	private static String parseL2S(long l){
		l = l/1000;//化为秒
		int i = (int)l/3600;//小时
		l=l-i*3600;
		int m = (int)l/60;//分
		l = l-m*60;
		return (i>0?(i+":"):"")+m+":"+l;
	}
	

}
