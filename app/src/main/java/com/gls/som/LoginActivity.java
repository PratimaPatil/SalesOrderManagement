package com.gls.som;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gls.som.login.LoginResponse;
import com.gls.som.login.UserBean;
import com.gls.som.utils.AppData;
import com.gls.som.utils.CallFor;
import com.gls.som.utils.PostData;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity {
    EditText input_layout_username;
    EditText input_layout_password;
    String username,password;
    Context context;
    Button btnlogin;
    RelativeLayout rlmainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=LoginActivity.this;
        rlmainLayout= (RelativeLayout) findViewById(R.id.rlmainLayout);
        input_layout_password= (EditText) findViewById(R.id.password);
        btnlogin= (Button) findViewById(R.id.btnlogin);
        input_layout_username= (EditText) findViewById(R.id.username);
    }

    @Override
    public void onGetResponse(String result, String callFor) {
        Log.e("result",result);
        if (callFor.equalsIgnoreCase(CallFor.LOGIN))
        {
            Log.e("callFor",callFor);
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new Gson().fromJson(result,LoginResponse.class);
            if (loginResponse!=null) {
                if (loginResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
                    Log.e("username success",username);
                    Log.e("password success",password);
                    AppData.setUserName(context, username);
                    AppData.setPassword(context,password);
                    AppData.setLoginResponse(context, loginResponse);
                    Intent homeActivity = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(homeActivity);
                } else if (loginResponse.getStatus().equalsIgnoreCase("Error")){
                    Snackbar snackbar = Snackbar
                            .make(rlmainLayout, "Please enter correct login details", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();
                }
            }else
            {
                Toast.makeText(getApplicationContext(), "Server is not responding", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void Login(View view)
    {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        Log.e("enter login","login");
        username=input_layout_username.getText().toString();
        password=input_layout_password.getText().toString();
        UserBean userData=new UserBean();
        userData.setPassword(password);
        userData.setUser_name(username);
        new PostData(new Gson().toJson(userData),this,CallFor.LOGIN).execute();
    }
    public void onLoginFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btnlogin.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String name = input_layout_username.getText().toString();
        String password = input_layout_password.getText().toString();

        if (name.isEmpty()) {
            input_layout_username.setError("Enter user name");
            valid = false;
        } else {
            input_layout_username.setError(null);
        }

        if (password.isEmpty()) {
            input_layout_password.setError("Enter password");
            valid = false;
        } else {
            input_layout_password.setError(null);
        }

        return valid;
    }
}
