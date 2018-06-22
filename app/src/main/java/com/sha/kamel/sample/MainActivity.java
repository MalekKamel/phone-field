package com.sha.kamel.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.sha.kamel.phonefield.PhoneEditText;
import com.sha.kamel.phonefield.PhoneInputLayout;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final PhoneInputLayout phoneInputLayout = findViewById(R.id.phone_input_layout);
    final PhoneEditText phoneEditText = findViewById(R.id.edit_text);

    // Must call init, else, you'll get IllegalStateException when clicking country view.
    phoneInputLayout.init(this);
    phoneEditText.init(this);

    final Button button = findViewById(R.id.submit_button);

    phoneInputLayout.setHint(R.string.phone_hint);
    phoneInputLayout.setDefaultCountry("EG");

    phoneEditText.setHint(R.string.phone_hint);
    phoneEditText.setDefaultCountry("FR");

    button.setOnClickListener(v -> {
      boolean valid = true;
      if (phoneInputLayout.isValid()) {
        phoneInputLayout.setError(null);
      } else {
        phoneInputLayout.setError(getString(R.string.invalid_phone_number));
        valid = false;
      }

      if (phoneEditText.isValid()) {
        phoneEditText.setError(null);
      } else {
        phoneEditText.setError(getString(R.string.invalid_phone_number));
        valid = false;
      }

      if (valid) {
        toast(R.string.valid_phone_number);
      } else {
        toast(R.string.invalid_phone_number);
      }
    });
  }

  private void toast(int res) {
    Toast.makeText(this, res, Toast.LENGTH_LONG).show();
  }

}
