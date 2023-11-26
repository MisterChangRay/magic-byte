package com.github.misterchangray.core.util;

import ognl.*;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public class OGNLUtil {
    static OgnlContext context = (OgnlContext) Ognl.createDefaultContext(null,
            new DefaultMemberAccess(true),
            new DefaultClassResolver(),
            new DefaultTypeConverter());



    /**
     * 执行ognl表达式
     * @param self
     * @param op 1pack 2unpack
     * @param script
     * @return
     */
    public static Object eval(Object self, Object root, int op, String script) {

        context.clear();
        context.setRoot(self);
        context.put("op", op);
        context.put("base", root);


        Object expression = null;
        try {
            expression = Ognl.parseExpression(script);
            Object result = Ognl.getValue(expression, context, context.getRoot());

            return result;
        } catch (OgnlException e) {
            throw new RuntimeException(e);
        }
    }

    static class DefaultMemberAccess implements MemberAccess {
        private boolean allowPrivateAccess = false;
        private boolean allowProtectedAccess = false;
        private boolean allowPackageProtectedAccess = false;

        public DefaultMemberAccess(boolean allowAllAccess) {
            this(allowAllAccess, allowAllAccess, allowAllAccess);
        }

        public DefaultMemberAccess(boolean allowPrivateAccess, boolean allowProtectedAccess,
                                   boolean allowPackageProtectedAccess) {
            super();
            this.allowPrivateAccess = allowPrivateAccess;
            this.allowProtectedAccess = allowProtectedAccess;
            this.allowPackageProtectedAccess = allowPackageProtectedAccess;
        }

        @Override
        public Object setup(OgnlContext context, Object target, Member member, String propertyName) {
            Object result = null;

            if (isAccessible(context, target, member, propertyName)) {
                AccessibleObject accessible = (AccessibleObject) member;

                if (!accessible.isAccessible()) {
                    result = Boolean.TRUE;
                    accessible.setAccessible(true);
                }
            }
            return result;
        }

        @Override
        public void restore(OgnlContext context, Object target, Member member, String propertyName, Object state) {
            if (state != null) {
                ((AccessibleObject) member).setAccessible((Boolean) state);
            }
        }

        /**
         * Returns true if the given member is accessible or can be made accessible by this object.
         */
        @Override
        public boolean isAccessible(OgnlContext context, Object target, Member member, String propertyName) {
            int modifiers = member.getModifiers();
            if (Modifier.isPublic(modifiers)) {
                return true;
            } else if (Modifier.isPrivate(modifiers)) {
                return this.allowPrivateAccess;
            } else if (Modifier.isProtected(modifiers)) {
                return this.allowProtectedAccess;
            } else {
                return this.allowPackageProtectedAccess;
            }
        }
    }
}
