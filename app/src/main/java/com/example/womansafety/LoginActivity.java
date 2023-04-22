package com.example.womansafety;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {
    //public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    //public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    //public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    //SharedPreferences sharedpreferences;
    //String Storeemail, Storepassword;

    private FirebaseAuth auth;
    FirebaseUser user ;

    FirebaseDatabase database ;
    ProgressDialog progressDialog;
    EditText email, password;
    Button login;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        progressDialog  =new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to  your account");
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        checkusers();

        // getting the data which is stored in shared preferences.
        //sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        //Storeemail = sharedpreferences.getString(EMAIL_KEY, null);
        //Storepassword = sharedpreferences.getString(PASSWORD_KEY, null);

        login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                SetValidation();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkusers() {
        if (user != null) {
            Intent  Homeintent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(Homeintent);
            // User is signed in
        } else {
            Toast.makeText(LoginActivity.this,"Please Register your self",Toast.LENGTH_SHORT).show();
            // No user is signed in
        }
    }

    public void SetValidation() {
        String useremail = email.getText().toString().trim();
        String pwd = password.getText().toString();

        if(TextUtils.isEmpty(useremail) && Patterns.EMAIL_ADDRESS.matcher(useremail).matches() && TextUtils.isEmpty(pwd) )
        {
            Toast.makeText(LoginActivity.this,"Please enter   email  and password",Toast.LENGTH_SHORT).show();
        }

        else if(!(TextUtils.isEmpty(useremail)  && TextUtils.isEmpty(pwd)))
        {
            auth.signInWithEmailAndPassword(useremail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful()){
                        //SharedPreferences.Editor editor = sharedpreferences.edit();
                        // below two lines will put values for
                        // email and password in shared preferences.
                        //editor.putString(EMAIL_KEY, useremail);
                        //editor.putString(PASSWORD_KEY, pwd);
                        sendToHomeActivity();
                        Toast.makeText(LoginActivity.this,"WELCOME TO ANDROID VOTING SYSTEM",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Error:",Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }

        else
        {
            Toast.makeText(LoginActivity.this,"enter valid detail",Toast.LENGTH_SHORT).show();
        }

    }

    private void sendToHomeActivity() {
        Intent  Homeintent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(Homeintent);
    }
    


}



