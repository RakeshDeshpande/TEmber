package com.example.rakeshdeshpande.t_ember;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity implements View.OnClickListener {

    private EditText userentry, passwordentry;
    private Button signings;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            //work required here
            finish();
            Intent intent = new Intent(signin.this, Main2Activity.class);
            startActivity(intent);
        }


        progressDialog = new ProgressDialog(this);

        userentry = (EditText) findViewById(R.id.signinuser);
        passwordentry = (EditText) findViewById(R.id.signinpassword);

        signings = (Button) findViewById(R.id.signing);

        signings.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == signings) {
            signing();
        }
    }

    public void signing(){

        String entry=userentry.getText().toString().trim();
        String passy=passwordentry.getText().toString().trim();



        if(TextUtils.isEmpty(entry)){

            Toast.makeText(this, "USERNAME field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passy)){
            Toast.makeText(this, "PASSWORD filed cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Sign In... Please wait..!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(entry,passy).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Profile activity!
                    finish();
                    Intent intent = new Intent(signin.this, Main2Activity.class);
                    startActivity(intent);
                } else {
                    finish();
                    Toast.makeText(signin.this, "Error! Please check your email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
