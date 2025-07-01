package momomo.com;

import momomo.com.exceptions.$ReflectionException;
import momomo.com.exceptions.$RuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * Reflection operations that have so far made it to the Core module.  
 * 
 * @author Joseph S.
 */
@SuppressWarnings("unchecked") public class Reflects { private Reflects(){}
    private static final IdentityHashMap<Class<?>, ArrayList<Field>> FIELDS = new IdentityHashMap<>();
    
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
     * TODO cache this
     */
    public static <T> Class<T> getGenericClass(Object instance) {
        return getGenericClass(instance, 0);
    }
    
    public static <T> Class<T> getGenericClass(Object instance, int position) {
        Type[] types = getGenericClasses(instance);
        if ( types != null && position < types.length ) {
            return cast(
                types[position]
            );
        }
        return null;
    }
    
    public static Type[] getGenericClasses(Object instance) {
        return getParameterizedType(instance.getClass());
    }
    
    private static Type[] getParameterizedType(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if ( type != null ) {
            if (type instanceof ParameterizedType) {
                return ((ParameterizedType) type).getActualTypeArguments();
            } else {
                Type[] types = clazz.getGenericInterfaces();
                if ( types != null && types.length > 0 ) {
                    type = types[0];
                    if (type instanceof ParameterizedType) {
                        return ((ParameterizedType) type).getActualTypeArguments();
                    } else {
                        // Likely a lambda, we can't infer the generic types as for anonomoys inner classes.
                        // TODO: See this: jdk.internal.org.objectweb.asm.Type.getArgumentTypes(methodRef[2])[argumentIndex].getClassName();
                        return null;
                    }
                }
            }
        }
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
                return eachSuperClass(klass, (superKlass) -> {
                    return getField(superKlass, name);
                });
            }
        }
    }
    
    public static Field getFieldlocal(Class<?> klass, String propertyName) {
        try {
            return klass.getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            throw Ex.runtime(e);
        }
    }
    
    public static List<Field> getFields(Class<?> clazz) {
        ArrayList<Field> fields;
        if ( (fields= FIELDS.get(clazz)) == null || !Is.Production() ) {
            FIELDS.put(clazz, fields = new ArrayList<Field>());
            getFields(fields, clazz);
        }
        return fields;
    }
    
    private static void getFields(List<Field> fields, Class<?> clazz) {
        for (Field element : getFieldsLocal(clazz)) fields.add(element); // Likely slightly faster than Collections.addAll( fields, localFields(clazz) );
        
        Class<?> superclass;
        if ( !isObject(clazz) && (superclass = clazz.getSuperclass()) != null ) {
            getFields(fields, superclass);
        }
    }
    
    /**
     * Excludes inherited fields
     */
    public static Field[] getFieldsLocal(Class<?> klass) {
        return klass.getDeclaredFields();
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static <T> T getValue(Object instance, Field field) {
        if ( !field.canAccess(instance) ) {
            field.setAccessible(true);
        }
        
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new $RuntimeException(e);
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    // is ...
    /////////////////////////////////////////////////////////////////////
    
    public static boolean isObject(Class<?> klass) {
        return klass.equals(Object.class);
    }
    
    public static boolean isInstance(Object val) {
        return !isClass(val);
    }
    
    public static boolean isStatic(Field property) {
        return Modifier.isStatic(property.getModifiers());
    }
    
    public static boolean isInstance(Field property) {
        return !Modifier.isStatic(property.getModifiers());
    }
    
    public static boolean isStatic(Class klass) {
        return Modifier.isStatic(klass.getModifiers());
    }
    
    public static boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers());
    }
    
    public static boolean isPrivate(Method method) {
        return Modifier.isPrivate(method.getModifiers());
    }
    
    public static boolean isProtected(Method method) {
        return Modifier.isProtected(method.getModifiers());
    }
    
    public static boolean isPublic(Class<?> clazz) {
        return Modifier.isPublic(clazz.getModifiers());
    }
    
    public static boolean isPublic(int modifiers) {
        return Modifier.isPublic(modifiers);
    }
    
    public static boolean isPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }
    
    public static boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers());
    }
    
    public static boolean isClass(Object val) {
        return val instanceof Class;
    }
    
    /**
     * Is non static inner class?
     */
    public static boolean isInnerclass(Class klass) {
        return klass.getDeclaringClass() != null && !isStatic(klass);
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
}
