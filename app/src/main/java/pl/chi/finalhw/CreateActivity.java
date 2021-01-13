package pl.chi.finalhw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CreateActivity extends AppCompatActivity {

    EditText email, name, pass;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        email = findViewById(R.id.etEmail);
        name = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);
        btnCreate = findViewById(R.id.btnCreate);

    }

    public void startCreateAcc(View view) {
        btnCreate.setEnabled(false);



    }
}