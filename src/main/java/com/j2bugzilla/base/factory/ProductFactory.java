package com.j2bugzilla.base.factory;

import java.util.HashMap;
import java.util.Map;

import com.j2bugzilla.base.Product;
import com.j2bugzilla.base.ProductMilestone;
import com.j2bugzilla.base.ProductRelease;

public class ProductFactory {
    /**
     * Private {@code Map} used to hold
     */
    private Map<String, Object> properties;

    /**
     * Creates a new {@link com.j2bugzilla.base.Bug} based off of the provided {@code Map} of
     * properties.
     *
     * @param properties A {@code Map<String, Object>} describing the internal
     *                   structure of a bug.
     * @return A new {@code Product} object.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Product createProduct(final Map<String, Object> properties) throws InstantiationException, IllegalAccessException {
        Map<String, Object> copy = new HashMap<String, Object>();
        for (String key : properties.keySet()) {
            copy.put(key, properties.get(key));
        }
        final Product product = new Product();
        product.setInternalState(copy);
        return product;
    }

    /**
     * Sets up this {@link ProductFactory} to produce a new {@link Product}. This method
     * must be called before any {@code setXxx()} methods or the
     * {@link #createProduct()} method.
     *
     * @return A reference to the original {@code ProductFactory}.
     */
    public ProductFactory newProduct() {
        if (properties != null) {
            throw new IllegalStateException("Already creating a new Product!");
        }
        properties = new HashMap<String, Object>();
        return this;
    }

    /**
     * Sets the name of the {@link Product} to be created by this
     * {@link ProductFactory}.
     *
     * @param name A unique name for this product.
     * @return A reference to the original {@code ProductFactory}.
     */
    public ProductFactory setName(final String name) {
        if (properties == null) {
            throw new IllegalStateException("Must call newProduct() first!");
        }
        properties.put("name", name);
        return this;
    }

    /**
     * Sets the longer description associated with the {@link Product} to be created
     * by this {@link ProductFactory}.
     *
     * @param description The description for the product.
     * @return A reference to the original {@code ProductFactory}.
     */
    public ProductFactory setDescription(final String description) {
        if (properties == null) {
            throw new IllegalStateException("Must call newProduct() first!");
        }
        properties.put("description", description);
        return this;
    }

    /**
     * Sets the classification associated with the {@link Product} to be created
     * by this {@link ProductFactory}.
     *
     * @param classification The classification for the product.
     * @return A reference to the original {@code ProductFactory}.
     */
    public ProductFactory setClassification(final String classification) {
        if (properties == null) {
            throw new IllegalStateException("Must call newProduct() first!");
        }
        properties.put("classification", classification);
        return this;
    }

    /**
     * Sets the Default Milestone associated with the {@link Product} to be created
     * by this {@link ProductFactory}.
     *
     * @param milestone The default milestone for the product.
     * @return A reference to the original {@code ProductFactory}.
     */
    public ProductFactory setDefaultMilestone(final ProductMilestone milestone) {
        if (properties == null) {
            throw new IllegalStateException("Must call newProduct() first!");
        }
        properties.put("default_milestone", milestone.getName());
        return this;
    }

    /**
     * Sets the Default Release associated with the {@link Product} to be created
     * by this {@link ProductFactory}.
     *
     * @param release The default release for the product.
     * @return A reference to the original {@code ProductFactory}.
     */
    public ProductFactory setDefaultRelease(final ProductRelease release) {
        if (properties == null) {
            throw new IllegalStateException("Must call newProduct() first!");
        }
        properties.put("default_release", release.getName());
        return this;
    }

    /**
     * Creates a new {@link Product} using the properties set in this
     * {@link ProductFactory}. This method must be called after {@link #newProduct()}.
     *
     * @return A new {@code Product} object.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Product createProduct() throws InstantiationException, IllegalAccessException {
        if (properties == null) {
            throw new IllegalStateException("Must call newProduct() first!");
        }

        final Product product = new Product();
        product.setInternalState(properties);
        properties = null;
        return product;
    }
}
