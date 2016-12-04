DebugInjector Pattern Sample
===============================
### Overview
This repo demonstrates the DebugInjector Pattern. This pattern describes how to securely
implement debug-only functionality in an Android Application.

### Locale Hello World App
The sample application is a simple Hello World App that supports multiple locale's. The app
contains a **Debug Settings** menu that is only available when `BuildConfig.DEBUG==true`. The
settings screen allows the developer/tester to switch the application's locale without having to
change the Android System Language Settings (which is rarely fun).

**Use Cases**

* As the app developer/tester I want to easily test locale changes in debug builds
* As a project manager I want to ensure debug functionality is not included in release builds
(security, efficiency)
* As a hacker, I **SHALL NOT** be able to exercise or reverse engineer
any of the debug functionality in a release build obtained.

### DebugInjection Pattern
The DebugInjection Pattern is just a name for a technique that has been implemented a
million times, adding debug-only functionality to an applications. The key is to encapsulate
the debug-only functionality in a manner that allows the behavior to be totally absent from
release builds. The Android platform makes this easy by providing the ability to use different
versions of a source/resource file for a given build variant
(see [sourceSets](https://developer.android.com/studio/build/build-variants.html#sourcesets)).

The Locale Hello World app demonstrates this pattern in the `DebugInjector.java` class. This
abstract class defines all the behavior available to debug builds and provides a static
creation method to encapsulate instantiation to this file.  The implementation is distributed
across two `DebugInjectImpl.java` classes provided in the `/debug` and `/relase` folders.
The `/release` version provides a simple no-op implementation while the `/debug` version
does the real work.

The functionality in this sample includes a debug-only Activity which needs to be declared in
the `/debug` version of the `AndroidManifest.xml` file. This takes advantage of Android's
[Manifest Merge](https://developer.android.com/studio/build/manifest-merge.html) capabilities
which is really cool (but has gotten me into trouble a couple of times with third-partly libs).

The debug functionality is initiated from the `MainActivity.java` class. This class calls
`DebugInejctor.getInstance()` method and assigns the result to a method variable. The
`startSettingsActivity()` is then called to respond to the **Debug Settings** menu click
and `getOverrideLocale()` is called from the `onResume` override.





