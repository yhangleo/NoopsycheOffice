package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;
import BP.Sys.PubClass;
import BP.Tools.StringHelper;

/**
 * 按钮
 */
public class FrmBtn extends EntityMyPK
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final String getMsgOK()
	{
		return this.GetValStringByKey(FrmBtnAttr.MsgOK);
	}
	
	public final void setMsgOK(String value)
	{
		this.SetValByKey(FrmBtnAttr.MsgOK, value);
	}
	
	public final String getMsgErr()
	{
		return this.GetValStringByKey(FrmBtnAttr.MsgErr);
	}
	
	public final void setMsgErr(String value)
	{
		this.SetValByKey(FrmBtnAttr.MsgErr, value);
	}
	
	/**
	 * EventContext
	 */
	public final String getEventContext()
	{
		String context = this.GetValStringByKey(FrmBtnAttr.EventContext);
		if (!StringHelper.isNullOrEmpty(context))
		{
			context = context.replace("#", "@");
		}
		return context;
		// return this.GetValStringByKey(FrmBtnAttr.EventContext);
	}
	
	public final void setEventContext(String value)
	{
		this.SetValByKey(FrmBtnAttr.EventContext, value);
	}
	
	public String getIsViewHtml()
	{
		try
		{
			return PubClass.ToHtmlColor(this.getIsView());
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * IsView
	 */
	public final String getIsView()
	{
		return this.GetValStringByKey(FrmBtnAttr.IsView);
	}
	
	public final void setIsView(String value)
	{
		if (value.equals("#FF000000"))
		{
			this.SetValByKey(FrmBtnAttr.IsView, "Red");
			return;
		} else
		{
		}
		this.SetValByKey(FrmBtnAttr.IsView, value);
	}
	
	public final String getUACContext()
	{
		return this.GetValStringByKey(FrmBtnAttr.UACContext);
	}
	
	public final void setUACContext(String value)
	{
		this.SetValByKey(FrmBtnAttr.UACContext, value);
	}
	
	public final boolean getEventType()
	{
		return this.GetValBooleanByKey(FrmBtnAttr.EventType);
	}
	
	public final void setEventType(boolean value)
	{
		this.SetValByKey(FrmBtnAttr.EventType, value);
	}
	
	public final boolean getUAC()
	{
		return this.GetValBooleanByKey(FrmBtnAttr.UAC);
	}
	
	public final void setUAC(boolean value)
	{
		this.SetValByKey(FrmBtnAttr.UAC, value);
	}
	
	/**
	 * IsEnable
	 */
	public final boolean getIsEnable()
	{
		return this.GetValBooleanByKey(FrmBtnAttr.IsEnable);
	}
	
	public final void setIsEnable(boolean value)
	{
		this.SetValByKey(FrmBtnAttr.IsEnable, value);
	}
	
	/**
	 * Y
	 */
	public final float getY()
	{
		return this.GetValFloatByKey(FrmBtnAttr.Y);
	}
	
	public final void setY(float value)
	{
		this.SetValByKey(FrmBtnAttr.Y, value);
	}
	
	/**
	 * X
	 */
	public final float getX()
	{
		return this.GetValFloatByKey(FrmBtnAttr.X);
	}
	
	public final void setX(float value)
	{
		this.SetValByKey(FrmBtnAttr.X, value);
	}
	
	public final BtnEventType getHisBtnEventType()
	{
		return BtnEventType.forValue(this.GetValIntByKey(FrmBtnAttr.EventType));
	}
	
	/**
	 * BtnType
	 */
	public final int getBtnType()
	{
		return this.GetValIntByKey(FrmBtnAttr.BtnType);
	}
	
	public final void setBtnType(int value)
	{
		this.SetValByKey(FrmBtnAttr.BtnType, value);
	}
	
	/**
	 * FK_MapData
	 */
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(FrmBtnAttr.FK_MapData);
	}
	
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmBtnAttr.FK_MapData, value);
	}
	
	/**
	 * Text
	 */
	public final String getText()
	{
		return this.GetValStrByKey(FrmBtnAttr.Text);
	}
	
	public final void setText(String value)
	{
		this.SetValByKey(FrmBtnAttr.Text, value);
	}
	
	public final String getTextHtml()
	{
		if (this.getEventType())
		{
			return "<b>"
					+ this.GetValStrByKey(FrmBtnAttr.Text).replace("@", "<br>")
					+ "</b>";
		} else
		{
			return this.GetValStrByKey(FrmBtnAttr.Text).replace("@", "<br>");
		}
	}
	
	/**
	 * 按钮
	 */
	public FrmBtn()
	{
	}
	
	/**
	 * 按钮
	 * 
	 * @param mypk
	 */
	public FrmBtn(String mypk)
	{
		this.setMyPK(mypk);
		this.Retrieve();
	}
	
	/**
	 * EnMap
	 */
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		
		Map map = new Map("Sys_FrmBtn");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("按钮");
		map.setEnType(EnType.Sys);
		
		map.AddMyPK();
		map.AddTBString(FrmBtnAttr.FK_MapData, null, "FK_MapData", true, false,
				1, 30, 20);
		map.AddTBString(FrmBtnAttr.Text, null, "标签", true, false, 0, 3900, 20);
		
		map.AddTBFloat(FrmBtnAttr.X, 5, "X", true, false);
		map.AddTBFloat(FrmBtnAttr.Y, 5, "Y", false, false);
		
		map.AddTBInt(FrmBtnAttr.IsView, 0, "是否可见", false, false);
		map.AddTBInt(FrmBtnAttr.IsEnable, 0, "是否起用", false, false);
		
		map.AddTBInt(FrmBtnAttr.BtnType, 0, "类型", false, false);
		
		map.AddTBInt(FrmBtnAttr.UAC, 0, "控制类型", false, false);
		map.AddTBString(FrmBtnAttr.UACContext, null, "控制内容", true, false, 0,
				3900, 20);
		
		map.AddTBInt(FrmBtnAttr.EventType, 0, "事件类型", false, false);
		map.AddTBString(FrmBtnAttr.EventContext, null, "事件内容", true, false, 0,
				3900, 20);
		
		map.AddTBString(FrmBtnAttr.MsgOK, null, "运行成功提示", true, false, 0, 500,
				20);
		map.AddTBString(FrmBtnAttr.MsgErr, null, "运行失败提示", true, false, 0, 500,
				20);
		
		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);
		
		this.set_enMap(map);
		return this.get_enMap();
	}
}