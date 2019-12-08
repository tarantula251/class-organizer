package model.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import model.data.Cycle;
import model.data.Faculty;
import model.data.Field;
import model.data.Model;

public class Server
{
    private Socket socket;
    private BufferedReader inputBuffer;
    private DataOutputStream dataOutputStream;
    private PrintWriter outputWriter;

    //Message codes
    private static final byte fieldGetCode = (byte)100;
    private static final byte facultyGetAllCode = (byte)200;
    private static final byte modelGetAllCode = (byte)201;
    private static final byte cycleGetAllCode = (byte)202;

    public Server(String ipAddress, int port) throws IOException
    {
        socket = new Socket(ipAddress, port);
        inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        outputWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    private void sendMessage(byte messageCode, String message) throws IOException
    {
        dataOutputStream.write(messageCode);
        dataOutputStream.flush();
        outputWriter.println(message);
    }

    private String readResponse() throws IOException
    {
        return inputBuffer.readLine();
    }

    public ArrayList<Faculty> getFaculties()
    {
        ArrayList<Faculty> result = new ArrayList<>();
        String response;

        try
        {
            sendMessage(facultyGetAllCode, "");
            response = readResponse();

        }
        catch(IOException exception)
        {
            return null;
        }
        if(response == null)
        {
            return null;
        }

        try
        {
            JSONArray facultiesArray = new JSONArray(response);
            for(int index = 0; index < facultiesArray.length(); ++index)
            {
                JSONObject facultyJsonObject = facultiesArray.getJSONObject(index);

                result.add(new Faculty(facultyJsonObject.getInt("id"),
                        facultyJsonObject.getString("name")));
            }
        }
        catch(JSONException exception)
        {
            return null;
        }

        return result;
    }

    public ArrayList<Model> getModels()
    {
        ArrayList<Model> result = new ArrayList<>();
        String response;

        try
        {
            sendMessage(modelGetAllCode, "");
            response = readResponse();

        }
        catch(IOException exception)
        {
            return null;
        }
        if(response == null)
        {
            return null;
        }

        try
        {
            JSONArray modelsArray = new JSONArray(response);
            for(int index = 0; index < modelsArray.length(); ++index)
            {
                JSONObject modelJsonObject = modelsArray.getJSONObject(index);

                result.add(new Model(modelJsonObject.getInt("id"),
                        modelJsonObject.getString("name")));
            }
        }
        catch(JSONException exception)
        {
            return null;
        }

        return result;
    }

    public ArrayList<Cycle> getCycles()
    {
        ArrayList<Cycle> result = new ArrayList<>();
        String response;

        try
        {
            sendMessage(cycleGetAllCode, "");
            response = readResponse();

        }
        catch(IOException exception)
        {
            return null;
        }
        if(response == null)
        {
            return null;
        }

        try
        {
            JSONArray cyclesArray = new JSONArray(response);
            for(int index = 0; index < cyclesArray.length(); ++index)
            {
                JSONObject cycleJsonObject = cyclesArray.getJSONObject(index);

                result.add(new Cycle(cycleJsonObject.getInt("id"),
                        cycleJsonObject.getString("name")));
            }
        }
        catch(JSONException exception)
        {
            return null;
        }

        return result;
    }

    public ArrayList<Field> getFields(Faculty faculty, Model model, Cycle cycle)
    {
        ArrayList<Field> result = new ArrayList<>();
        JSONObject requestData = new JSONObject();
        String response;

        if(faculty == null || model == null || cycle == null) return null;

        try
        {
            requestData.put("faculty", faculty.getId());
            requestData.put("model", model.getId());
            requestData.put("cycle", cycle.getId());
        }
        catch(JSONException e)
        {
            return null;
        }

        try
        {
            sendMessage(fieldGetCode, requestData.toString());
            response = readResponse();
        }
        catch(IOException exception)
        {
            return null;
        }
        if(response == null)
        {
            return null;
        }

        try
        {
            JSONArray fieldsArray = new JSONArray(response);
            for(int index = 0; index < fieldsArray.length(); ++index)
            {
                JSONObject fieldJsonObject = fieldsArray.getJSONObject(index);

                result.add(new Field(fieldJsonObject.getInt("id"),
                        fieldJsonObject.getString("name"),
                        faculty,
                        model,
                        cycle));
            }
        }
        catch(JSONException exception)
        {
            return null;
        }

        return result;
    }
}
