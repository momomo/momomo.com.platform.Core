package momomo.com.sources;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Since SimpleDateFormat is not synchronize and unsafe to use in a concurrent environemnt you are often forced to create multiple instances, unless you provide a thread safe one.
 *
 * @author Joseph S.
 */
public class SimpleDateFormatSynchronized {
    private final SimpleDateFormat delegate;
    
    /////////////////////////////////////////////////////////////////////
    public SimpleDateFormatSynchronized() {
        this.delegate = new SimpleDateFormat();
    }
    public SimpleDateFormatSynchronized(String pattern) {
        delegate = new SimpleDateFormat(pattern);
    }
    public SimpleDateFormatSynchronized(String pattern, Locale locale) {
        this.delegate = new SimpleDateFormat(pattern, locale);
    }
    public SimpleDateFormatSynchronized(String pattern, DateFormatSymbols formatSymbols) {
        this.delegate = new SimpleDateFormat(pattern, formatSymbols);
    }
    /////////////////////////////////////////////////////////////////////
    
    public synchronized void set2DigitYearStart(Date startDate) {
        delegate.set2DigitYearStart(startDate);
    }
    
    public synchronized void setNumberFormat(NumberFormat newNumberFormat) {
        delegate.setNumberFormat(newNumberFormat);
    }
    
    public synchronized void setCalendar(Calendar newCalendar) {
        delegate.setCalendar(newCalendar);
    }
    
    public synchronized void applyPattern(String pattern) {
        delegate.applyPattern(pattern);
    }
    
    public synchronized StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
        return delegate.format(date, toAppendTo, pos);
    }
    
    public synchronized static DateFormat getTimeInstance(int style) {
        return DateFormat.getTimeInstance(style);
    }
    
    public synchronized static Locale[] getAvailableLocales() {
        return DateFormat.getAvailableLocales();
    }
    
    public synchronized void applyLocalizedPattern(String pattern) {
        delegate.applyLocalizedPattern(pattern);
    }
    
    public synchronized static DateFormat getTimeInstance(int style, Locale aLocale) {
        return DateFormat.getTimeInstance(style, aLocale);
    }
    
    public synchronized String format(Date date) {
        return delegate.format(date);
    }
    
    public synchronized static DateFormat getDateInstance() {
        return DateFormat.getDateInstance();
    }
    
    public synchronized StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return delegate.format(obj, toAppendTo, fieldPosition);
    }
    
    public synchronized void setLenient(boolean lenient) {
        delegate.setLenient(lenient);
    }
    
    public synchronized Object parseObject(String source) throws ParseException {
        return delegate.parseObject(source);
    }
    
    public synchronized DateFormatSymbols getDateFormatSymbols() {
        return delegate.getDateFormatSymbols();
    }
    
    public synchronized Object parseObject(String source, ParsePosition pos) {
        return delegate.parseObject(source, pos);
    }
    
    public synchronized static DateFormat getDateTimeInstance() {
        return DateFormat.getDateTimeInstance();
    }
    
    public synchronized AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        return delegate.formatToCharacterIterator(obj);
    }
    
    public synchronized void setDateFormatSymbols(DateFormatSymbols newFormatSymbols) {
        delegate.setDateFormatSymbols(newFormatSymbols);
    }
    
    public synchronized String toPattern() {
        return delegate.toPattern();
    }
    
    public synchronized TimeZone getTimeZone() {
        return delegate.getTimeZone();
    }
    
    public synchronized static DateFormat getInstance() {
        return DateFormat.getInstance();
    }
    
    public synchronized static DateFormat getDateTimeInstance(int dateStyle, int timeStyle) {
        return DateFormat.getDateTimeInstance(dateStyle, timeStyle);
    }
    
    public synchronized static DateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale aLocale) {
        return DateFormat.getDateTimeInstance(dateStyle, timeStyle, aLocale);
    }
    
    public synchronized Calendar getCalendar() {
        return delegate.getCalendar();
    }
    
    public synchronized Date parse(String source) throws ParseException {
        return delegate.parse(source);
    }
    
    public synchronized static DateFormat getDateInstance(int style, Locale aLocale) {
        return DateFormat.getDateInstance(style, aLocale);
    }
    
    public synchronized static DateFormat getDateInstance(int style) {
        return DateFormat.getDateInstance(style);
    }
    
    public synchronized NumberFormat getNumberFormat() {
        return delegate.getNumberFormat();
    }
    
    public synchronized Date get2DigitYearStart() {
        return delegate.get2DigitYearStart();
    }
    
    public synchronized void setTimeZone(TimeZone zone) {
        delegate.setTimeZone(zone);
    }
    
    public synchronized String format(Object obj) {
        return delegate.format(obj);
    }
    
    public synchronized Date parse(String text, ParsePosition pos) {
        return delegate.parse(text, pos);
    }
    
    public synchronized static DateFormat getTimeInstance() {
        return DateFormat.getTimeInstance();
    }
    
    public synchronized boolean isLenient() {
        return delegate.isLenient();
    }
    
    public synchronized String toLocalizedPattern() {
        return delegate.toLocalizedPattern();
    }
}
