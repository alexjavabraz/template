package br.com.bjbraz.app.estabelecimentos.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil {

    protected final static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * Retorna o nome da propriedade da classe <code>clazz</code> anotada com DefaultListOrder
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getOrderPropertyName(Class clazz) {
        return getPropertyNameForAnnotation(clazz, DefaultListOrder.class);
    }

    /**
     * Retorna o nome da primeira propriedade da classe <code>clazz</code> que está anotada com
     * <code>annotationClass</code>
     * 
     * @param clazz
     * @param annotationClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getPropertyNameForAnnotation(Class clazz, Class<? extends Annotation> annotationClass) {
        logger.debug("procurando anotacao {} nos campos da classe {}", annotationClass.getName(), clazz.getName());
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(annotationClass)) {
                return f.getName();
            }
        }
        return null;
    }

    private BeanUtilsBean beanUtil;

    public static ReflectionUtil getInstance() {
        ReflectionUtil ru = new ReflectionUtil();
        ru.beanUtil = BeanUtilsBean.getInstance();
        return ru;
    }

    /**
     * Pega o valor da propriedade <code>idName</code> no objeto
     * <code>last</code>. incrementa 1 unidade e seta esse valor na mesma propriedade do objeto
     * <code>newObject</code>
     * 
     * @param newObject
     * @param last
     * @param idName 
     */
    public void setNextIdValue(Object newObject, Object last, String idName) {
        if (newObject == null) {
            throw new NullPointerException("newObject cannot be null.");
        }
        if (last == null) {
            throw new NullPointerException("last cannot be null.");
        }
        if (idName == null) {
            throw new NullPointerException("idName cannot be null.");
        }
        try {
            String lastId = getPropertyValue(last, idName);
            if (lastId == null) {
                throw new NumberFormatException("id da classe " + newObject.getClass() + " is null");
            }
            long newValue = Long.parseLong(lastId);
            newValue++;

            logger.debug("alterando valor da propriedade {} do objeto {} para {}", new Object[] { idName, newObject, newValue });
            setProperty(newObject, idName, newValue);
        }
        catch (NumberFormatException e) {
            logger.error("erro na formatacao do valor do campo " + idName, e);
        }
    }

    public String getPropertyValue(Object objeto, String idName) {
        logger.debug("buscando valor da propriedade {} do objeto {}", idName, objeto);
        String lastId = null;
        try {
            lastId = beanUtil.getProperty(objeto, idName);
        }
        catch (IllegalAccessException e) {
            logger.error("erro ao acessar getter/setter da propriedade " + idName + " da classe "
                + objeto.getClass().getName(), e);
        }
        catch (InvocationTargetException e) {
            logger.error("erro ao acessar getter/setter da propriedade " + idName + " da classe "
                + objeto.getClass().getName(), e);
        }
        catch (NoSuchMethodException e) {
            logger.error("erro ao acessar getter/setter da propriedade " + idName + " da classe "
                + objeto.getClass().getName(), e);
        }
        return lastId;
    }

    public void setProperty(Object newObject, String id, Number newValue) {
        try {
            this.beanUtil.setProperty(newObject, id, newValue);
        }
        catch (IllegalAccessException e) {
            logger.error("erro ao acessar getter/setter da propriedade " + id + " da classe "
                + newObject.getClass().getName(), e);
        }
        catch (InvocationTargetException e) {
            logger.error("erro ao acessar getter/setter da propriedade " + id + " da classe "
                + newObject.getClass().getName(), e);
        }
    }

}
