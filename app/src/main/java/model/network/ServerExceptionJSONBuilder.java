package model.network;

import org.json.JSONException;
import org.json.JSONObject;

import model.builders.JSONDataBuilder;

public class ServerExceptionJSONBuilder implements JSONDataBuilder<ServerException>
{
    @Override
    public ServerException buildData(JSONObject jsonObject) throws JSONException
    {
        return new ServerException(jsonObject.getInt("errorCode"),
                jsonObject.getString("message"));
    }
}
