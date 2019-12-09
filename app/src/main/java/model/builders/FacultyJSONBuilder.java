package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

import model.data.Faculty;

public class FacultyJSONBuilder implements JSONDataBuilder<Faculty>
{
    @Override
    public Faculty buildData(JSONObject jsonObject) throws JSONException
    {
        return new Faculty(jsonObject.getInt("id"), jsonObject.getString("name"));
    }
}
