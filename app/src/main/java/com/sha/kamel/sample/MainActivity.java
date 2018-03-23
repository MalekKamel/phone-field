package com.sha.kamel.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
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

    phoneInputLayout.init(this);
    phoneEditText.init(this);

//    CustomPhoneInputLayout customPhoneInputLayout = new CustomPhoneInputLayout(this, "EG");

//    final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
//        .findViewById(android.R.id.content)).getChildAt(0);
//
//    viewGroup.addView(customPhoneInputLayout, 2);


    final Button button = findViewById(R.id.submit_button);

    assert phoneInputLayout != null;
    assert phoneEditText != null;
    assert button != null;

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
        Toast.makeText(MainActivity.this, R.string.valid_phone_number, Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(MainActivity.this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();
      }
    });
  }
}
