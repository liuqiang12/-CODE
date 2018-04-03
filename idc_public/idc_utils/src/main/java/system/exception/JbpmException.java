package system.exception;

/**
 * Created by DELL on 2017/9/7.
 */
public class JbpmException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public JbpmException()
    {
    }

    public JbpmException(String paramString)
    {
        super(paramString);
    }

    public JbpmException(Throwable paramThrowable)
    {
        super(paramThrowable);
    }

    public JbpmException(String paramString, Throwable paramThrowable)
    {
        super(paramString, paramThrowable);
    }
}
