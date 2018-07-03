package com.example.rakeshdeshpande.t_ember;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {


    private Button signupbutton,signinbutton;
    private EditText usernameentry, passwordentry,conpassentry;
    private TextView signinmsg;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            //work required here
            finish();
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);
        }


        progressDialog = new ProgressDialog(this);

        signupbutton = (Button) findViewById(R.id.signup);
        signinbutton = (Button) findViewById(R.id.signin);

        usernameentry=(EditText)findViewById(R.id.username);
        passwordentry = (EditText)findViewById(R.id.password);
        conpassentry = (EditText)findViewById(R.id.confirmpassword);
        signinmsg=(TextView)findViewById(R.id.signinmsgs);


        signinbutton.setOnClickListener(this);
        signupbutton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        if(view== signupbutton){

            registeruser();
        }
        if(view==signinbutton){
           Intent intent = new Intent(MainActivity.this,signin.class);
           startActivity(intent);
        }
    }

    public void registeruser(){

        String email = usernameentry.getText().toString();
        String password1 = passwordentry.getText().toString();
        String conpass = conpassentry.getText().toString();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(MainActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password1)){

            Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(conpass)){

            Toast.makeText(this, "Re-enter Password field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password1.matches(conpass)){
        progressDialog.setMessage("Registering User...Please Wait!");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show(); }
                            if(firebaseAuth.getCurrentUser()!=null){
                                //work required here
                                finish();
                                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                                startActivity(intent);
                            }
                       else{

                            Toast.makeText(MainActivity.this, "Oops! Something went wrong, please try again!", Toast.LENGTH_SHORT).show();

                        }progressDialog.dismiss();

                    }
                }

        ); } else {

            Toast.makeText(this, "Passwords Don't match! Please check again!", Toast.LENGTH_SHORT).show();
          //  usernameentry.setText("");
            passwordentry.setText("");
            conpassentry.setText("");
            return;
        }

    }








}
