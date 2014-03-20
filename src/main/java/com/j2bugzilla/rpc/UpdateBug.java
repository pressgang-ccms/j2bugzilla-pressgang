package com.j2bugzilla.rpc;

import java.util.HashMap;
import java.util.Map;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugBase;
import com.j2bugzilla.base.BugzillaMethod;

/**
 * The {@code UpdateBug} class allows clients to update an existing {@link Bug}
 * on the installation with new values. Currently, this method only allows one
 * bug at a time to be updated.
 *
 * @author Tom
 */
public class UpdateBug implements BugzillaMethod {

    /**
     * The method name for this webservice operation.
     */
    private static final String METHOD_NAME = "Bug.update";

    /**
     * A {@link Bug} to update on the installation.
     */
    private final BugBase bug;
    private String updateComment;
    private boolean isCommentPrivate;

    /**
     * Creates a new {@link UpdateBug} object to submit to the Bugzilla
     * webservice. The {@link Bug} on the installation identified by the id or
     * alias of the bug provided will have its fields updated to match those of
     * the values in the provided bug.
     *
     * @param bug
     */
    public UpdateBug(final BugBase bug) {
        this.bug = bug;
        updateComment = null;
        isCommentPrivate = false;
    }

    /**
     * Creates a new {@link UpdateBug} object to submit to the Bugzilla
     * webservice. The {@link Bug} on the installation identified by the id or
     * alias of the bug provided will have its fields updated to match those of
     * the values in the provided bug.
     *
     * @param bug
     * @param comment
     * @param isPrivate
     */
    public UpdateBug(final BugBase bug, final String comment) {
        this.bug = bug;
        updateComment = comment;
        isCommentPrivate = false;
    }

    /**
     * Creates a new {@link UpdateBug} object to submit to the Bugzilla
     * webservice. The {@link Bug} on the installation identified by the id or
     * alias of the bug provided will have its fields updated to match those of
     * the values in the provided bug.
     *
     * @param bug
     * @param comment
     * @param isCommentPrivate
     */
    public UpdateBug(final BugBase bug, final String comment, final boolean isCommentPrivate) {
        this.bug = bug;
        updateComment = comment;
        this.isCommentPrivate = isCommentPrivate;
    }

    /**
     * {@inheritDoc}
     */
    public void setResultMap(final Map<Object, Object> hash) {
        Object[] modified = (Object[]) hash.get("bugs");
        // For now, we only modify one bug at a time, thus this array should be
        // a single element
        assert (modified.length == 1);
        // There aren't a ton of useful elements returned, so for now just
        // discard the map.
    }

    /**
     * {@inheritDoc}
     */
    public Map<Object, Object> getParameterMap() {
        Map<Object, Object> params = new HashMap<Object, Object>();

        Map<Object, Object> internals = bug.getParameterMap();

        params.put("ids", bug.getID());

        if (internals.get("assigned_to") != null)
            params.put("assigned_to", internals.get("assigned_to"));
        if (internals.get("component") != null)
            params.put("component", bug.getComponent());
        if (internals.get("op_sys") != null)
            params.put("op_sys", internals.get("op_sys"));
        if (internals.get("platform") != null)
            params.put("platform", internals.get("platform"));
        if (internals.get("priority") != null)
            params.put("priority", internals.get("priority"));
        if (internals.get("product") != null)
            params.put("product", internals.get("product"));
        if (internals.get("status") != null)
            params.put("status", internals.get("status"));
        if ("closed".equalsIgnoreCase(bug.getStatus()))
            params.put("resolution", internals.get("resolution"));
        if (internals.get("summary") != null)
            params.put("summary", internals.get("summary"));
        if (internals.get("version") != null)
            params.put("version", bug.getVersion());
        if (updateComment != null) {
            Map<String, Object> comment = new HashMap<String, Object>();
            comment.put("body", updateComment);
            comment.put("is_private", isCommentPrivate);
            params.put("comment", comment);
        }

        return params;
    }

    /**
     * {@inheritDoc}
     */
    public String getMethodName() {
        return METHOD_NAME;
    }

    public String getComment() {
        return updateComment;
    }

    public void setComment(String comment) {
        updateComment = comment;
    }

    public boolean isCommentPrivate() {
        return isCommentPrivate;
    }

    public void setCommentPrivate(boolean privateComment) {
        isCommentPrivate = privateComment;
    }
}
