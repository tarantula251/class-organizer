package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

import model.data.Cycle;
import model.data.Faculty;
import model.data.Field;
import model.data.Model;

public class FieldJSONBuilder implements JSONDataBuilder<Field>
{
    private Faculty faculty;
    private Model model;
    private Cycle cycle;

    public FieldJSONBuilder(Faculty faculty, Model model, Cycle cycle)
    {
        this.faculty = faculty;
        this.model = model;
        this.cycle = cycle;
    }

    @Override
    public Field buildData(JSONObject jsonObject) throws JSONException
    {
        return new Field(jsonObject.getInt("id"),
                jsonObject.getString("name"),
                faculty,
                model,
                cycle);
    }
}
