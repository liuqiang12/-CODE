package utils.typeHelper;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class CustomStringEditor extends PropertyEditorSupport implements PropertyEditor {
	public void setAsText(String text)
	  {
	    if (StringUtils.isNotEmpty(text)) {
	      if (text.contains("\"") && !text.contains("[{")) {
	        super.setValue(HtmlUtils.htmlEscape(text));
	      }
	      else {
	        super.setValue(text);
	      }
	    }
	    else
	      super.setValue(null);
	  }

	  public String getAsText()
	  {
	    Object value = getValue();
	    return value != null ? value.toString() : "";
	  }
}
