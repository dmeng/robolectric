package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.P;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.Rect;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(
    className = "android.view.RenderNode",
    isInAndroidSdk = false,
    minSdk = LOLLIPOP,
    maxSdk = P)
public class ShadowRenderNode {
  private float alpha = 1f;
  private float cameraDistance;
  private boolean clipToOutline;
  private float elevation;
  private boolean overlappingRendering;
  private boolean pivotExplicitlySet;
  private float pivotX;
  private float pivotY;
  private float rotation;
  private float rotationX;
  private float rotationY;
  private float scaleX = 1f;
  private float scaleY = 1f;
  private float translationX;
  private float translationY;
  private float translationZ;
  private int left;
  private int top;
  private int right;
  private int bottom;

  @Implementation
  public boolean setAlpha(float alpha) {
    this.alpha = alpha;
    return true;
  }

  @Implementation
  public float getAlpha() {
    return alpha;
  }

  @Implementation
  public boolean setCameraDistance(float cameraDistance) {
    this.cameraDistance = cameraDistance;
    return true;
  }

  @Implementation
  public float getCameraDistance() {
    return cameraDistance;
  }

  @Implementation
  public boolean setClipToOutline(boolean clipToOutline) {
    this.clipToOutline = clipToOutline;
    return true;
  }

  @Implementation
  public boolean getClipToOutline() {
    return clipToOutline;
  }

  @Implementation
  public boolean setElevation(float lift) {
    elevation = lift;
    return true;
  }

  @Implementation
  public float getElevation() {
    return elevation;
  }

  @Implementation
  public boolean setHasOverlappingRendering(boolean overlappingRendering) {
    this.overlappingRendering = overlappingRendering;
    return true;
  }

  @Implementation
  public boolean hasOverlappingRendering() {
    return overlappingRendering;
  }

  @Implementation
  public boolean setRotation(float rotation) {
    this.rotation = rotation;
    return true;
  }

  @Implementation
  public float getRotation() {
    return rotation;
  }

  @Implementation
  public boolean setRotationX(float rotationX) {
    this.rotationX = rotationX;
    return true;
  }

  @Implementation
  public float getRotationX() {
    return rotationX;
  }

  @Implementation
  public boolean setRotationY(float rotationY) {
    this.rotationY = rotationY;
    return true;
  }

  @Implementation
  public float getRotationY() {
    return rotationY;
  }

  @Implementation
  public boolean setScaleX(float scaleX) {
    this.scaleX = scaleX;
    return true;
  }

  @Implementation
  public float getScaleX() {
    return scaleX;
  }

  @Implementation
  public boolean setScaleY(float scaleY) {
    this.scaleY = scaleY;
    return true;
  }

  @Implementation
  public float getScaleY() {
    return scaleY;
  }

  @Implementation
  public boolean setTranslationX(float translationX) {
    this.translationX = translationX;
    return true;
  }

  @Implementation
  public boolean setTranslationY(float translationY) {
    this.translationY = translationY;
    return true;
  }

  @Implementation
  public boolean setTranslationZ(float translationZ) {
    this.translationZ = translationZ;
    return true;
  }

  @Implementation
  public float getTranslationX() {
    return translationX;
  }

  @Implementation
  public float getTranslationY() {
    return translationY;
  }

  @Implementation
  public float getTranslationZ() {
    return translationZ;
  }

  @Implementation
  public boolean isPivotExplicitlySet() {
    return pivotExplicitlySet;
  }

  @Implementation
  public boolean resetPivot() {
    this.pivotExplicitlySet = false;
    this.pivotX = 0;
    this.pivotY = 0;
    return true;
  }

  @Implementation
  public boolean setPivotX(float pivotX) {
    this.pivotX = pivotX;
    this.pivotExplicitlySet = true;
    return true;
  }

  @Implementation
  public float getPivotX() {
    return pivotX;
  }

  @Implementation
  public boolean setPivotY(float pivotY) {
    this.pivotY = pivotY;
    this.pivotExplicitlySet = true;
    return true;
  }

  @Implementation
  public float getPivotY() {
    return pivotY;
  }

  @Implementation
  public boolean setLeft(int left) {
    this.left = left;
    return true;
  }

  @Implementation
  public int getLeft() {
    return left;
  }

  @Implementation
  public boolean setTop(int top) {
    this.top = top;
    return true;
  }

  @Implementation
  public int getTop() {
    return top;
  }

  @Implementation
  public boolean setRight(int right) {
    this.right = right;
    return true;
  }

  @Implementation
  public int getRight() {
    return right;
  }

  @Implementation
  public boolean setBottom(int bottom) {
    this.bottom = bottom;
    return true;
  }

  @Implementation
  public int getBottom() {
    return bottom;
  }

  @Implementation
  public int getWidth() {
    return right - left;
  }

  @Implementation
  public int getHeight() {
    return bottom - top;
  }

  @Implementation
  public boolean setLeftTopRightBottom(int left, int top, int right, int bottom) {
    return setPosition(left, top, right, bottom);
  }

  @Implementation
  public boolean setPosition(int left, int top, int right, int bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    return true;
  }

  @Implementation
  public boolean setPosition(Rect position) {
    this.left = position.left;
    this.top = position.top;
    this.right = position.right;
    this.bottom = position.bottom;
    return true;
  }

  @Implementation
  public boolean offsetLeftAndRight(int offset) {
    this.left += offset;
    this.right += offset;
    return true;
  }

  @Implementation
  public boolean offsetTopAndBottom(int offset) {
    this.top += offset;
    this.bottom += offset;
    return true;
  }

  @Implementation
  public void getInverseMatrix(Matrix matrix) {
    getMatrix(matrix);
    matrix.invert(matrix);
  }

  @Implementation
  public void getMatrix(Matrix matrix) {
    if (!pivotExplicitlySet) {
      pivotX = getWidth() / 2f;
      pivotY = getHeight() / 2f;
    }
    matrix.reset();
    if (rotationX == 0 && rotationY == 0) {
      matrix.setTranslate(translationX, translationY);
      matrix.preRotate(rotation, pivotX, pivotY);
      matrix.postScale(scaleX, scaleY, pivotX, pivotY);
    } else {
      matrix.preScale(scaleX, scaleY, pivotX, pivotY);
      Camera camera = new Camera();
      camera.rotateX(rotationX);
      camera.rotateY(rotationY);
      camera.rotateZ(-rotation);
      Matrix transform = new Matrix();
      camera.getMatrix(transform);
      transform.preTranslate(-pivotX, -pivotY);
      transform.postTranslate(pivotX + translationX, pivotY + translationY);
      matrix.postConcat(transform);
    }
  }

  @Implementation
  public boolean hasIdentityMatrix() {
    Matrix matrix = new Matrix();
    getMatrix(matrix);
    return matrix.isIdentity();
  }

  @Implementation
  protected boolean isValid() {
    return true;
  }

  /**
   * Implementation of native method nSetLayerType
   *
   * @param renderNode Ignored
   * @param layerType Ignored
   * @return Always true
   */
  @Implementation
  protected static boolean nSetLayerType(long renderNode, int layerType) {
    return true;
  }

  /**
   * Implementation of native method nSetLayerPaint
   *
   * @param renderNode Ignored
   * @param paint Ignored
   * @return Always true
   */
  @Implementation
  protected static boolean nSetLayerPaint(long renderNode, long paint) {
    return true;
  }
}
