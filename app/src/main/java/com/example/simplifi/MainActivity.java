package com.example.simplifi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.cast.framework.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.jar.Attributes;

import javax.xml.transform.ErrorListener;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
//    private EditText Email;
    private TextView textView2;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.etName);
//        Email = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        textView2 = (TextView) findViewById(R.id.textView2);
        Login = (Button) findViewById(R.id.button);

        Login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
//                volleyPost();
//                try {
//                    register();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Regist();

                Response<String> resy = call();
                valiadate(resy);



            }
        });
    }

    private void valiadate(Response<String> resy) {
//        JSONObject json = (JSONObject) JSONSerializer.toJSON(resy);
//        String value = json.getJSONObject("LL").getString("value");
//        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
////        intent.putExtra("key", resy);
//        startActivity(intent);
    }
    private void valiadate(String name, String email, String token) {
//        JSONObject json = (JSONObject) JSONSerializer.toJSON(resy);
//        String value = json.getJSONObject("LL").getString("value");
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("message_name", name);
        intent.putExtra("message_email", email);
        intent.putExtra("message_token", token);
//        Toast.makeText(MainActivity.this, "valiade", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }




    public Response<String>  call() {
        Response<String> res = null;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://i2.platform.simplifii.com/api/v1/admin/authenticate";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", Name.getText().toString());
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        try {
            jsonBody.put("password",Password.getText().toString());
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        final String mRequestBody = jsonBody.toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("LOG_VOLLEY YASH", response);
//                JSONObject jsonObject = new JSONObject(response);
//                String success = jsonObject.getString("success");
//                JSONObject jsonObject = null;



                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res_token = jsonObject.getString("token");
                    String res = jsonObject.getString("response");
                    JSONObject jsonObject_res = new JSONObject(res);
                    String res_name = jsonObject_res.getString("name");
                    String res_email = jsonObject_res.getString("email");
                    Log.i("LOG_VOLLEY YASH NAme", res_token);
//                    Toast.makeText(MainActivity.this, res_token, Toast.LENGTH_SHORT).show();
                    // Name
//                    Toast.makeText(MainActivity.this, "START", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, res_name, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, res_email, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, res_token, Toast.LENGTH_SHORT).show();
                    valiadate(res_name, res_email, res_token);
//                    String Token = jsonObject.getString("email");
//                    String Email = jsonObject.getString("token");
//                    String courseImageURL = jsonObject.getString("courseimg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                String success = jsonObject.getString("success");

// textView on main activity full response of post request
//                textView2.setText("Response: " + response.toString());

                if (response.equals("200")) {

                    Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    responseString = String.valueOf(response.statusCode);

                }
                Response<String> res = super.parseNetworkResponse(response);
                return super.parseNetworkResponse(response);
//                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", Name.getText().toString());
//                params.put("email", email);
                params.put("password", Password.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
        return res;
    }
    //----------------------------------------------------------\\
//    public void makePostUsingVolley()
//    {
//        new session = new SessionManager(new MainActivity().getApplicationContext());
//        session.checkLogin();
//        HashMap<String, String> user = session.getUserDetails();
//
//        final String  token = user.get(SessionManager.KEY_NAME);
//
//        //Toast.makeText(getActivity().getApplicationContext(),name, Toast.LENGTH_SHORT).show();
//
//        final Map<String, String> params = new HashMap<String, String>();
//        //params.put("Employees",name);
//        String tag_json_obj = "json_obj_req";
//        String url = "enter your url";
//
//        final ProgressDialog pDialog = new ProgressDialog(getApplicationContext());
//        pDialog.setMessage("Loading...");
//        pDialog.show();
//
//        StringRequest req = new StringRequest(Request.Method.GET,url,
//                new Response.Listener<String>() {
//                    // final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                    //"http://emservices.azurewebsites.net/Employee.asmx/CheckUserGet", new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(String response) {
//
//                        JSONObject json;
//                        // Toast.makeText(getActivity().getApplicationContext(),"dfgghfhfgjhgjghjuhj", Toast.LENGTH_SHORT).show();
//
//
//
//                        //Toast.makeText(getActivity().getApplicationContext(),obb.length(), Toast.LENGTH_SHORT).show();
//
//
//
//                        // JSONObject data=obj.getJSONObject("Employee_Name");
//                        ObjectOutput out = null;
//                        try {
//
//                            json = new JSONObject(response);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//
//
//                        pDialog.hide();
//                        // Toast.makeText(getApplicationContext(),"hi", Toast.LENGTH_SHORT).show();
//                        Log.d("", response);
//
//
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("", "Error: " + error.getMessage());
//                Toast.makeText(getActivity().getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                pDialog.hide();
//                // hide the progress dialog
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username",name);
//                params.put("password",password);
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(req, tag_json_obj);
//    }





//



}