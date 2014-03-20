package com.j2bugzilla.base;

import java.util.HashMap;
import java.util.Map;

/**
 * A version of the BaseBug class that works when retrieving bugs from Bugzilla
 * 4
 *
 * @author Matthew Casperson
 */
public class ECSBug extends BugBase {
    /**
     * The keys which <em>must</em> have non-blank values for a bug to be
     * properly submitted
     */
    private static String[] requiredKeys = {"product"};

    public ECSBug() {
        setInternalState(new HashMap<String, Object>());
    }

    public ECSBug(final Map<String, Object> state) {
        super(state);
    }

    @Override
    protected String[] getRequiredKeys() {

        return requiredKeys;
    }

    /**
     * Returns the internal Bugzilla ID number for this bug. If it is not in the
     * Bugzilla database, this will return null.
     *
     * @return integer ID
     */
    @Override
    public Integer getID() {
        /* When returning a specific bug, the id is in the id field */
        if (getInternalState().containsKey("id")) return (Integer) getInternalState().get("id");
		/* When doing a search, the id is in the bug_id field */
        return (Integer) getInternalState().get("bug_id");
    }

    public String getBuildId() {
        return (String) getInternalState().get("cf_build_id");
    }
}
