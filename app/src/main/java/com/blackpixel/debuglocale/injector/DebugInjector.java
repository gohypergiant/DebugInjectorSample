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

public abstract class DebugInjector {

    private static DebugInjector sDebugInjector;

    public static DebugInjector getInstance(Context context) {

        if (sDebugInjector == null) {

/*
             // DebugInjectorPattern #1
             // use reflection to create DebugInjectorImpl
             // pros:
             //   one version of DebugInjectorImpl.java
             //   easier to "lean on the compiler" when developing

            try {
                sDebugInjector = (DebugInjector) Class.forName("com.blackpixel.debuglocale.injector.DebugInjectorImpl")
                        .getConstructor(Context.class).newInstance(context);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
                    | NoSuchMethodException | InvocationTargetException e) {
                // TODO: error handling
                e.printStackTrace();
            }
*/

            // DebugInjectorPattern #2
            // place no-op version of DebugInjectorImpl.java in release folder
            // pros:
            //   no reflection
            //   more android-y
            sDebugInjector = new DebugInjectorImpl(context);

        }
        return sDebugInjector;
    }

    public abstract void startSettingActivity(Activity activity);

    public abstract boolean overrideLocale(Activity activity);

}
