package model.network;

public class ServerException extends Exception
{
    private Integer errorCode;

    public ServerException(Integer errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode()
    {
        return errorCode;
    }
}
