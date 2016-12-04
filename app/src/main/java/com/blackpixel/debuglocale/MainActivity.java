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

package com.blackpixel.debuglocale;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.blackpixel.debuglocale.injector.DebugInjector;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DebugInjector debugInjector = null;

    private TextView helloWorldTextView;
    private TextView dateTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloWorldTextView = (TextView) findViewById(R.id.main_hello_world_tv);
        dateTimeTextView = (TextView) findViewById(R.id.main_date_time_tv);

        if (BuildConfig.DEBUG) {
            debugInjector = DebugInjector.getInstance(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        if (BuildConfig.DEBUG) {
            MenuItem item = menu.findItem(R.id.setting_debug);
            item.setVisible(true);
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            debugInjector.overrideLocale(this);
        }
        updateUi();
    }

    private void updateUi() {
        helloWorldTextView.setText(R.string.hello_world);
        dateTimeTextView.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG)
                .format(new Date()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;

        switch (item.getItemId()) {
            case R.id.setting_app:
                // TODO: add settings activity
                Snackbar bar = Snackbar.make(findViewById(android.R.id.content),
                        getResources().getString(R.string.snackbar_build_type,
                                    BuildConfig.DEBUG ? "Debug" : "Release" ),
                        Snackbar.LENGTH_LONG);
                bar.show();
                handled = true;
                break;
            case R.id.setting_debug:
                if (BuildConfig.DEBUG) {
                    debugInjector.startSettingsActivity(this);
                }
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }

        return handled;
    }
}
