package com.j2bugzilla.base;

import java.util.Map;


public class Bug extends BugBase
{
	/**
	 * The keys which <em>must</em> have non-blank values for a bug to be
	 * properly submitted
	 */
	private static String[] requiredKeys = { "product", "component", "summary", "version" };

	/**
	 * Constructor for creating a new {@link Bug} to submit to an installation.
	 * The constructor ensures any required values in {@link #requiredKeys} are
	 * set, and throws an {@link IllegalStateException} if they are null.
	 * 
	 * @param state
	 *            A <code>Map</code> pairing required keys to values
	 */
	Bug(final Map<String, Object> state)
	{
		super(state);
	}

	@Override
	protected String[] getRequiredKeys()
	{
		return requiredKeys;
	}

}
