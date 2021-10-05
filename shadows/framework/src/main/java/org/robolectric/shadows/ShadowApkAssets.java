package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.S;

import org.robolectric.RuntimeEnvironment;

// transliterated from
// https://android.googlesource.com/platform/frameworks/base/+/android-9.0.0_r12/core/jni/android_content_res_ApkAssets.cpp

/** Parent class for all shadows of ApkAssets */
public abstract class ShadowApkAssets {

  public static class Picker extends ResourceModeShadowPicker<ShadowApkAssets> {

    public Picker() {
      super(ShadowLegacyApkAssets.class, null, ShadowArscApkAssets9.class);
    }

    @Override
    public Class<? extends ShadowApkAssets> pickShadowClass() {
      if (RuntimeEnvironment.getApiLevel() >= S) {
        return ShadowApkAssetsS.class;
      }
      return super.pickShadowClass();
    }
  }
}
