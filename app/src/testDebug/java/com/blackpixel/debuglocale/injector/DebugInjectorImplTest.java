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
import android.content.Intent;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Verify actual behavior from debug build type.
 */
@RunWith(MockitoJUnitRunner.class)
public class DebugInjectorImplTest {

    @Mock
    Context mockContext;

    private DebugInjectorImpl debugInjector;

    @Before
    public void setUp() {
        debugInjector = spy(new DebugInjectorImpl(mockContext));
        debugInjector.sharedPrefs = mock(SharedPreferences.class);

        // is there a better way to override this internal method?
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                return null;
            }
        }).when(debugInjector).setLocale(any(Locale.class), any(Activity.class));
    }

    @Test
    public void startSettingsActivity() throws Exception {
        Activity activity = mock(Activity.class);
        debugInjector.startSettingsActivity(activity);

        verify(activity).startActivity(any(Intent.class));
    }

    @Test
    public void overrideLocaleInitialDefault() throws Exception {
        Activity activity = mock(Activity.class);
        debugInjector.originalDefaultLocale = null;

        when(debugInjector.sharedPrefs.getString(eq(DebugInjectorImpl.PREF_DEBUG_LOCALE), anyString()))
            .thenReturn("");

        assertFalse(debugInjector.overrideLocale(activity));

        verify(debugInjector, never()).setLocale(any(Locale.class), any(Activity.class));
        assertEquals(Locale.getDefault(), debugInjector.originalDefaultLocale);
    }

    @Test
    public void overrideLocaleInitialChange() throws Exception {
        Activity activity = mock(Activity.class);

        String originalLanguage = "en";
        String newLanguage = "fr";
        debugInjector.originalDefaultLocale = new Locale(originalLanguage);

        when(debugInjector.sharedPrefs.getString(eq(DebugInjectorImpl.PREF_DEBUG_LOCALE), anyString()))
                .thenReturn(newLanguage);

        assertTrue(debugInjector.overrideLocale(activity));

        ArgumentCaptor<Locale> captor = ArgumentCaptor.forClass(Locale.class);

        verify(debugInjector).setLocale(captor.capture(), any(Activity.class));
        assertEquals(newLanguage, captor.getValue().getLanguage());
        assertEquals(originalLanguage, debugInjector.originalDefaultLocale.getLanguage());
    }

}