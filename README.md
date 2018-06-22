# PhoneFiled


##### PhoneFiled is an easy way to select country phone and validate the entered number.

##### It's a fork from https://github.com/lamudi-gmbh/android-phone-field.
##### I added a search and arabic names for countries.

![alt text](https://github.com/ShabanKamell/phone-field/blob/master/raw/phone-field.gif "Sample App")

# Installation

```gradle
repositories {
        maven {
            url  "https://dl.bintray.com/shabankamel/android"
        }
    }
dependencies {
    implemetation 'com.sha.kamel:phonefield:1.2.1@aar'
}
```

# Usage:

```java
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
  
```

### See 'app' module for the full code.

# License

## Apache license 2.0
