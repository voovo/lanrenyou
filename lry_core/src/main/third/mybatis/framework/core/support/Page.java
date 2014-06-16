package mybatis.framework.core.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象. 包含当前页数据及分页信息.
 */
public class Page implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * 默认值 - 每页显示的数据条数
	 */
	private static final int DEFAULT_PAGE_SIZE = 20;
	/**
	 * 每页显示的数据条数
	 */
	private int pageSize;
	/**
	 * 第一条数据的索引位置,用于设置查询数据的起始位置
	 */
	private int start;
	/**
	 * 数据对象
	 */
	private List data;
	/**
	 * 数据总数
	 */
	private long totalCount;

	public Page()
	{
		this(0, 0L, DEFAULT_PAGE_SIZE, ((List) (new ArrayList())));
	}

	public Page(int start, long totalSize, int pageSize, List data)
	{
		if (pageSize <= 0 || start < 0 || totalSize < 0L)
		{
			throw new IllegalArgumentException("Illegal Arguments to Initiate Page Object");
		}
		else
		{
			this.pageSize = pageSize;
			this.start = start;
			this.totalCount = totalSize;
			this.data = data;
			return;
		}
	}

	/**
	 * 得到数据总数
	 * @return
	 */
	public long getTotalCount()
	{
		return totalCount;
	}

	/**
	 * 得到总页数
	 * @return
	 */
	public long getTotalPageCount()
	{
		return totalCount % (long) pageSize != 0L ? totalCount / (long) pageSize + 1L : totalCount
				/ (long) pageSize;
	}

	/**
	 * 每页显示的数据条数
	 * @return
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	public void setResult(List data)
	{
		this.data = data;
	}

	/**
	 * 返回数据对象
	 * @return
	 */
	public List getResult()
	{
		return data;
	}

	/**
	 * 得到当前页数
	 * @return
	 */
	public int getCurrentPageNo()
	{
		return start / pageSize + 1;
	}

	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean hasNextPage()
	{
		return (long) getCurrentPageNo() < getTotalPageCount();
	}

	/**
	 * 是否有上一页
	 * @return
	 */
	public boolean hasPreviousPage()
	{
		return getCurrentPageNo() > 1;
	}

	/**
	 * 是否为空
	 * @return
	 */
	public boolean isEmpty()
	{
		return data == null || data.isEmpty();
	}

	public int getStartIndex()
	{
		return (getCurrentPageNo() - 1) * pageSize;
	}

	public int getEndIndex()
	{
		int endIndex = getCurrentPageNo() * pageSize - 1;
		return (long) endIndex < totalCount ? endIndex : (int) totalCount - 1;
	}

	protected static int getStartOfPage(int pageNo)
	{
		return getStartOfPage(pageNo, 20);
	}

	public static int getStartOfPage(int pageNo, int pageSize)
	{
		return (pageNo - 1) * pageSize;
	}

	@Override
	public String toString()
	{
		
		return new StringBuilder("Page{totalCount: ").append(getTotalCount())
			.append("; pageNo: ").append(getCurrentPageNo())
			.append("; pageSize: ").append(getPageSize())
			.append("; pageCount: ").append(getTotalPageCount())
			.append("; data: ").append(data == null ? 0 : data.size())
			.append("; round: ").append(getStartIndex()).append("-").append(getEndIndex())
			.append("}").toString();
	}
}
