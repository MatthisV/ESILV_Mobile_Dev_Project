package com.example.test_bank;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.*;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {



    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    static {
        System.loadLibrary("keys");
    }
    public native String getApiKey();
    public native String getBaseApi();
    String ApiKey= new String(Base64.decode(getApiKey(),Base64.DEFAULT));
    String RealApiKey = new String(Base64.decode(getBaseApi(),Base64.DEFAULT));

    @Override
    protected void onStart() {
        super.onStart();


        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: Use Biometric login" , Toast.LENGTH_SHORT)
                        .show();
                ///if(errString=="Use account password"){}
                ///else{System.exit(0);}

                System.exit(0);

            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Fingerprint required")
                .build();

        ///biometricPrompt.authenticate(promptInfo);
        biometricPrompt.authenticate(promptInfo);

        ArrayList<String> ibans = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> balances = new ArrayList<String>();
        ArrayList<String> currencies = new ArrayList<String>();
        EditText input_iban = findViewById(R.id.request_iban);
        TextView account_name = findViewById(R.id.show_name);
        TextView account_iban = findViewById(R.id.show_iban);
        TextView account_balance = findViewById(R.id.show_balance);
        TextView account_currency = findViewById(R.id.show_currency);
        Button get_request_button = findViewById(R.id.get_data);
        Button refresh_accounts=findViewById(R.id.refresh_api);

        refresh_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://"+ApiKey+".mockapi.io/api/m1/accounts";
                Toast.makeText(MainActivity.this,"Refreshing...",Toast.LENGTH_SHORT).show();
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONArray jsonArray = response;
                            boolean success = databaseHelper.addData(response); //This was for testing purpose (it returned true)
                            Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                queue.add(jsonArrayRequest);

            }
        });
        get_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selected_iban = input_iban.getText().toString();



                DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
                account_model selectedAccount=databaseHelper.getAccount(selected_iban);
                if(selectedAccount.getName()!=null) {
                    Toast.makeText(MainActivity.this, selectedAccount.getName(), Toast.LENGTH_SHORT).show();
                    account_name.setText(selectedAccount.getName());
                    account_balance.setText(selectedAccount.getAmount());
                    account_currency.setText(selectedAccount.getCurrency());
                    account_iban.setText(selectedAccount.getIban());
                }
                else{
                    Toast.makeText(getApplicationContext(), "IBAN not found", Toast.LENGTH_SHORT).show();
                }

                /*int index = 0;

                for (int i = 0; i < ibans.size(); i++) {
                    if (ibans.get(i).equals(selected_iban)) {
                        index = i;
                        correct_iban = true;
                    }
                }

                if (correct_iban) {
                    account_name.setText(names.get(index));
                    account_balance.setText(balances.get(index));
                    account_currency.setText(currencies.get(index));
                    account_iban.setText(ibans.get(index));
                } else {
                    Toast.makeText(getApplicationContext(), "IBAN not found", Toast.LENGTH_SHORT).show();
                }*/


            }
        });

    }

}