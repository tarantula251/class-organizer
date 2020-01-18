package com.example.classorganizer;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import model.builders.UserJSONBuilder;
import model.data.User;
import model.network.Server;


public class AuthorizedUserBeam extends Activity implements CreateNdefMessageCallback {
    NfcAdapter nfcAdapter;

    private ArrayList<Pair<User, Date>> attendanceList;
    private static AuthorizedUserBeam instance;

    public static AuthorizedUserBeam getInstance()
    {
        return instance;
    }

    public ArrayList<Pair<User, Date>> getAttendanceList()
    {
        return attendanceList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.authorized_user_beam);
        attendanceList = new ArrayList<>();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Toast.makeText(this, "NFC activated", Toast.LENGTH_LONG).show();

        nfcAdapter.setNdefPushMessageCallback(this, this);
        finish();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        JSONObject userJson = new JSONObject();
        JSONObject userTypeJson = new JSONObject();
        User user = Server.getInstance().getAuthorizedUser();
        try
        {
            userTypeJson.put("id", user.getType().getId());
            userTypeJson.put("name", user.getType().getName());

            userJson.put("id", user.getId());
            userJson.put("firstName", user.getFirstName());
            userJson.put("lastName", user.getLastName());
            userJson.put("email", user.getEmail());
            userJson.put("userType", userTypeJson);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        String text = userJson.toString();
        return new NdefMessage(
                new NdefRecord[] { NdefRecord.createMime("application/vnd.com.example.android.beam", text.getBytes())
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        String beamedContent = new String(msg.getRecords()[0].getPayload());
        UserJSONBuilder userJSONBuilder = new UserJSONBuilder();
        try
        {
            User user = userJSONBuilder.buildData(new JSONObject(beamedContent));
            attendanceList.add(new Pair<>(user, new Date()));
        }
        catch(JSONException e)
        {
            Toast.makeText(this, "Unable to parse received data!", Toast.LENGTH_LONG);
        }
    }
}
