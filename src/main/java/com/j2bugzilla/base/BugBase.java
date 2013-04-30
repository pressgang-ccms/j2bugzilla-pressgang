package com.j2bugzilla.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class encapsulates all information about a bug report posted on a
 * Bugzilla installation. It provides getter methods for various properties,
 * such as the bug summary and status. To obtain a new {@code Bug} object, you
 * must use the {@link BugFactory} class which provides a fluent interface for
 * bug creation. Note that the {@code BugFactory} does not submit a report for
 * you -- to actually add the created bug to your Bugzilla installation, you
 * must use the {@link ReportBug} method.
 * <p/>
 * This class is designed to be extended by a specific implementation that can
 * account for the differences in fields. This means that there are no fixed enums.
 *
 * @author Matthew Casperson
 */
abstract public class BugBase {
    /**
     * HashMap representing fields for each Bug. The Value for each Key is a
     * <code>String</code> <em>except</em> for the CC: field, which is an array
     * of <code>Strings</code>.
     */
    private Map<String, Object> internalState;

    /**
     * Constructor for creating a new {@link Bug}
     */
    public BugBase() {

    }

    /**
     * Constructor for creating a new {@link Bug} to submit to an installation.
     * The constructor ensures any required values in {@link #requiredKeys} are
     * set, and throws an {@link IllegalStateException} if they are null.
     *
     * @param state A <code>Map</code> pairing required keys to values
     */
    public BugBase(final Map<String, Object> state) {
        checkRequiredFields(state);
        setInternalState(state);
    }

    /**
     * @return The keys which <em>must</em> have non-blank values for a bug to be
     *         properly submitted
     */
    abstract protected String[] getRequiredKeys();

    /**
     * Internal method for determining whether a given <code>HashMap</code> is a
     * valid representation of a {@link Bug} or not.
     *
     * @param state a collection of String keys and String values in a
     *              <code>HashMap</code>
     * @throws IllegalStateException If a required key-value pair is null
     */
    private void checkRequiredFields(final Map<String, Object> state) {
        for (final String str : getRequiredKeys()) {
            if (!state.containsKey(str)) {
                throw new IllegalStateException("Missing key/value pair: " + str);
            }
        }
    }

    /**
     * Returns how highly this bug is ranked
     *
     * @return a {@link Priority} describing the relative importance of this bug
     */
    public int getPriority() {
        final String p = (String) getInternalState().get("priority");
        int level = Integer.valueOf(p);
        switch (level) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return 3;
        }
    }

    /**
     * Returns the internal Bugzilla ID number for this bug. If it is not in the
     * Bugzilla database, this will return null.
     *
     * @return integer ID
     */
    public Integer getID() {
        return (Integer) getInternalState().get("id");
    }

    /**
     * Returns the unique alias of this {@link Bug}. If none is set, this method
     * will return null.
     *
     * @return A {@code String} representing the unique alias for this bug.
     */
    public String getAlias() {
        return getInternalState().get("alias").toString();
    }

    /**
     * Sets the alias of this {@link Bug}. By default, Bugzilla restricts
     * aliases to be 20 characters in length.
     *
     * @param alias A {@code String} representing a unique alias for this bug.
     */
    public void setAlias(final String alias) {
        getInternalState().put("alias", alias);
    }

    /**
     * Returns the one-line summary included with the original bug report.
     *
     * @return A {@code String} representing the summary entered for this
     *         {@link Bug}.
     */
    public String getSummary() {
        return getInternalState().get("summary").toString();
    }

    /**
     * Sets the one-line summary of this {@link Bug}.
     *
     * @param summary A {@code String} representing the summary describing this bug.
     */
    public void setSummary(final String summary) {
        getInternalState().put("summary", summary);
    }

    /**
     * Returns the user this {@link Bug} is assigned to.
     *
     * @return the Product category this {@link Bug} is filed under.
     */
    public String getAssignedTo() {
        return getInternalState().get("assigned_to").toString();
    }

    /**
     * Sets the user this {@link Bug} is assigned to.
     *
     * @param product A {@code String} representing the product name.
     */
    public void setAssignedTo(final String product) {
        getInternalState().put("assigned_to", product);
    }

    /**
     * @return Returns the open status of this {@link Bug}.
     */
    public Boolean getIsOpen() {
        final Object isOpen = getInternalState().get("is_open");
        return isOpen == null ? null : (Boolean) isOpen;
    }

    /**
     * Sets wether this bug is open of not
     *
     * @param product true if open, false otherwise
     */
    public void setIsOpen(final Boolean open) {
        getInternalState().put("is_open", open);
    }

    /**
     * Returns the product this {@link Bug} belongs to.
     *
     * @return the Product category this {@link Bug} is filed under.
     */
    public String getProduct() {
        return getInternalState().get("product").toString();
    }

    /**
     * Sets the product this {@link Bug} is associated with. Note that a
     * nonexistent product name will result in an error from Bugzilla upon bug
     * submission.
     *
     * @param product A {@code String} representing the product name.
     */
    public void setProduct(final String product) {
        getInternalState().put("product", product);
    }

    /**
     * Returns the component this {@link Bug} is associated with.
     *
     * @return the component of the {@link Bug}'s parent Product
     */
    public String getComponent() {
        final Object component = getInternalState().get("component");

        if (component instanceof Object[]) {
            final StringBuffer retValue = new StringBuffer();
            final Object[] components = (Object[]) component;
            for (final Object componentsElement : components) {
                if (retValue.length() != 0) retValue.append("\n");
                retValue.append(componentsElement.toString());
            }

            return retValue.toString();
        }

        return component.toString();
    }

    /**
     * Sets the component this {@link Bug} is associated with. Note that a
     * nonexistent component name will result in Bugzilla returning an error
     * upon submission.
     *
     * @param component A {@code String} representing the component name.
     */
    public void setComponent(final String component) {
        getInternalState().put("component", component);
    }

    /**
     * Returns the version number of the product this {@link Bug} is associated
     * with.
     *
     * @return the version associated with this {@link Bug}
     */
    public String getVersion() {
        return getInternalState().get("version").toString();
    }

    /**
     * Sets the version number of the product this {@link Bug} is associated
     * with. Note that a nonexistent version number will result in Bugzilla
     * returning an error on submission.
     *
     * @param version A {@code String} describing the version number of the product
     *                affected by this bug.
     */
    public void setVersion(final String version) {
        getInternalState().put("version", version);
    }

    /**
     * Returns the {@link Status} of this {@link Bug} indicating whether it is
     * open or closed.
     *
     * @return the {@link Status} of a {@link Bug}
     */
    public String getStatus() {
        /*
		 * Bit of a hacky solution, but it avoids switching over the enums to
		 * match them to Strings.
		 */
        return getInternalState().get("status").toString();
    }

    /**
     * Sets the {@link Status} of this {@link Bug} indicating whether it is open
     * or closed.
     *
     * @param status The {@code Status} of this bug.
     */
    public void setStatus(final String status) {
        getInternalState().put("status", status);
    }

    /**
     * Returns the operating system this bug was discovered to affect.
     *
     * @return A {@code String} representing the name of the affected operating
     *         system.
     */
    public String getOperatingSystem() {
        return getInternalState().get("op_sys").toString();
    }

    /**
     * Sets the operating system this {@link Bug} was discovered to affect. Note
     * that the default values provided by Bugzilla are "All," "Mac OS,"
     * "Windows," "Linux," or "Other."
     *
     * @param os A {@code String} representing the operating system name.
     */
    public void setOperatingSystem(final String os) {
        getInternalState().put("op_sys", os);
    }

    /**
     * Returns the hardware platform this bug was discovered to affect.
     *
     * @return A {@code String} representing the name of the affected platform.
     */
    public String getPlatform() {
        return getInternalState().get("platform").toString();
    }

    /**
     * Sets the platform affected by this {@link Bug}. Note that the default
     * values provided by Bugzilla are "All," "Macintosh," "PC," or "Other."
     *
     * @param platform A {@code String} representing the platform name.
     */
    public void setPlatform(final String platform) {
        getInternalState().put("platform", platform);
    }

    /**
     * Used when a representation of this {@link Bug Bug's} internals must be
     * passed via XML-RPC for a remote method. Regular users of this API should
     * prefer the normal {@code getXxx()} methods.
     *
     * @return A read-only {@code Map} of key-value pairs corresponding to this
     *         {@code Bug's} properties.
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
