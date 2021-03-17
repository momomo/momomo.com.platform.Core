package momomo.com;

import momomo.com.exceptions.$ReflectionException;
import momomo.com.exceptions.$RuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Reflection operations that have so far made it to the Core module.  
 * 
 * @author Joseph S.
 */
@SuppressWarnings("unchecked") public class Reflects { private Reflects(){}

    /////////////////////////////////////////////////////////////////////
    
    public static <R> R eachSuperClass(Class<?> clazz, Lambda.R1<R, Class<?>> lambda ) {
        // Perhaps it is in the supeclass?
        Class<?> superclass = clazz.getSuperclass();
        if ( superclass != null && !isObject(superclass) ) {
            return lambda.call(superclass);     // Recursively call this up to Object
        }
        return null;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static <C> Class<C> getClass(CharSequence name) {
        try {
            return (Class<C>) Class.forName(name.toString());
        } catch (ClassNotFoundException e) {
            throw Ex.runtime(e);
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Given the class invoke new, with the given arguments
     */
    public static <T>T newInstance(Class<T> clazz, Object... args) {
        if ( Is.Ok(args) ) {
            Class[] classes = new Class[args.length];
            
            int i = -1; while (++i < args.length) {
                classes[i] = args[i].getClass();
            }
    
            try {
                return clazz.getDeclaredConstructor(classes).newInstance(args);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw Ex.runtime(e);
            }
        }
        else {
            return newInstance(clazz);  // Args might be passed as a null or empty array
        }
    }
    
    public static <T>T newInstance(Class<T> clazz) {
        return Ex.runtime(clazz::newInstance);
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static <T>T cast(Object obj){
        return (T) obj;
    }
    
    public static <T> T castAt(int index, Object ... objs) {
        if ( objs.length > index )
            return cast(objs[index]);
        return null;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Any field. Will traverse the superclasses as well to get the method if needed, excluding Object.class
     * 
     * TODO cache this result
     */
    public static Field getField(Class<?> klass, String name) {
        try {
            return klass.getField(name);
        } catch (NoSuchFieldException e) {
            // If private, this exception will be thrown
            
            try {
                return getFieldlocal(klass, name);
                
            } catch ($ReflectionException ignore) {
                return eachSuperClass(klass, (superKlass)->{
                    return getField(superKlass, name);
                });
            }
        }
    }
    
    public static Field getFieldlocal(Class<?> clazz, String propertyName) {
        try {
            return clazz.getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            throw Ex.runtime(e);
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Optimized why redundancy is not a concern ...
     */
    public static Object getValue(Object instance, Field field) {
        if ( !field.isAccessible() ) {
            field.setAccessible(true);
        }
        
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            throw new $RuntimeException(e);
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static boolean isObject(Class<?> klass) {
        return klass.equals(Object.class);
    }
    
}
