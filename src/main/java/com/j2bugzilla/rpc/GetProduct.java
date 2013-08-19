package com.j2bugzilla.rpc;

import java.util.HashMap;
import java.util.Map;

import com.j2bugzilla.base.BugzillaMethod;
import com.j2bugzilla.base.Product;
import com.j2bugzilla.base.factory.ProductFactory;

/**
 * Allows users to retrieve a specific product for which the ID or name is already known
 *
 * @author Tom
 */
public class GetProduct implements BugzillaMethod {

    /**
     * The method name for this {@link com.j2bugzilla.base.BugzillaMethod}
     */
    private static final String GET_PRODUCT = "Product.get";

    private Map<Object, Object> hash = new HashMap<Object, Object>();
    private Map<Object, Object> params = new HashMap<Object, Object>();

    /**
     * Creates a new {@link com.j2bugzilla.rpc.GetProduct} object to retrieve the {@code Product} specified
     * by the ID parameter
     *
     * @param id An {@code int} representing the ID of a product in the
     *           installation connected to
     */
    public GetProduct(final int id) {
        params.put("ids", id);
    }

    /**
     * Creates a new {@link com.j2bugzilla.rpc.GetProduct} object to retrieve the {@code Product} specified
     * by the name parameter
     *
     * @param id An {@code int} representing the name of a product in the
     *           installation connected to
     */
    public GetProduct(final String name) {
        params.put("names", name);
    }

    /**
     * {@inheritDoc}
     */
    public void setResultMap(final Map<Object, Object> hash) {
        this.hash = hash;
    }

    /**
     * {@inheritDoc}
     */
    public Map<Object, Object> getParameterMap() {
        return params;
    }

    /**
     * Retrieves the {@link com.j2bugzilla.base.Product} corresponding to the given
     * ID
     *
     * @return A {@code Product} matching the ID, or null if the returned hash does
     *         not contain a match
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Product getProduct() throws InstantiationException, IllegalAccessException {
        Product result = null;
        if (hash.containsKey("products")) {
            Object[] products = (Object[]) hash.get("products");
            if (products.length == 0) {
                return result; // early return if map is empty
            }

            for (Object o : products) {
                @SuppressWarnings("unchecked")
                Map<String, Object> productMap = (HashMap<String, Object>) o;
                result = new ProductFactory().createProduct(productMap);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public String getMethodName() {
        return GET_PRODUCT;
    }

}
