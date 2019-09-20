package com.evineon.healthfreakv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    FrameLayout fragmentContainer;
    TextView forgotPassword;
    Button logInButton, signUpButton;
    EditText username, password;
    SignUpFragment signUpFragment;
    String TAG = "Password check";
    Boolean signUpfrag = false;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    FragmentManager fragmentManager;

    FirebaseDatabase database;
    DatabaseReference myRef, passwordRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        fragmentContainer = findViewById(R.id.fragment_container);
        logInButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.continue_button);
        forgotPassword = findViewById(R.id.forgot_password);
        username = findViewById(R.id.username_login_edit);
        password = findViewById(R.id.password_login_edit);
        signUpfrag = false;
        database = FirebaseDatabase.getInstance();
        sp = getSharedPreferences("key", 0);
        editor = sp.edit();
        myRef = database.getReference("User");
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String password_txt, email_txt;
                if(!password.getText().toString().equals("") && !username.getText().toString().equals("")){
                    password_txt = password.getText().toString();
                    email_txt = username.getText().toString();
                    passwordRef = myRef.child(email_txt);
                    passwordRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String actualPassword = dataSnapshot.child("Password").getValue(String.class);
                            String username = dataSnapshot.child("Name").getValue().toString();
                            Log.d(TAG, "Password is: " + actualPassword);
                            if(actualPassword.equals(password_txt)){
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("username", username);
                                editor.apply();
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("DB Error", databaseError.toString());
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Fields can not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpFragment = new SignUpFragment();
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_from_right);
                fragmentTransaction.add(R.id.fragment_container, signUpFragment).commit();
                fragmentContainer.setClickable(true);
                signUpfrag = true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(signUpfrag)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_from_right);
            fragmentTransaction.remove(signUpFragment).commit();
            fragmentContainer.setClickable(false);
            signUpfrag = false;
        }
        else
            super.onBackPressed();
    }
}
