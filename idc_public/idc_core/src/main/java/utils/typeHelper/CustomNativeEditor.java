package utils.typeHelper;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

public class CustomNativeEditor extends PropertyEditorSupport {
	private final Class numberClass;
	  private final NumberFormat numberFormat;
	  private final boolean allowEmpty;

	  public CustomNativeEditor(Class numberClass, boolean allowEmpty)
	    throws IllegalArgumentException
	  {
	    this(numberClass, null, allowEmpty);
	  }

	  public CustomNativeEditor(Class numberClass, NumberFormat numberFormat, boolean allowEmpty)
	    throws IllegalArgumentException
	  {
	    if ((numberClass == null) || (!Number.class.isAssignableFrom(numberClass))) {
	      throw new IllegalArgumentException(
	        "Property class must be a subclass of Number");
	    }
	    this.numberClass = numberClass;
	    this.numberFormat = numberFormat;
	    this.allowEmpty = allowEmpty;
	  }

	  public void setAsText(String text)
	    throws IllegalArgumentException
	  {
	    if ((this.allowEmpty) && (!StringUtils.hasText(text)))
	    {
	      setValue(Integer.valueOf(0));
	    } else if (this.numberFormat != null)
	    {
	      setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
	    }
	    else
	      setValue(NumberUtils.parseNumber(text, this.numberClass));
	  }

	  public void setValue(Object value)
	  {
	    if ((value instanceof Number))
	      super.setValue(NumberUtils.convertNumberToTargetClass(
	        (Number)value, this.numberClass));
	    else
	      super.setValue(value);
	  }

	  public String getAsText()
	  {
	    Object value = getValue();
	    if (value == null) {
	      return "";
	    }
	    if (this.numberFormat != null)
	    {
	      return this.numberFormat.format(value);
	    }

	    return value.toString();
	  }
}
