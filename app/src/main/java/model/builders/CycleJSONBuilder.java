package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

import model.data.Cycle;

public class CycleJSONBuilder implements JSONDataBuilder<Cycle>
{
    @Override
    public Cycle buildData(JSONObject jsonObject) throws JSONException
    {
        return new Cycle(jsonObject.getInt("id"), jsonObject.getString("name"));
    }
}
