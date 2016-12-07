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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Verify No-op behavior from release build type.
 */
@RunWith(MockitoJUnitRunner.class)
public class DebugInjectorImplTest {

    @Mock
    Context mockContext;

    DebugInjectorImpl debugInjector;

    @Before
    public void setUp() {
        debugInjector = spy(new DebugInjectorImpl(mockContext));
    }

    @Test
    public void startSettingsActivity() throws Exception {
        Activity activity = mock(Activity.class);
        debugInjector.startSettingsActivity(activity);
        verify(debugInjector).startSettingsActivity(any(Activity.class));
        verifyZeroInteractions(activity);
        verifyZeroInteractions(debugInjector);
    }

    @Test
    public void overrideLocaleInitialDefault() throws Exception {
        Activity activity = mock(Activity.class);
        assertFalse(debugInjector.overrideLocale(activity));
        verify(debugInjector).overrideLocale(any(Activity.class));
        verifyZeroInteractions(activity);
        verifyZeroInteractions(debugInjector);
    }
}