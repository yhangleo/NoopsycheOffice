package BP.En;

/**
 * 多对多的集合
 */
public abstract class EntitiesMM extends Entities
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 多对多的集合
	 */
	protected EntitiesMM()
	{
	}
	
	/**
	 * 提供通过一个实体的 val 查询另外的实体集合。
	 * 
	 * @param attr
	 *            属性
	 * @param val
	 *            植
	 * @param refEns
	 *            关联的集合
	 * @return 关联的集合
	 */
	protected final Entities throwOneKeyValGetRefEntities(String attr, int val,
			Entities refEns)
	{
		QueryObject qo = new QueryObject(refEns);
		qo.AddWhere(attr, val);
		return refEns;
	}
	
	/**
	 * 提供通过一个实体的 val 查询另外的实体集合。
	 * 
	 * @param attr
	 *            属性
	 * @param val
	 *            植
	 * @param refEns
	 *            关联的集合
	 * @return 关联的集合
	 */
	protected final Entities throwOneKeyValGetRefEntities(String attr,
			String val, Entities refEns)
	{
		QueryObject qo = new QueryObject(refEns);
		qo.AddWhere(attr, val);
		return refEns;
	}
}