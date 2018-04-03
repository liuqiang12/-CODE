package modules.utils;

import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2017/6/22.
 */
public class CreateActTablesTest {
    private CreateActTables createActTables;
    @Before
    public void setUp(){
        createActTables = new CreateActTables();
    }
    @Test
    public void createActivityTable() throws Exception {
        /*createActTables.createActivityTable();*/
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        context.setVariable("count", factory.createValueExpression(1000, String.class));
        ValueExpression e = factory.createValueExpression(context, "${count == 1000}", boolean.class);
        System.out.println(e.getValue(context));
    }
    public static void main() throws  Exception{

    }
}