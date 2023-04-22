package com.example.womansafety;

import android.content.Intent;
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

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText name, email, phone, password;
    Button Register;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        Register=findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();

        //if(auth.getCurrentUser()!=null){
            //Intent intent=new Intent(this,LoginActivity.class);
            //startActivity(intent);
        //}
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    public void SetValidation() {
        String username = name.getText().toString().trim();
        String useremail = email.getText().toString().trim();
        String userphone = phone.getText().toString().trim();
        String pwd = password.getText().toString();

        if(TextUtils.isEmpty(useremail) && Patterns.EMAIL_ADDRESS.matcher(useremail).matches() && TextUtils.isEmpty(pwd) )
        {
            Toast.makeText(SignupActivity.this,"Please enter   email  and password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(username))
        {
            name.requestFocus();
            name.setError("enter your name");
        }
        else if (TextUtils.isEmpty(userphone))
        {
            Toast.makeText(getApplicationContext(), "Phone Number Field is Empty", Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            phone.setError("enter your phone No");
        }
        else if (TextUtils.isEmpty(pwd))
        {
            Toast.makeText(getApplicationContext(), "Password Field is Empty", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            password.setError("enter a password");
        }

        else
        {
            auth.createUserWithEmailAndPassword(useremail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful()){

                        sendToHomeActivity();
                        Toast.makeText(SignupActivity.this,"WELCOME TO WOMEN SAFETY APP",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SignupActivity.this,"Error:",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void sendToHomeActivity() {
        Intent  Homeintent = new Intent(SignupActivity.this,MainActivity.class);
        startActivity(Homeintent);
    }

    //@Override
    //protected void onStart() {
        //super.onStart();
        //if (email != null && password != null) {
            //Intent i = new Intent(SignupActivity.this, MainActivity.class);
            //startActivity(i);
        //}
    //}


}
