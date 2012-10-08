package com.j2bugzilla.rpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugBase;
import com.j2bugzilla.base.BugFactory;
import com.j2bugzilla.base.BugzillaMethod;

/**
 * This class provides convenience methods for searching for {@link Bug Bugs} on
 * your installation.
 * 
 * @author Tom
 * 
 */
public class BugSearch<T extends BugBase> implements BugzillaMethod
{
	private Class<T> bugClass;
	
	/**
	 * The email of the assignee
	 */
	public static final String OWNER = "assigned_to";

	/**
	 * The email of the reporting user
	 */
	public static final String REPORTER = "reporter";

	/**
	 * The {@link jbugz.base.Bug.Status} field value
	 */
	public static final String STATUS = "status";

	/**
	 * The resolution field, if the bug's status is closed. You can search for
	 * all open bugs by searching for a blank resolution.
	 */
	public static final String RESOLUTION = "resolution";

	/**
	 * The {@link jbugz.base.Bug.Priority} field value
	 */
	public static final String PRIORITY = "priority";

	/**
	 * The product affected by this bug
	 */
	public static final String PRODUCT = "product";

	/**
	 * The component affected by this bug
	 */
	public static final String COMPONENT = "component";

	/**
	 * The operating system affected by this bug
	 */
	public static final String OPERATING_SYSTEM = "op_sys";

	/**
	 * The hardware affected by this bug
	 */
	public static final String PLATFORM = "platform";

	/**
	 * The initial summary comment
	 */
	public static final String SUMMARY = "summary";

	/**
	 * The version affected by this bug
	 */
	public static final String VERSION = "version";

	/**
	 * The unique alias for a bug
	 */
	public static final String ALIAS = "alias";

	/**
	 * The method Bugzilla will execute via XML-RPC
	 */
	private static final String METHOD_NAME = "Bug.search";

	/**
	 * A {@code Map} returned by the XML-RPC method.
	 */
	private Map<Object, Object> hash = new HashMap<Object, Object>();

	/**
	 * A {@code Map} used by the XML-RPC method containing the required object
	 * parameters.
	 */
	private final Map<Object, Object> params = new HashMap<Object, Object>();

	/**
	 * Creates a new {@link BugSearch} object
	 */
	public BugSearch(final Class<T> bugClass)
	{
		this.bugClass = bugClass;
	}
	
	/**
	 * Creates a new {@link BugSearch} object with the appropriate search limit
	 * and query string.
	 * 
	 * @param limit
	 *            What dimension to search {@link Bug Bugs} by in the Bugzilla
	 *            installation
	 * @param query
	 *            What to match fields against
	 */
	public BugSearch(final Class<T> bugClass, final String limit, final String query)
	{
		this(bugClass);
		params.put(limit, query);
	}

	/**
	 * Add an additional search limit to the {@link BugSearch}
	 * 
	 * @param limit
	 *            What dimension to search {@link Bug Bugs} by in the Bugzilla
	 *            installation
	 * @param query
	 *            What to match fields against
	 */
	public void addQueryParam(final String limit, final Object query)
	{
		params.put(limit, query);
	}

	/**
	 * Returns the {@link Bug Bugs} found by the query as a <code>List</code>
	 * 
	 * @return a {@link List} of {@link Bug Bugs} that match the query and limit
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public List<T> getSearchResults() throws InstantiationException, IllegalAccessException
	{
		final List<T> results = new ArrayList<T>();
		/*
		 * The following is messy, but necessary due to how the returned XML
		 * document nests Maps.
		 */

		if (hash.containsKey("bugs"))
		{

			// Map<String, Object>[] bugList = (Map<String,
			// Object>[])hash.get("bugs");
			final Object[] bugs = (Object[]) hash.get("bugs");
			if (bugs.length == 0)
			{
				return results; // early return if map is empty
			}

			for (final Object o : bugs)
			{
				@SuppressWarnings("unchecked")
				final Map<String, Object> bugMap = (HashMap<String, Object>) o;
				final T bug = new BugFactory<T>(bugClass).createBug(bugMap);
				results.add(bug);
			}
		}
		return results;
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
	 * {@inheritDoc}
	 */
	public String getMethodName()
	{
		return METHOD_NAME;
	}
}
