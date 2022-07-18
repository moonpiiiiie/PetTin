package edu.neu.madcourse.pettin;

import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.neu.madcourse.pettin.Classes.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUsername;
    private EditText registerEmail;
    private EditText registerPassword;
    private Button registerButton;
    private TextView textLogin;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsername = findViewById(R.id.registerUsername);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        textLogin = findViewById(R.id.textLogin);
        registerButton = findViewById(R.id.registerButton);

        progressBar = findViewById(R.id.registerProgressBar);

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(view -> {

            String user = registerUsername.getText().toString();
            String email = registerEmail.getText().toString();
            String pw = registerPassword.getText().toString();

            if (user.isEmpty()) {
                registerUsername.setError("Username is required");
            }

            else if (email.isEmpty()) {
                registerEmail.setError("Email is required");
            }
            else if (pw.isEmpty()) {
                registerPassword.setError("Password is required");
            }

            else {
                reference.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(RegisterActivity.this, "Duplicate username", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progressBar.setVisibility(view.VISIBLE);

                            //Register the user in the firebase
                            auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    User newUser = new User(user, email, pw);
                                    reference.child(user).setValue(newUser);
                                    Toast.makeText(RegisterActivity.this, "Register Successfully,  " + user, Toast.LENGTH_SHORT).show();
                                    Intent toMainPage = new Intent(RegisterActivity.this, PlayDateActivity.class);
                                    toMainPage.putExtra("username", user);
                                    startActivity(toMainPage);
                                    progressBar.setVisibility(INVISIBLE);
                                }
                                else {
                                    progressBar.setVisibility(INVISIBLE);
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            });
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


        });

        textLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }

}
