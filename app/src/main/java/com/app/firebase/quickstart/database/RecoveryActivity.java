package com.app.firebase.quickstart.database;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.quickstart.database.R;

public class RecoveryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RecoveryActivity";

    private Button recoveryPassButton;
    private EditText emailAddress;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        // Views
        recoveryPassButton = (Button) findViewById(R.id.button_recovery_pass);
        emailAddress = (EditText) findViewById(R.id.field_email_recovery);
        // Click Listeners
        recoveryPassButton.setOnClickListener(this);
        emailAddress.setOnClickListener(this);
        // Show button UP
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()){
                case R.id.button_recovery_pass:
                    if(emailAddress.getText().toString().isEmpty()){
                        Toast.makeText(RecoveryActivity.this, "Ingresa tu correo electrónico",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        sendMailResestPass(emailAddress.getText().toString());
                    }
                    break;

            }
    }

    private void sendMailResestPass(String emailAddress){

        final Intent intent = new  Intent(this, SignInActivity.class);

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(RecoveryActivity.this, "Revisa tu correo electrónico",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(RecoveryActivity.this, "Tu e-mail no está registrado",
                                Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
