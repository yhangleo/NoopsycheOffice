package BP.Web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.impl.common.IdentityConstraint.IdState;
import org.junit.runner.Request;

import BP.DA.DBAccess;
import BP.DA.DataType;
import BP.DA.Paras;
import BP.En.QueryObject;
import BP.Port.Current;
import BP.Port.Depts;
import BP.Port.Emp;
import BP.Port.EmpDepts;
import BP.Port.Stations;
import BP.Sys.SystemConfig;
import BP.Tools.StringHelper;
import BP.WF.Dev2Interface;
import cn.jflow.common.util.ContextHolderUtils;

/**
 * User 的摘要说明。
 */
public class WebUser {

	/**
	 * 登录
	 * 
	 * @param em
	 */
	public static void SignInOfGener(Emp em) {
		SignInOfGener(em, "CH", null, true, false);
	}

	/**
	 * 登录
	 * 
	 * @param em
	 * @param isRememberMe
	 */
	public static void SignInOfGener(Emp em, boolean isRememberMe) {
		SignInOfGener(em, "CH", null, isRememberMe, false);
	}

	/**
	 * 登录
	 * 
	 * @param em
	 * @param auth
	 */
	public static void SignInOfGenerAuth(Emp em, String auth) {
		SignInOfGener(em, "CH", auth, true, false);
	}

	/**
	 * 登录
	 * 
	 * @param em
	 * @param lang
	 */
	public static void SignInOfGenerLang(Emp em, String lang, boolean isRememberMe) {
		SignInOfGener(em, lang, null, isRememberMe, false);
	}

	/**
	 * 登录
	 * 
	 * @param em
	 * @param lang
	 */
	public static void SignInOfGenerLang(Emp em, String lang) {
		SignInOfGener(em, lang, null, true, false);
	}

	public static void SignInOfGener(Emp em, String lang) {
		SignInOfGener(em, lang, em.getNo(), true, false);
	}

	/**
	 * 登录
	 * 
	 * @param em 登录人
	 * @param lang 语言
	 * @param auth 被授权登录人
	 * @param isRememberMe 是否记忆我
	 */
	public static void SignInOfGener(Emp em, String lang, String auth, boolean isRememberMe) {
		SignInOfGener(em, lang, auth, isRememberMe, false);
	}

	/**
	 * 通用的登录
	 * 
	 * @param em 人员
	 * @param lang 语言
	 * @param auth 授权人
	 * @param isRememberMe 是否记录cookies
	 * @param IsRecSID 是否记录SID
	 */
	public static String SignInOfGener(Emp em, String lang, String auth, boolean isRememberMe, boolean IsRecSID) {
		if (SystemConfig.getIsBSsystem()) {
			BP.Sys.Glo.WriteUserLog("SignIn", em.getNo(), "登录");
		}
		if (auth == null) {
			auth = "";
		}
		String sid = null;
		try {
			WebUser.setNo(em.getNo());
			WebUser.setName(em.getName());
			WebUser.setFK_Dept(em.getFK_Dept());
			WebUser.setFK_DeptName(em.getFK_DeptText());
			
			try {
				sid = ContextHolderUtils.getSession().getId();
			} catch (Exception e) {
				sid = DBAccess.GenerOID()+"";
			}
			
			if (IsRecSID) {
				WebUser.setSID(sid);
				Dev2Interface.Port_SetSID(em.getNo(), sid);
			}
			
			WebUser.setAuth(auth);
			WebUser.setUserWorkDev(UserWorkDev.PC);
			WebUser.setSysLang(lang);
			
			if (SystemConfig.getIsBSsystem()) {
				try {
					int expiry = 60 * 60 * 24 * 2;
					ContextHolderUtils.addCookie("No", expiry, em.getNo());
					ContextHolderUtils.addCookie("Name", expiry, URLEncoder.encode(em.getName(), "UTF-8"));
					ContextHolderUtils.addCookie("IsRememberMe", expiry, isRememberMe ? "1" : "0");
					ContextHolderUtils.addCookie("FK_Dept", expiry, em.getFK_Dept());
					ContextHolderUtils.addCookie("FK_DeptName", expiry, URLEncoder.encode(em.getFK_DeptText(), "UTF-8"));
					if (ContextHolderUtils.getSession() != null) {
						ContextHolderUtils.addCookie("Token", expiry, sid);
						ContextHolderUtils.addCookie("SID", expiry, sid);
					}
					ContextHolderUtils.addCookie("Lang", expiry, lang);
//					String isEnableStyle = SystemConfig.getAppSettings().get("IsEnableStyle").toString();
//					if (isEnableStyle.equals("1")) {
//						try {
//							String sql = "SELECT Style FROM WF_Emp WHERE No='" + em.getNo() + "' ";
//							int val = DBAccess.RunSQLReturnValInt(sql, 0);
//							/*
//							 * warning cookie.Values.Add("Style", (new
//							 * Integer(val)).toString());
//							 */
//							ContextHolderUtils.addCookie("Style", expiry, String.valueOf(val));
//							WebUser.setStyle(String.valueOf(val));
//						} catch (java.lang.Exception e) {}
//					}
					/*
					 * warning cookie.Values.Add("Auth", auth); // 授权人.
					 * BP.Glo.getHttpContextCurrent().Response.AppendCookie(cookie);
					 */
					ContextHolderUtils.addCookie("Auth", expiry, auth);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sid;
	}

	/**
	 * 退出
	 */
	public static void Exit() {
		try {
//			String token = WebUser.getToken();
//			ContextHolderUtils.clearCookie();// 不能全部都清除，应只清理本系统数据。
//			ContextHolderUtils.getSession().invalidate();// 不能全部都清除，应只清理本系统数据。
			// 清理Session
			WebUser.setNo(null);
			WebUser.setName(null);
			WebUser.setFK_Dept(null);
			WebUser.setFK_DeptName(null);
			WebUser.setSID(null);
			WebUser.setAuth(null);
			WebUser.setUserWorkDev(null);
			WebUser.setSysLang(null);
			if (SystemConfig.getIsBSsystem()) {
				int expiry = 60 * 60 * 24 * 2;
				ContextHolderUtils.addCookie("No", expiry, null);
				ContextHolderUtils.addCookie("Name", expiry, null);
				ContextHolderUtils.addCookie("IsRememberMe", expiry, null);
				ContextHolderUtils.addCookie("FK_Dept", expiry, null);
				ContextHolderUtils.addCookie("FK_DeptName", expiry, null);
				ContextHolderUtils.addCookie("Token", expiry, null);
				ContextHolderUtils.addCookie("SID", expiry, null);
				ContextHolderUtils.addCookie("Lang", expiry, null);
				ContextHolderUtils.addCookie("Auth", expiry, null);
			}
//			WebUser.setToken(token);
		} catch (java.lang.Exception e2) {}
	}

	/**
	 * 是不是b/s 工作模式。
	 */
	private static boolean getIsBSMode() {
		if (ContextHolderUtils.getRequest() == null) {
			return false;
		} else {
			return true;
		}
	}
	
//	public static Object GetObjByKey(String key) {
//		if (getIsBSMode()) {
//			/*
//			 * warning return BP.Glo.getHttpContextCurrent().Session[key];
//			 */
//			return ContextHolderUtils.getSession().getAttribute(key);
//		} else {
//			return Current.Session.get(key);
//		}
//	}

	// 静态方法
	/**
	 * 通过key,取出session.
	 * 
	 * @param key key
	 * @param isNullAsVal 如果是Null, 返回的值.
	 * @return
	 */
	public static String GetSessionByKey(String key, String isNullAsVal) {
//		return GetSessionByKey(key, isNullAsVal, false);
//	}
//
//	/**
//	 * 通过key,取出session.
//	 * 
//	 * @param key key
//	 * @param isNullAsVal 如果是Null, 返回的值.
//	 * @return
//	 */
//	public static String GetSessionByKey(String key, String isNullAsVal, boolean isChinese) {
//		try {
			if (getIsBSMode() && null != ContextHolderUtils.getRequest() && null != ContextHolderUtils.getSession()) {
				Object value = ContextHolderUtils.getSession().getAttribute(key);
				String str = value == null ? "" : String.valueOf(value);
				if (StringHelper.isNullOrEmpty(str)) {
					str = isNullAsVal;
				}
				return str;
			} else {
				if ((Current.Session.get(key) == null || Current.Session.get(key).toString().equals("")) && isNullAsVal != null) {
					return isNullAsVal;
				} else {
					String str = (String) Current.Session.get(key);
					return str;
				}
			}
//		} catch (UnsupportedEncodingException e) {
//			return isNullAsVal;
//		}
	}

	public static Object GetSessionByKey(String key, Object defaultObjVal) {
		if (getIsBSMode() && null != ContextHolderUtils.getRequest() && null != ContextHolderUtils.getSession()) {
			Object value = ContextHolderUtils.getSession().getAttribute(key);
			if (null == value) {
				return defaultObjVal;
			} else {
				return value;
			}
		} else {
			if (Current.Session.get(key) == null) {
				return defaultObjVal;
			} else {
				return Current.Session.get(key);
			}
		}
	}

	/**
	 * 设置session
	 * 
	 * @param key 键
	 * @param val 值
	 */
	public static void SetSessionByKey(String key, Object val) {
		if (getIsBSMode() && null != ContextHolderUtils.getRequest() && null != ContextHolderUtils.getSession()) {
			ContextHolderUtils.getSession().setAttribute(key, val);
		} else {
			Current.SetSession(key, val);
		}
	}

	public static String GetValFromCookie(String valKey, String isNullAsVal, boolean isChinese) {
		if (!getIsBSMode()) {
			return Current.GetSessionStr(valKey, isNullAsVal);
		}
	
		try {
			// 先从session里面取.
			Object value = ContextHolderUtils.getSession().getAttribute(valKey);
			String v = value == null ? "" : String.valueOf(value);
			if (!StringHelper.isNullOrEmpty(v)) {
				if (isChinese) {
					v = URLDecoder.decode(v, "UTF-8");
				}
				return v;
			}
		} catch (java.lang.Exception e) {}
	
		try {
			String val = null;
			Cookie cookie = ContextHolderUtils.getCookie(valKey);
			if (cookie != null){
				if (isChinese) {
					val = URLDecoder.decode(cookie.getValue(), "UTF-8");
				} else {
					val = cookie.getValue();
				}
			}
	
			if (StringHelper.isNullOrEmpty(val)) {
				return isNullAsVal;
			}
			return val;
		} catch (java.lang.Exception e2) {
			e2.printStackTrace();
			return isNullAsVal;
		}
	}

	/**
	 * 查询全部部门
	 * @return
	 */
	public static Depts getHisDepts() {
		Object obj = null;
		obj = GetSessionByKey("HisDepts", obj);
		if (obj == null) {
			Depts sts = WebUser.getHisEmp().getHisDepts();
			SetSessionByKey("HisDepts", sts);
			return sts;
		}
		return (Depts) ((obj instanceof Depts) ? obj : null);
	}

	public static String getSysLang() {
		return "CH";
	}

	public static void setSysLang(String value) {
		SetSessionByKey("Lang", value);
	}

	/**
	 * FK_Dept
	 */
	public static String getFK_Dept() {
		String val = GetValFromCookie("FK_Dept", null, false);
		if (val == null) {
			throw new RuntimeException("@err-003 FK_Dept 登录信息丢失，请确认当前操作员的部门信息是否完整，检查表:Port_Emp字段FK_Dept。");
		}
		return val;
	}

	public static void setFK_Dept(String value) {
		SetSessionByKey("FK_Dept", value);
	}

	/**
	 * 当前登录人员的父节点编号
	 */
	public static String getDeptParentNo() {
		String val = GetValFromCookie("DeptParentNo", null, false);
		if (val == null) {
			throw new RuntimeException("@err-001 DeptParentNo 登录信息丢失。");
		}
		return val;
	}

	public static void setDeptParentNo(String value) {
		SetSessionByKey("DeptParentNo", value);
	}

	/**
	 * 检查权限控制
	 * @param sid
	 * @return
	 */
	public static boolean CheckSID(String UserNo, String SID) {
		/*String mysid = null;
		try {
			mysid = DBAccess.RunSQLReturnStringIsNull(
					"SELECT SID FROM Port_Emp WHERE No='" + UserNo + "'", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (SID != null && SID.equals(mysid)) {
			return true;
		} else {
			return false;
		}*/
		return true;
	}

	/**
	 * sessionID
	 */
	public static String getNoOfSessionID() {
		String s = GetSessionByKey("No", null);
		if (s == null) {
			/*
			 * warning return BP.Glo.getHttpContextCurrent().Session.SessionID;
			 */
			return ContextHolderUtils.getSession().getId();
		}
		return s;
	}

	public static String getNoOfRel() {
		return GetSessionByKey("No", null);
	}

	/**
	 * 编号
	 */
	public static String getNo() {
		// 如果设置了第三方的SessionKey名称，则进行根据第三方系统用户Key进行登录。
		String userNoSessionKey = ContextHolderUtils.getInstance().getUserNoSessionKey();
		if (StringUtils.isNotBlank(userNoSessionKey)){
			String userNo = ObjectUtils.toString(ContextHolderUtils.getSession().getAttribute(userNoSessionKey));
			// 如果第三方的用户id不存在，则代表已退出，同步退出jflow。
			if (StringUtils.isNotBlank(userNo)){
				// 如果是平台管理员账号，则转换为jflow的管理员账号
				if (BP.WF.Glo.getIsAdmin(userNo)){
					userNo = "admin";
				}
				// 获取当前是否已登录，如果已登录并且与第三方id一样，则直接返回，否则重新登录
				String val = GetSessionByKey("No", "");
				if (val != null && userNo.equals(val)){
					return val;
				}else{
					// 登录WF平台，这里需要保存SID方便第三方系统获取。
					BP.Port.Emp emp = new BP.Port.Emp(userNo);
					WebUser.SignInOfGener(emp, "CH", null, true, true);
				}
			}else{
				WebUser.Exit();
			}
		}
		//		String val = GetValFromCookie("No", null, true);
		String val = GetSessionByKey("No", "");
		if (val == null) {
			//val = "admin";
			throw new RuntimeException("@err-002 No 登录信息丢失。");
		}
		return val;
	}

	public static void setNo(String value) {
		SetSessionByKey("No", value);
	}

	/**
	 * 名称
	 */
	public static String getName() {
		//		String val = GetValFromCookie("Name", null, true);
		String val = GetSessionByKey("Name", "");
		if (val == null) {
			throw new RuntimeException("@err-002 Name 登录信息丢失。");
		}
		return val;
	}

	public static void setName(String value) {
		SetSessionByKey("Name", value);
	}

	/**
	 * 域
	 */
	public static String getDomain() {
		String val = GetSessionByKey("Domain", "");
		if (val == null) {
			throw new RuntimeException("@err-003 Domain 登录信息丢失。");
		}
		return val;
	}

	public static void setDomain(String value) {
		SetSessionByKey("Domain", value);
	}

	/**
	 * 令牌
	 */
	public static String getToken() {

		return GetSessionByKey("token", "null");
	}

	public static void setToken(String value) {
		SetSessionByKey("token", value);
	}

	/**
	 * 授权人
	 */
	public static String getAuth() {
		String val = GetValFromCookie("Auth", null, false);
		if (val == null) {
			val = GetSessionByKey("Auth", null);
		}
		return val;
	}

	public static void setAuth(String value) {
		if (value == null || value.equals("")) {
			SetSessionByKey("Auth", null);
		} else {
			SetSessionByKey("Auth", value);
		}
	}

	public static String getFK_DeptName() {
		try {
			//String val = GetValFromCookie("FK_DeptName", null, true);
			String val = GetSessionByKey("FK_DeptName", "");
			return val;
		} catch (java.lang.Exception e) {
			return "无";
		}
	}

	public static void setFK_DeptName(String value) {
		SetSessionByKey("FK_DeptName", value);
	}

	/**
	 * 部门全称
	 */
	public static String getFK_DeptNameOfFull() {
		String val = GetValFromCookie("FK_DeptNameOfFull", null, true);
		if (StringHelper.isNullOrEmpty(val)) {
			try {
				val = DBAccess.RunSQLReturnStringIsNull("SELECT NameOfFull FROM Port_Dept WHERE No='" + WebUser.getFK_Dept() + "'", null);
				return val;
			} catch (java.lang.Exception e) {
				val = WebUser.getFK_DeptName();
			}

			// 给它赋值.
			setFK_DeptNameOfFull(val);
		}
		return val;
	}

	public static void setFK_DeptNameOfFull(String value) {
		SetSessionByKey("FK_DeptNameOfFull", value);
	}

	public static String getStyle() {
		return GetSessionByKey("Style", "0");
	}

	public static void setStyle(String value) {
		SetSessionByKey("Style", value);
	}

	/**
	 * 当前工作人员实体
	 */
	public static Emp getHisEmp() {
		return new Emp(WebUser.getNo());
	}

	/**
	 * SID
	 */
	public static String getSID() {
		String val = GetValFromCookie("SID", null, true);
		if (val == null) {
			return "";
		}
		return val;
	}

	public static void setSID(String value) {
		SetSessionByKey("SID", value);
	}

	/**
	 * 是否是授权状态
	 */
	public static boolean getIsAuthorize() {
		if (getAuth() == null || getAuth().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * 使用授权人ID
	 */
	public static String getAuthorizerEmpID() {
		return GetSessionByKey("AuthorizerEmpID", null);

	}

	public static void setAuthorizerEmpID(String value) {
		SetSessionByKey("AuthorizerEmpID", value);
	}

	/**
	 * 用户工作方式.
	 */
	public static UserWorkDev getUserWorkDev() {
		if (BP.Sys.SystemConfig.getIsBSsystem() == false) {
			return getUserWorkDev().PC;
		}

		int s = (Integer) GetSessionByKey("UserWorkDev", 0);
		BP.Web.UserWorkDev wd = UserWorkDev.forValue(s);
		return wd;
	}

	public static void setUserWorkDev(UserWorkDev value) {
		SetSessionByKey("UserWorkDev", value.getValue());
	}

	public static boolean getIsWap() {
		if (!SystemConfig.getIsBSsystem())
			return false;
		int s = Integer.parseInt(GetSessionByKey("IsWap", 9).toString());
		if (9 == s) {
			String userAgent = ContextHolderUtils.getRequest().getHeader("USER-AGENT").toLowerCase();
			boolean b = userAgent.contains("wap");
			setIsWap(b);
			if (b) {
				SetSessionByKey("IsWap", 1);
			} else {
				SetSessionByKey("IsWap", 0);
			}
			return b;
		}
		if (s == 1)
			return true;
		else
			return false;
	}

	public static void setIsWap(boolean value) {
		if (value) {
			SetSessionByKey("IsWap", 1);
		} else {
			SetSessionByKey("IsWap", 0);
		}
	}

	// 部门权限
	/**
	 * 部门权限
	 */
	public static Depts getHisDeptsOfPower() {
		EmpDepts eds = new EmpDepts();
		return eds.GetHisDepts(WebUser.getNo());
	}

	// 部门权限

	// public static Stations getHisStations() {
	// EmpStations sts = new EmpStations();
	// return sts.GetHisStations(WebUser.getNo());
	// }
	public static Stations getHisStations() {
		Object obj = null;
		obj = GetSessionByKey("HisSts", obj);
		if (obj == null) {
			Stations sts = new Stations();
			QueryObject qo = new QueryObject(sts);
			qo.AddWhereInSQL("No", "SELECT FK_Station FROM Port_EmpStation WHERE FK_Emp='" + WebUser.getNo() + "'");
			qo.DoQuery();
			SetSessionByKey("HisSts", sts);
			return sts;
		}
		return (Stations) ((obj instanceof Stations) ? obj : null);
	}

	/**
	 * 岗位s
	 */
	public static String getHisStationsStr() {
		String val = GetValFromCookie("HisStationsStr", null, true);
		if (val == null) {
			Object tempVar = BP.DA.DBAccess.RunSQLReturnVal("SELECT Stas FROM WF_Emp WHERE No='" + WebUser.getNo() + "'");
			val = (String) ((tempVar instanceof String) ? tempVar : null);

			if (val == null) {
				val = "";
			}
			SetSessionByKey("HisStationsStr", val);
		}
		return val;
	}

	public static void setHisStationsStr(String value) {
		SetSessionByKey("HisStationsStr", value);

	}

	/**
	 * 部门s
	 */
	public static String getHisDeptsStr() {
		String val = GetValFromCookie("HisDeptsStr", "", true);
		if (val == null) {
			Object tempVar = BP.DA.DBAccess.RunSQLReturnVal("SELECT Depts FROM WF_Emp WHERE No='" + WebUser.getNo() + "'");
			val = (String) ((tempVar instanceof String) ? tempVar : null);
			if (val == null) {
				val = "";
			}
			SetSessionByKey("HisDeptsStr", val);
		}
		return val;
	}

	public static void setHisDeptsStr(String value) {
		SetSessionByKey("HisDeptsStr", value);
	}
}
