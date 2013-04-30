package com.j2bugzilla.rpc;

import java.util.HashMap;
import java.util.Map;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugBase;
import com.j2bugzilla.base.BugzillaMethod;
import com.j2bugzilla.base.Comment;

/**
 * The <code>CommentBug</code> class allows clients to add a new comment to an
 * existing {@link Bug} in a Bugzilla installation.
 *
 * @author Tom
 */
public class CommentBug implements BugzillaMethod {

    /**
     * The method Bugzilla will execute via XML-RPC
     */
    private static final String METHOD_NAME = "Bug.add_comment";

    private Map<Object, Object> params = new HashMap<Object, Object>();
    private Map<Object, Object> hash = new HashMap<Object, Object>();

    /**
     * Creates a new {@link CommentBug} object to add a comment to an existing
     * {@link Bug} in the client's installation
     *
     * @param bug     A <code>Bug</code> to comment on
     * @param comment The comment to append
     */
    public CommentBug(final BugBase bug, final String comment) {
        this(bug.getID(), comment);
    }

    /**
     * Creates a new {@link CommentBug} object to add a {@link Comment} to an
     * existing {@link Bug} in the client's installation
     *
     * @param bug     A {@code Bug} to comment on
     * @param comment A {@code Comment} to append
     */
    public CommentBug(final BugBase bug, final Comment comment) {
        this(bug.getID(), comment.getText());
    }

    /**
     * Creates a new {@link CommentBug} object to add a {@link Comment} to an
     * existing {@link Bug} in the client's installation
     *
     * @param id      The integer ID of an existing {@code Bug}
     * @param comment A {@code Comment} to append
     */
    public CommentBug(final int id, final Comment comment) {
        this(id, comment.getText());
    }

    /**
     * Creates a new {@link CommentBug} object to add a comment to an existing
     * {@link Bug} in the client's installation
     *
     * @param id      The integer ID of an existing <code>Bug</code>
     * @param comment The comment to append
     */
    public CommentBug(final int id, final String comment) {
        params.put("id", id);
        params.put("comment", comment);
    }

    /**
     * Returns the ID of the newly appended comment
     *
     * @return An <code>int</code> representing the ID of the new comment
     */
    public int getCommentID() {
        if (hash.containsKey("id")) {
            Integer i = (Integer) hash.get("id");
            return i.intValue();
        } else {
            return -1;
        }
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
     * {@inheritDoc}
     */
    public String getMethodName() {
        return METHOD_NAME;
    }

}
