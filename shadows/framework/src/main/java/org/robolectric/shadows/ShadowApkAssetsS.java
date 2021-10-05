package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.S;

import android.content.res.ApkAssets;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/** A shadow specifically for the android-S variant of ApkAssets. */
@Implements(
    value = ApkAssets.class,
    minSdk = S,
    shadowPicker = ShadowApkAssets.Picker.class,
    isInAndroidSdk = false)
public class ShadowApkAssetsS extends ShadowArscApkAssets9 {

  // Overwrite ApkAssets.java to prevent clearing out the apk assets
  @Implementation(minSdk = S)
  protected void close() {}
}
