package com.sha.kamel.phonefield;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.sha.kamel.phonefield.R;
import com.sha.kamel.phonefield.shared.ui.adapter.RecyclerAdapterCallback;
import com.sha.kamel.phonefield.shared.ui.recyclerviewdialog.SearchRecyclerViewDialogFrag;

/**
 * PhoneField is a custom view for phone numbers with the corresponding country flag, and it uses
 * libphonenumber to validate the phone number.
 *
 * Created by Ismail on 5/6/16.
 */
public abstract class PhoneField extends LinearLayout implements RecyclerAdapterCallback{

  private ImageView iv_flag;

  private EditText et_phoneNumber;

  private Country mCountry;

  private PhoneNumberUtil mPhoneUtil = PhoneNumberUtil.getInstance();

  private AppCompatActivity activity;

  public void init(AppCompatActivity activity){
    this.activity = activity;
  }

  /**
   * Instantiates a new Phone field.
   *
   * @param context the context
   */
  public PhoneField(Context context) {
    this(context, null);
  }

  /**
   * Instantiates a new Phone field.
   *
   * @param context the context
   * @param attrs the attrs
   */
  public PhoneField(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  /**
   * Instantiates a new Phone field.
   *
   * @param context the context
   * @param attrs the attrs
   * @param defStyleAttr the def style attr
   */
  public PhoneField(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    inflate(getContext(), getLayoutResId(), this);
    updateLayoutAttributes();
    prepareView();
  }

  /**
   * Prepare view.
   */
  protected void prepareView() {

    iv_flag = findViewById(R.id.iv_flag);
    et_phoneNumber = findViewById(R.id.et_phoneNumber);

    if (iv_flag == null || et_phoneNumber == null) {
      throw new IllegalStateException("Please provide a valid xml layout.");
    }

    mCountry = Countries.COUNTRIES.get(0);
    iv_flag.setImageResource(mCountry.getResId(getContext()));

    iv_flag.setOnClickListener(v -> {

      if (activity == null)
        throw new IllegalStateException("Please, call init first.");

      SearchRecyclerViewDialogFrag.newInstance(
              new CountryAdapter(
                      Countries.COUNTRIES,
                      this,
                      item -> {
                        mCountry = item;
                        iv_flag.setImageResource(item.getResId(getContext()));
                      })
      ).show(activity.getFragmentManager(), SearchRecyclerViewDialogFrag.class.getSimpleName());
    });
  }

  /**
   * Gets edit text.
   *
   * @return the edit text
   */
  public EditText getEditText() {
    return et_phoneNumber;
  }

  /**
   * Checks whether the entered phone number is valid or not.
   *
   * @return  a boolean that indicates whether the number is of a valid pattern
   */
  public boolean isValid() {
    try {
      return mPhoneUtil.isValidNumber(parsePhoneNumber(getRawInput()));
    } catch (NumberParseException e) {
      return false;
    }
  }

  private Phonenumber.PhoneNumber parsePhoneNumber(String number) throws NumberParseException {
    String defaultRegion = mCountry != null ? mCountry.getCode().toUpperCase() : "";
    return mPhoneUtil.parseAndKeepRawInput(number, defaultRegion);
  }

  /**
   * Gets phone number.
   *
   * @return the phone number
   */
  public String getPhoneNumber() {
    try {
      Phonenumber.PhoneNumber number = parsePhoneNumber(getRawInput());
      return mPhoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
    } catch (NumberParseException ignored) {
    }
    return getRawInput();
  }

  /**
   * Sets default country.
   *
   * @param countryCode the country code
   */
  public void setDefaultCountry(String countryCode) {
    for (Country country : Countries.COUNTRIES) {
      if (country.getCode().equalsIgnoreCase(countryCode)) {
        mCountry = country;
        iv_flag.setImageResource(country.getResId(getContext()));
        break;
      }
    }
  }

  private void selectCountry(int dialCode) {
    for (Country country : Countries.COUNTRIES) {
      if (country.getDialCode() == dialCode) {
        mCountry = country;
        iv_flag.setImageResource(country.getResId(getContext()));
      }
    }
  }

  /**
   * Sets phone number.
   *
   * @param rawNumber the raw number
   */
  public void setPhoneNumber(String rawNumber) {
    try {
      Phonenumber.PhoneNumber number = parsePhoneNumber(rawNumber);
      if (mCountry == null || mCountry.getDialCode() != number.getCountryCode()) {
        selectCountry(number.getCountryCode());
      }
      et_phoneNumber.setText(mPhoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
    } catch (NumberParseException ignored) {
      ignored.printStackTrace();
    }
  }

  private void hideKeyboard() {
    ((InputMethodManager) getContext().getSystemService(
        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(et_phoneNumber.getWindowToken(), 0);
  }

  /**
   * Update layout attributes.
   */
  protected abstract void updateLayoutAttributes();

  /**
   * Gets layout res id.
   *
   * @return the layout res id
   */
  public abstract int getLayoutResId();

  /**
   * Sets hint.
   *
   * @param resId the res id
   */
  public void setHint(int resId) {
    et_phoneNumber.setHint(resId);
  }

  /**
   * Gets raw input.
   *
   * @return the raw input
   */
  public String getRawInput() {
    return et_phoneNumber.getText().toString();
  }

  /**
   * Sets error.
   *
   * @param error the error
   */
  public void setError(String error) {
    et_phoneNumber.setError(error);
  }

  /**
   * Sets text color.
   *
   * @param resId the res id
   */
  public void setTextColor(int resId) {
    et_phoneNumber.setTextColor(resId);
  }

  @Override
  public View view() {
    return this;
  }

  @Override
  public Context context() {
    return getContext();
  }

  @Override
  public AppCompatActivity activity() {
    return activity;
  }
}
