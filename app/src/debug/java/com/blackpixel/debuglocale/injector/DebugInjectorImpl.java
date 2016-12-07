/*
 * Copyright (c) 2016. BlackPixel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.blackpixel.debuglocale.injector;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.VisibleForTesting;

import java.util.Locale;

public class DebugInjectorImpl extends DebugInjector {

    private final static String PREFS_DEBUG_SETTINGS = "com.blackpixel.debuglocale.injector.pref_debug_setting";

    @VisibleForTesting
    final static String PREF_DEBUG_LOCALE = "pref_debug_locale";

    @VisibleForTesting
    SharedPreferences sharedPrefs;

    @VisibleForTesting
    Locale originalDefaultLocale;

    public DebugInjectorImpl(Context context) {
        this.sharedPrefs = context.getSharedPreferences(PREFS_DEBUG_SETTINGS, Context.MODE_PRIVATE);
    }

    @Override
    public void startSettingsActivity(Activity activity) {
        activity.startActivity(DebugSettingsActivity.newIntent(activity));
    }

    @Override
    public boolean overrideLocale(Activity activity) {

        boolean override = false;

        if (originalDefaultLocale == null) {
            originalDefaultLocale = Locale.getDefault();
        }

        Locale currentLocale = Locale.getDefault();

        String localeCode = sharedPrefs.getString(PREF_DEBUG_LOCALE, "");

        boolean isPhoneDefault = localeCode.isEmpty();
        Locale locale = isPhoneDefault ? originalDefaultLocale : new Locale(localeCode);

        if (!currentLocale.equals(locale)) {
            setLocale(locale, activity);
            override = true;
        }

        return override;
    }

    @VisibleForTesting
    void setLocale(Locale locale, Activity activity) {
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);

        Locale.setDefault(locale);
        activity.getResources().updateConfiguration(config, null);
    }


    static void setOverrideLocale(Context context, String localeCode) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_DEBUG_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PREF_DEBUG_LOCALE, localeCode);
        editor.apply();
    }

    static String getOverrideLocale(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_DEBUG_SETTINGS, Context.MODE_PRIVATE);
        return sharedPrefs.getString(PREF_DEBUG_LOCALE, "");
    }

}
