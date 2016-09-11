package BP.WF.Data;

import java.util.*;
import BP.DA.*;
import BP.WF.*;
import BP.Port.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Template.*;

/** 
 流程实例s
*/
public class GenerWorkFlowViews extends Entities
{
	/** 
	 根据工作流程,工作人员 ID 查询出来他当前的能做的工作.
	 
	 @param flowNo 流程编号
	 @param empId 工作人员ID
	 @return 
	*/
	public static DataTable QuByFlowAndEmp(String flowNo, int empId)
	{
		String sql = "SELECT a.WorkID FROM WF_GenerWorkFlowView a, WF_GenerWorkerlist b WHERE a.WorkID=b.WorkID   AND b.FK_Node=a.FK_Node  AND b.FK_Emp='" + String.valueOf(empId) + "' AND a.FK_Flow='" + flowNo + "'";
		return DBAccess.RunSQLReturnTable(sql);
	}


		///#region 方法
	/** 
	 得到它的 Entity 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new GenerWorkFlowView();
	}
	/** 
	 流程实例集合
	*/
	public GenerWorkFlowViews()
	{
	}
		///#endregion

		///#region 为了适应自动翻译成java的需要,把实体转换成List.
	/** 
	 转化成 java list,C#不能调用.
	 
	 @return List
	*/
	public final List<GenerWorkFlowView> ToJavaList()
	{
		return (List<GenerWorkFlowView>)(Object)this;
	}
	/** 
	 转化成list
	 
	 @return List
	*/
	public final ArrayList<GenerWorkFlowView> Tolist()
	{
		ArrayList<GenerWorkFlowView> list = new ArrayList<GenerWorkFlowView>();
		for (int i = 0; i < this.size(); i++)
		{
			list.add((GenerWorkFlowView)this.get(i));
		}
		return list;
	}
		///#endregion 为了适应自动翻译成java的需要,把实体转换成List.
}