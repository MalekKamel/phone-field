package com.sha.kamel.phonefield;

import android.content.Context;
import android.content.res.Resources;

import com.sha.kamel.andrutil.LocaleUtil;

import java.util.Locale;

/**
 * Country object that holds the country iso2 code, name, and dial code.
 * @author Ismail Almetwally
 */
public class Country {

  private String mCode;

  private String nameAr;

  private String nameEn;

  private int mDialCode;

  public Country(
          String code,
          String nameEn,
          String nameAr,
          int dialCode) {
    mCode = code;
    this.nameEn = nameEn;
    this.nameAr = nameAr;
    mDialCode = dialCode;
  }

  public String getName(Context context) {
    switch (context.getResources().getConfiguration().locale.getLanguage()){
      case LocaleUtil.ARABIC:
        return nameAr;

        default:
          return nameEn;
    }
  }

  public String getNameAr() {
    return nameAr;
  }

  public String getNameEn() {
    return nameEn;
  }

  public String getCode() {
    return mCode;
  }

  public int getDialCode() {
    return mDialCode;
  }

  public String getDisplayName() {
    return new Locale("", mCode).getDisplayCountry(Locale.US);
  }

  public int getResId(Context context) {
    String name = String.format("country_flag_%s", mCode.toLowerCase());
    final Resources resources = context.getResources();
    return resources.getIdentifier(name, "drawable", context.getPackageName());
  }
}
