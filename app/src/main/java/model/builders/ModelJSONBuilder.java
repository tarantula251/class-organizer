package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

import model.data.Model;

public class ModelJSONBuilder implements JSONDataBuilder<Model>
{
    @Override
    public Model buildData(JSONObject jsonObject) throws JSONException
    {
        return new Model(jsonObject.getInt("id"), jsonObject.getString("name"));
    }
}
