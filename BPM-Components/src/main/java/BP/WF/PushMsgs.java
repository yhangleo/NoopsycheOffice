package BP.WF;

import java.util.List;

import BP.En.EntitiesMyPK;
import BP.En.Entity;
import BP.En.QueryObject;

/**
 * 消息推送
 */
public class PushMsgs extends EntitiesMyPK
{
	/**
	 * 消息推送
	 */
	public PushMsgs()
	{
	}
	
	/**
	 * 消息推送
	 * 
	 * @param fk_flow
	 */
	public PushMsgs(String fk_flow)
	{
		
		QueryObject qo = new QueryObject(this);
		qo.AddWhereInSQL(PushMsgAttr.FK_Node,
				"SELECT NodeID FROM WF_Node WHERE FK_Flow='" + fk_flow + "'");
		qo.DoQuery();
	}
	
	/**
	 * 得到它的 Entity
	 */
	@Override
	public Entity getGetNewEntity()
	{
		return new PushMsg();
	}
	public List<PushMsg> ToJavaList()
	{
		return (List<PushMsg>)(Object)this;
	}
}