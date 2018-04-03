package system.view;

import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.File;

/**
 * Created by mylove on 2016/12/14.
 */
public class MyUrlBasedViewResolver extends InternalResourceViewResolver {

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AbstractUrlBasedView view = (AbstractUrlBasedView) BeanUtils.instantiateClass(getViewClass());
        String url = getPrefix() + viewName + getSuffix();
        File file = new File(getWebApplicationContext().getServletContext().getRealPath("/")+url);
        if (file.exists()) {
            if (logger.isDebugEnabled()) {
                logger.debug("在web-info下搜寻文件"+viewName + getSuffix());
            }
            view.setUrl(getPrefix() + viewName + getSuffix());
        } else {
            if (logger.isDebugEnabled()) {
              logger.debug("在jar中搜寻文件"+viewName + getSuffix());
            }
            view.setUrl(getPrefix().replace("/WEB-INF/", "/") + viewName + getSuffix());
        }
        String contentType = getContentType();
        if (contentType != null) {
            view.setContentType(contentType);
        }

        view.setRequestContextAttribute(getRequestContextAttribute());
        view.setAttributesMap(getAttributesMap());

        Boolean exposePathVariables = getExposePathVariables();
        if (exposePathVariables != null) {
            view.setExposePathVariables(exposePathVariables);
        }
        Boolean exposeContextBeansAsAttributes = getExposeContextBeansAsAttributes();
        if (exposeContextBeansAsAttributes != null) {
            view.setExposeContextBeansAsAttributes(exposeContextBeansAsAttributes);
        }
        String[] exposedContextBeanNames = getExposedContextBeanNames();
        if (exposedContextBeanNames != null) {
            view.setExposedContextBeanNames(exposedContextBeanNames);
        }

        return view;
    }
}
