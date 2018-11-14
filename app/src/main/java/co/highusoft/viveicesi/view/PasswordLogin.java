package co.highusoft.viveicesi.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

import co.highusoft.viveicesi.R;
//import com.google.firebase.analytics.FirebaseAnalytics;

public class PasswordLogin extends AppCompatActivity {

    private EditText et_email;
    private ImageButton btn_sendEmail;
    private ActionCodeSettings actionCodeSettings;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login);

        auth = FirebaseAuth.getInstance();

        et_email=findViewById(R.id.et_emailPass);

        btn_sendEmail=findViewById(R.id.btn_sendEmail);
        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  actionCodeSettings=new ActionCodeSettings();
                //auth.sendSignInLinkToEmail();
            }
        });

    }
}
