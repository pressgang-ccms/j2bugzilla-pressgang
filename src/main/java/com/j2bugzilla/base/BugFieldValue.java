package com.j2bugzilla.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BugFieldValue {
    /**
     * HashMap representing fields for each Bug Field Value. The Value for each Key is a
     * <code>String</code>.
     */
    private Map<String, Object> internalState;

    /**
     * Constructor for creating a new {@link com.j2bugzilla.base.BugFieldValue}
     */
    public BugFieldValue() {

    }

    /**
     * Constructor for creating a new {@link com.j2bugzilla.base.BugFieldValue} to submit to an installation.
     *
     * @param state A <code>Map</code> pairing required keys to values
     */
    public BugFieldValue(final Map<String, Object> state) {
        setInternalState(state);
    }

    /**
     * Returns the internal Bugzilla ID number for this product version. If it is not in the
     * Bugzilla database, this will return null.
     *
     * @return The product version ID
     */
    public Integer getID() {
        return (Integer) getInternalState().get("id");
    }

    /**
     * Returns the internal Bugzilla Name for this Product Version. If it is not in the
     * Bugzilla database, this will return null.
     *
     * @return The product version name
     */
    public String getName() {
        return (String) getInternalState().get("name");
    }

    /**
     * Sets the name of this {@link com.j2bugzilla.base.BugFieldValue}.
     *
     * @param name A {@code String} representing a unique name for this Product Version.
     */
    public void setName(final String name) {
        getInternalState().put("name", name);
    }

    /**
     * Returns the internal Bugzilla Description for this field value. If it is not in the
     * Bugzilla database, this will return null.
     *
     * @return The field value description
     */
    public String getDescription() {
        return (String) getInternalState().get("description");
    }

    /**
     * Sets the description of this {@link BugFieldValue}.
     *
     * @param description A {@code String} representing a description for this field value.
     */
    public void setDescription(final String description) {
        getInternalState().put("description", description);
    }

    public Boolean getIsActive() {
        final Object isActive = getInternalState().get("is_active");
        return isActive == null ? null : (Boolean) isActive;
    }

    public void setIsActive(Boolean isActive) {
        getInternalState().put("is_active", isActive);
    }

    public Integer getSortKey() {
        return (Integer) getInternalState().get("sort_key");
    }

    public void setSortKey(Integer sortKey) {
        getInternalState().put("sort_key", sortKey);
    }

    /**
     * Used when a representation of this {@link com.j2bugzilla.base.BugFieldValue Product Version's} internals must be
     * passed via XML-RPC for a remote method. Regular users of this API should
     * prefer the normal {@code getXxx()} methods.
     *
     * @return A read-only {@code Map} of key-value pairs corresponding to this
     *         {@code Product Version's} properties.
     */
    public Map<Object, Object> getParameterMap() {
        Map<Object, Object> params = new HashMap<Object, Object>();
        for (final String key : getInternalState().keySet()) {
            params.put(key, getInternalState().get(key));
        }
        return Collections.unmodifiableMap(params);
    }

    public Map<String, Object> getInternalState() {
        return internalState;
    }

    public void setInternalState(final Map<String, Object> internalState) {
        this.internalState = internalState;
    }
}
