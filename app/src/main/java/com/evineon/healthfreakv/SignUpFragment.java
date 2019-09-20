package com.evineon.healthfreakv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment {

    EditText username, email, password, confirmPassword, address, phone, city, postalCode, name;
    String Username, Email, Password, ConfirmPassword, Address, Phone, City, PostalCode, Name;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ImageButton backButton, doneButton;
    FirebaseDatabase database;
    DatabaseReference myRef, childRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_page,container,false);
        database = FirebaseDatabase.getInstance();
        sp = this.getActivity().getSharedPreferences("key", 0);
        editor = sp.edit();
        editor.putBoolean("isLoggedIn", false);
        username = rootView.findViewById(R.id.username_edit);
        email = rootView.findViewById(R.id.username_login_edit);
        password = rootView.findViewById(R.id.password_login_edit);
        confirmPassword = rootView.findViewById(R.id.verify_password_edit);
        name = rootView.findViewById(R.id.full_name_edit);
        address = rootView.findViewById(R.id.address_edit);
        phone = rootView.findViewById(R.id.phone_number_edit);
        city = rootView.findViewById(R.id.city_edit);;
        postalCode = rootView.findViewById(R.id.postal_code_edit);
        backButton = rootView.findViewById(R.id.back_button);
        doneButton = rootView.findViewById(R.id.done_button);
        editor.apply();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().onBackPressed();
                myRef = database.getReference("User");
                Username = username.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();
                ConfirmPassword = confirmPassword.getText().toString();
                Name = name.getText().toString();
                Address = address.getText().toString();
                Phone = phone.getText().toString();
                City = city.getText().toString();
                PostalCode = postalCode.getText().toString();
                if(Password.equals(ConfirmPassword)){
                    editor.putString("username", Name);
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    myRef.setValue(username);
                    childRef = myRef.child(Email);
                    childRef.child("Address").setValue(Address);
                    childRef.child("Postal_Code").setValue(PostalCode);
                    childRef.child("City").setValue(City);
                    childRef.child("Contact").setValue(Phone);
                    childRef.child("Name").setValue(Name);
                    childRef.child("Password").setValue(Password);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}
