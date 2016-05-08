package br.com.bjbraz.app.estabelecimentos.config.aop;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @version $VERSION
 */
public class Util {

    private static final int MAX_ARRAY_DISPLAY = 10;

    public static Logger getLogger(JoinPoint jp) {
        String fqcn = jp.getTarget().getClass().getName();
        return LoggerFactory.getLogger(fqcn);
    }

    public static String serviceName(ProceedingJoinPoint jp) {
        return jp.getTarget().getClass().getSimpleName();
    }

    public static String methodName(JoinPoint jp) {
        return jp.getSignature().getName();
    }

    public static String fullMethodName(JoinPoint jp) {
        return jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
    }

    public static String className(JoinPoint jp) {
        return jp.getTarget().getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public static Object getArgOfType(ProceedingJoinPoint jp, Class type) {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if (arg != null && arg.getClass().isAssignableFrom(type)) {
                return arg;
            }
        }

        throw new IllegalArgumentException(type.getName() + " not found in service call");
    }

    public static void appendArguments(Object[] args, StringBuilder sb) {
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (i > 0) {
                sb.append(',');
            }
            sb.append(displayObject(arg));
        }
    }

    public static String displayObject(Object arg) {
        if (arg == null) {
            return "<null>";
        }
        if (arg instanceof String || arg instanceof Character || arg instanceof Boolean || arg instanceof Number) {
            return arg.toString();
        }
        if (arg.getClass().isArray()) {
            int length = Array.getLength(arg);
            if (length > MAX_ARRAY_DISPLAY) {
                return "LargeArray[size=" + length + "]";
            }
        }
        if (hasToString(arg)) {
            return arg.toString();
        }
        return ToStringBuilder.reflectionToString(arg, lazyToStringStyle);
    }

    public static boolean hasToString(Object o) {
        try {
            Method m = o.getClass().getDeclaredMethod("toString");
            return m != null;
        }
        catch (NoSuchMethodException e) {
        }
        return false;
    }

    /**
     * Configura o ToStringStyle do commons-lang para detectar quando um objeto do Hibernate for lazy
     */
    private static class LazyToStringStyle extends ToStringStyle {
        private static final long serialVersionUID = -7880206790817261341L;

        private LazyToStringStyle() {
            super();
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
        }

        public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
            if (Hibernate.isInitialized(value)) {
                super.append(buffer, fieldName, value, fullDetail);
            }
            else {
                appendFieldStart(buffer, fieldName);
                buffer.append("<lazy>");
                appendFieldEnd(buffer, fieldName);
            }
        }
    }

    private static LazyToStringStyle lazyToStringStyle = new LazyToStringStyle();

    public static void main(String[] args) {

        System.out.println("" + String.class.getName());
        System.out.println("" + String.class.getSimpleName());

        int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
        System.out.println("x = " + x.toString());
        System.out.println("x = " + Arrays.asList(x).toString());
        //System.out.println("x = " + Arrays.deepToString(x).toString());
        System.out.println("x = " + ToStringBuilder.reflectionToString(x));
        System.out.println("x = " + ToStringBuilder.reflectionToString(x, lazyToStringStyle));

        int[] y = new int[MAX_ARRAY_DISPLAY];
        System.arraycopy(x, 0, y, 0, MAX_ARRAY_DISPLAY);
        System.out.println("y = " + ToStringBuilder.reflectionToString(y, lazyToStringStyle));
    }

}
