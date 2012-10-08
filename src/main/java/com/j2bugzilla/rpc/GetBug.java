package com.j2bugzilla.rpc;

import java.util.HashMap;
import java.util.Map;

import com.j2bugzilla.base.BugBase;
import com.j2bugzilla.base.BugFactory;
import com.j2bugzilla.base.BugzillaMethod;

/**
 * Allows users to retrieve a specific bug for which the ID is already known
 * 
 * @author Tom
 * 
 */
public class GetBug<T extends BugBase> implements BugzillaMethod
{
	private Class<T> bugClass;

	/**
	 * The method name for this {@link BugzillaMethod}
	 */
	private static final String GET_BUG = "Bug.get";

	private Map<Object, Object> hash = new HashMap<Object, Object>();
	private Map<Object, Object> params = new HashMap<Object, Object>();

	/**
	 * Creates a new {@link GetBug} object to retrieve the {@code Bug} specified
	 * by the ID parameter
	 * 
	 * @param id
	 *            An {@code int} representing the ID of an existing bug in the
	 *            installation connected to
	 */
	public GetBug(final Class<T> bugClass, final int id)
	{
		this(bugClass);
		params.put("ids", id);
	}

	public GetBug(final Class<T> bugClass, final String alias)
	{
		this(bugClass);
		params.put("ids", alias);
	}
	
	private GetBug(final Class<T> bugClass)
	{
		this.bugClass = bugClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setResultMap(final Map<Object, Object> hash)
	{
		this.hash = hash;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<Object, Object> getParameterMap()
	{
		return params;
	}

	/**
	 * Retrieves the {@link com.j2bugzilla.base.Bug} corresponding to the given
	 * ID
	 * 
	 * @return A {@code Bug} matching the ID, or null if the returned hash does
	 *         not contain a match
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public T getBug() throws InstantiationException, IllegalAccessException
	{
		T result = null;
		if (hash.containsKey("bugs"))
		{
			Object[] bugs = (Object[]) hash.get("bugs");
			if (bugs.length == 0)
			{
				return result; // early return if map is empty
			}

			for (Object o : bugs)
			{
				@SuppressWarnings("unchecked")
				Map<String, Object> bugMap = (HashMap<String, Object>) o;

				if (!bugMap.containsKey("version"))
				{
					// version required for bugs, but older versions of Bugzilla
					// didn't return it in the outer map -- check the
					// 'internals'
					@SuppressWarnings("unchecked")
					Map<String, Object> internals = (Map<String, Object>) bugMap.get("internals");
					Double version = (Double) internals.get("version");
					bugMap.put("version", version.toString());
				}

				result = new BugFactory<T>(bugClass).createBug(bugMap);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getMethodName()
	{
		return GET_BUG;
	}

}
