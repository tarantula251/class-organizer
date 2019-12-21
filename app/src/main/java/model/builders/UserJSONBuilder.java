package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

import model.data.User;
import model.data.UserType;

public class UserJSONBuilder implements JSONDataBuilder<User>
{
    @Override
    public User buildData(JSONObject jsonObject) throws JSONException
    {

        UserType userType = new UserType(jsonObject.getJSONObject("userType").getInt("id"),
                jsonObject.getJSONObject("userType").getString("name"));
        return new User(jsonObject.getInt("id"),
                jsonObject.getString("firstName"),
                jsonObject.getString("lastName"),
                jsonObject.getString("email"),
                userType);
    }
}
