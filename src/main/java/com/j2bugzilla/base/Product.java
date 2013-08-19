package com.j2bugzilla.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class encapsulates all information about a product on a
 * Bugzilla installation. It provides getter methods for various properties,
 * such as the bug summary and status. To obtain a new {@code Product} object, you
 * must use the {@link com.j2bugzilla.base.factory.ProductFactory} class which provides a fluent interface for
 * product creation.
 *
 * @author Lee Newson
 */
public class Product {
    /**
     * HashMap representing fields for each Product. The Value for each Key is a
     * <code>String</code> <em>except</em> for the versions, releases, milestones and components field, which is an array
     * of <code>HashMaps</code>.
     */
    private Map<String, Object> internalState;

    /**
     * Constructor for creating a new {@link Product}
     */
    public Product() {

    }

    /**
     * Constructor for creating a new {@link Product} to submit to an installation.
     *
     * @param state A <code>Map</code> pairing required keys to values
     */
    public Product(final Map<String, Object> state) {
        setInternalState(state);
    }

    /**
     * Returns the internal Bugzilla ID number for this product. If it is not in the
     * Bugzilla database, this will return null.
     *
     * @return The product ID
     */
    public Integer getID() {
        return (Integer) getInternalState().get("id");
    }

    /**
     * Returns the internal Bugzilla Name for this Product. If it is not in the
     * Bugzilla database, this will return null.
     *
     * @return The product name
     */
    public String getName() {
        return (String) getInternalState().get("name");
    }

    /**
     * Sets the name of this {@link Product}.
     *
     * @param name A {@code String} representing a unique name for this product.
     */
    public void setName(final String name) {
        getInternalState().put("name", name);
    }

    /**
     * Returns the internal Bugzilla Description for this Product. If it is not in the
     * Bugzilla database, this will return null.
     *
     * @return The product description
     */
    public String getDescription() {
        return (String) getInternalState().get("description");
    }

    /**
     * Sets the description of this {@link Product}.
     *
     * @param description A {@code String} representing a description for this product.
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

    public List<ProductVersion> getVersions() {
        final Object[] versions = (Object[]) getInternalState().get("versions");
        if (versions == null) {
            return null;
        } else {
            final ArrayList<ProductVersion> productVersions = new ArrayList<ProductVersion>();

            for (final Object o : versions) {
                final HashMap<String, Object> versionMap = (HashMap<String, Object>) o;
                final ProductVersion version = new ProductVersion(versionMap);
                productVersions.add(version);
            }

            return productVersions;
        }
    }

    public List<ProductComponent> getComponents() {
        final Object[] components = (Object[]) getInternalState().get("components");
        if (components == null) {
            return null;
        } else {
            final ArrayList<ProductComponent> productComponents = new ArrayList<ProductComponent>();

            for (final Object o : components) {
                final HashMap<String, Object> componentMap = (HashMap<String, Object>) o;
                final ProductComponent component = new ProductComponent(componentMap);
                productComponents.add(component);
            }

            return productComponents;
        }
    }

    public List<ProductRelease> getReleases() {
        final Object[] releases = (Object[]) getInternalState().get("releases");
        if (releases == null) {
            return null;
        } else {
            final ArrayList<ProductRelease> productReleases = new ArrayList<ProductRelease>();

            for (final Object o : releases) {
                final HashMap<String, Object> releaseMap = (HashMap<String, Object>) o;
                final ProductRelease release = new ProductRelease(releaseMap);
                productReleases.add(release);
            }

            return productReleases;
        }
    }

    public List<ProductMilestone> getMilestones() {
        final Object[] milestones = (Object[]) getInternalState().get("milestones");
        if (milestones == null) {
            return null;
        } else {
            final ArrayList<ProductMilestone> productMilestones = new ArrayList<ProductMilestone>();

            for (final Object o : milestones) {
                final HashMap<String, Object> milestoneMap = (HashMap<String, Object>) o;
                final ProductMilestone milestone = new ProductMilestone(milestoneMap);
                productMilestones.add(milestone);
            }

            return productMilestones;
        }
    }

    /**
     * Used when a representation of this {@link Product Product's} internals must be
     * passed via XML-RPC for a remote method. Regular users of this API should
     * prefer the normal {@code getXxx()} methods.
     *
     * @return A read-only {@code Map} of key-value pairs corresponding to this
     *         {@code Product's} properties.
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
