package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONDataBuilder<T>
{
    public T buildData(JSONObject jsonObject) throws JSONException;
}
