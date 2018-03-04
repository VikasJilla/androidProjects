package com.vj.gsignin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 300;
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.profileView).setVisibility(View.INVISIBLE);
            findViewById(R.id.signOut).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.signOut).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in_button).setVisibility(View.INVISIBLE);
            findViewById(R.id.profileView).setVisibility(View.VISIBLE);
            TextView name = findViewById(R.id.name);
            TextView emailID = findViewById(R.id.email);
            name.setText(account.getDisplayName());
            emailID.setText(account.getEmail());
            final ImageView profilePhoto = findViewById(R.id.profilePhoto);
            final RequestQueue rqueue = Volley.newRequestQueue(this);
            rqueue.add(new ImageRequest(account.getPhotoUrl().toString(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    profilePhoto.setImageBitmap(response);
                }
            }, 140, 140, ImageView.ScaleType.CENTER, Bitmap.Config.ALPHA_8, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));
            rqueue.start();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e(TAG, "Successfully signed in" + account.getEmail() + account.getDisplayName());
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    public void signOut(View v) {
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e(TAG, "sucessfully logged out");
                Toast.makeText(getApplicationContext(), "Logged out!", Toast.LENGTH_LONG).show();
                updateUI(null);
            }
        });
    }
}
