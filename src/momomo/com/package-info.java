package momomo.com;

/**
 * Classes that start with a dollar $ are done so usually to avoid having to use a different naming strategy since those names are usually 
 * already taken by the system, or by other very common libraries. To avoid having to resolve conflicts all the time, and retain easy naming schemes, 
 * the $ is often placed in front of the name that we intend to use. Sometimes a dollar might be used simply to remain consistent with other similarly named classes, 
 * and to allow for quicker code completion. Typing $ in the editor will often show you all those classes that might be relevant instanteounously and where it not for strong views
 * and the ugliness of it, as well as an extra 'unnecessary' character to it, and the somewhat difficult to type $ character on the keyboard, and the argument that this character 
 * is somehow reserved for compiled representation of inner classes we would use it for all our classes. So we compromise.
 * 
 * Conflicting and common class names such as: 
 * 
 * {@link momomo.com.collections}
 * @see java.lang.reflect.Array
 * @see java.sql.Array
 * @see java.util.ArrayList
 * 
 * {@link momomo.com}
 * @see java.util.Arrays
 * @see java.util.Base64
 * @see java.util.prefs.Base64
 * @see sun.jvm.hotspot.runtime.Bytes
 * @see com.google.common.primitives.Bytes
 * @see java.util.Collections
 * @see org.hibernate.cfg.Environment
 * @see java.util.concurrent.Executor
 * @see java.util.concurrent.Executors
 * @see com.google.common.collect.Lists
 * @see org.apache.commons.compress.utils.Lists
 * @see com.google.common.collect.Maps
 * @see java.util.Objects
 * @see com.google.common.base.Strings
 * 
 * Below we prefer to use without the $ since dollar in Intellij is often annoying to autocomplete regardless of others using the namespace. 
 * Strings  
 * Threads
 * Randoms
 * Runtimes
 * 
 **/