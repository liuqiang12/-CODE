package utils;

/**
 * Created by mylove on 2017/6/26.
 */
public interface BaseEnum<E extends Enum<?>, T> {
    public T getValue();
    public String getDisplayName();
}