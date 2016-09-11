package BP.Demo;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/**
 * 学生组
 */
public class Students extends EntitiesNoName
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 13242323232L;
	
	/**
	 * 构造函数
	 */
	public Students()
	{
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Student> convertStudents(Object obj)
	{
		return (ArrayList<Student>) obj;
	}
	
	/**
	 * 得到它的 Entity
	 */
	@Override
	public Entity getGetNewEntity()
	{
		return new Student();
	}
}