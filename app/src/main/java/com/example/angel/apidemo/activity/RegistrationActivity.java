package com.example.angel.apidemo.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.angel.apidemo.APIConfiguration.ApiConfiguration;
import com.example.angel.apidemo.HttpRequestProcessor.HttpRequestProcessor;
import com.example.angel.apidemo.HttpRequestProcessor.Response;
import com.example.angel.apidemo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    private EditText edtName, edtAddress, edtEmailID, edtPhone, edtUserName, edtPassword;
    private String Name, Address, EmailId, MobileNo, UserName, Password;
    private Button btnRegister;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister;
    private String jsonPostString, jsonResponseString;
    private int responseData;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //findViewByID
        edtName = (EditText) findViewById(R.id.name);
        edtAddress = (EditText) findViewById(R.id.address);
        edtEmailID = (EditText) findViewById(R.id.email);
        edtPhone = (EditText) findViewById(R.id.mobileNo);
        edtUserName = (EditText) findViewById(R.id.userName);
        edtPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "AccountAPI/SaveApplicationUser";
        Log.e("url",urlRegister);

        //On clicking register Button

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting values
                Name = edtName.getText().toString();
                Address = edtAddress.getText().toString();
                EmailId = edtEmailID.getText().toString();
                MobileNo = edtPhone.getText().toString();
                UserName = edtUserName.getText().toString();
                Password = edtPassword.getText().toString();
                new RegistrationTask().execute(Name, Address, EmailId, MobileNo, UserName, Password);
                // Toast.makeText(getBaseContext(),"Data Fetched",Toast.LENGTH_LONG).show();;
                //  Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                // startActivity(intent);
            }
        });
    }

    private class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Name = params[0];
            Log.e("Name", Name);
            Address = params[1];
            Log.e("Address", Address);
            EmailId = params[2];
            MobileNo = params[3];
            UserName = params[4];
            Password = params[5];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", Name);
                jsonObject.put("Address", Address);
                jsonObject.put("EmailId", EmailId);
                jsonObject.put("MobileNo", MobileNo);
                jsonObject.put("Gender", "M");
                jsonObject.put("DateOfBirth", "2011-10-04");
                jsonObject.put("FatherName", "ABC ");
                jsonObject.put("MotherName", "xyz ");
                jsonObject.put("UserName", UserName);
                jsonObject.put("Password", Password);
                jsonObject.put("CreatedBy", "1");
                jsonObject.put("ModifiedBy", "1");


                jsonPostString = jsonObject.toString();
                Log.e("jsonPostString", jsonPostString );
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlRegister);
                jsonResponseString = response.getJsonResponseString();
                Log.e("jsonResponseString",jsonResponseString);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));

                responseData = jsonObject.getInt("responseData");
                // Log.d("message", message);

                if (responseData == 1) {
                    Toast.makeText(RegistrationActivity.this, "Data Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegistrationActivity.this, "Registration Unsuccessful", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
