package com.example.roufish;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends Activity {

    //FirebaseAuth mAuth;
    EditText inputPassword, inputAlamat, inputNoHP ;
    EditText inputUsername;
    EditText inputEmail;
    Button btnDaftar ;
    FloatingActionButton backToMain ;
    Button daftar ;
    DatabaseReference reference;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        //mAuth = FirebaseAuth.getInstance();
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        inputEmail = findViewById(R.id.input_Email);
        inputAlamat = findViewById(R.id.input_Alamat);
        inputNoHP = findViewById(R.id.input_NoHP);
        btnDaftar = (Button) findViewById(R.id.btn_daftar);
        backToMain = findViewById(R.id.backToMainREG);
        //daftar = findViewById(R.id.btn_daftar);


        //reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://roufish-database-default-rtdb.firebaseio.com/");




       /* daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(register.this, login.class);

                startActivity(registerIntent);
            }
        });*/

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(register.this, MainActivity.class);

                startActivity(registerIntent);
            }
        });


        //hide password yang diinputkan
        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            boolean passwordView = false;
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                final int Right = 2;

                if(motionEvent.getAction()== motionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=inputPassword.getRight()-inputPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=inputPassword.getSelectionEnd();
                        if(passwordView){
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibilityoff_icon,0);
                            inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordView=false;
                        }else{
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_icon,0);
                            inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordView=true;
                        }
                        inputPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

        //pop up akun berhasil di daftarkan

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String username,password, email,alamat, noHP;



                username = inputUsername.getText().toString();
                password = inputPassword.getText().toString();
                email = inputEmail.getText().toString();
                alamat = inputAlamat.getText().toString();
                noHP = inputNoHP.getText().toString();


                if (username.isEmpty() || password.isEmpty() || email.isEmpty() || alamat.isEmpty() ||noHP.isEmpty()){
                    Toast.makeText(register.this,"Data masih ada yang kosong",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    user user = new user(username,password,email,alamat,noHP);
                    reference.child(username).setValue(user);


                    /*Toast.makeText(register.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);*/

                    Toast.makeText(register.this,"Pendaftaran berhasil",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                    builder.setTitle("Pendaftaran Berhasil");
                    builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent registerIntent = new Intent(register.this, login.class);

                            startActivity(registerIntent);
                        }
                    }).setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).create().show();



                    /*reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)){
                                Toast.makeText(register.this,"username sudah tersedia",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                reference.child("users").child(username).child("username").setValue(username);
                                reference.child("users").child(username).child("email").setValue(email);
                                reference.child("users").child(username).child("password").setValue(password);
                                reference.child("users").child(username).child("alamat").setValue(alamat);
                                reference.child("users").child(username).child("NoHP").setValue(noHP);

                                Toast.makeText(register.this,"Pendaftaran berhasil",Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                                builder.setTitle("Pendaftaran Berhasil");
                                builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        Intent registerIntent = new Intent(register.this, login.class);

                                        startActivity(registerIntent);
                                    }
                                }).setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                }).create().show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/
                }
            }
        });
    }
}