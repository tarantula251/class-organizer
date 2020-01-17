package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.data.Class;
import model.data.Result;
import model.data.User;

public class ResultJSONBuilder implements JSONDataBuilder<Result>
{
    private Class classObject;
    private User user;

    public ResultJSONBuilder(Class classObject, User user)
    {
        this.classObject = classObject;
        this.user = user;
    }

    @Override
    public Result buildData(JSONObject jsonObject) throws JSONException
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new Result(jsonObject.getInt("id"),
                jsonObject.getDouble("score"),
                jsonObject.getString("title"),
                jsonObject.getString("note"),
                LocalDateTime.parse(jsonObject.getString("lastUpdate"), formatter),
                user,
                classObject);
    }
}
