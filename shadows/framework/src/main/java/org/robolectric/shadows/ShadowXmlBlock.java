package org.robolectric.shadows;

import static org.robolectric.res.android.Errors.NO_ERROR;

import android.os.Build.VERSION_CODES;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.res.android.ResXMLParser;
import org.robolectric.res.android.ResXMLTree;
import org.robolectric.res.android.ResourceTypes.Res_value;
import org.xmlpull.v1.XmlPullParserException;

@Implements(className = "android.content.res.XmlBlock", isInAndroidSdk = false)
public class ShadowXmlBlock {
  static final NativeObjRegistry<ResXMLTree> NATIVE_RES_XML_TREES = new NativeObjRegistry<>();
  static final NativeObjRegistry<ResXMLParser> NATIVE_RES_XML_PARSERS = new NativeObjRegistry<>();

  @Implementation
  public static long nativeCreate(byte[] bArray, int off, int len) {
    if (bArray == null) {
      throw new NullPointerException();
    }

    int bLen = bArray.length;
    if (off < 0 || off >= bLen || len < 0 || len > bLen || (off+len) > bLen) {
      throw new IndexOutOfBoundsException();
    }

    // todo: optimize
    byte[] b = new byte[len];
    System.arraycopy(bArray, off, b, 0, len);

    ResXMLTree osb = new ResXMLTree(null);
    osb.setTo(b, len, true);
//    env->ReleaseByteArrayElements(bArray, b, 0);

    if (osb.getError() != NO_ERROR) {
      throw new IllegalArgumentException();
    }

    return NATIVE_RES_XML_TREES.getNativeObjectId(osb);
  }

  // todo: implement pre-Lollipop

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static long nativeGetStringBlock(long obj) {
    ResXMLTree osb = NATIVE_RES_XML_TREES.getNativeObject(obj);
//    if (osb == NULL) {
//      jniThrowNullPointerException(env, NULL);
//      return 0;
//    }

    return ShadowStringBlock.getNativePointer(osb.getStrings());
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static long nativeCreateParseState(long obj) {
    ResXMLTree osb = NATIVE_RES_XML_TREES.getNativeObject(obj);
//    if (osb == NULL) {
//      jniThrowNullPointerException(env, NULL);
//      return 0;
//    }

//    ResXMLParser st = new ResXMLParser(osb);
    ResXMLParser st = osb.mParser;
//    if (st == NULL) {
//      jniThrowException(env, "java/lang/OutOfMemoryError", NULL);
//      return 0;
//    }

    st.restart();

    return NATIVE_RES_XML_PARSERS.getNativeObjectId(st);
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeNext(long state) throws XmlPullParserException {
    ResXMLTree st = NATIVE_RES_XML_TREES.getNativeObject(state);
    if (st == null) {
      return ResXMLParser.event_code_t.END_DOCUMENT;
    }

    do {
      int code = st.next();
      switch (code) {
        case ResXMLParser.event_code_t.START_TAG:
          return 2;
        case ResXMLParser.event_code_t.END_TAG:
          return 3;
        case ResXMLParser.event_code_t.TEXT:
          return 4;
        case ResXMLParser.event_code_t.START_DOCUMENT:
          return 0;
        case ResXMLParser.event_code_t.END_DOCUMENT:
          return 1;
        case ResXMLParser.event_code_t.BAD_DOCUMENT:
//                goto bad;
          throw new XmlPullParserException("Corrupt XML binary file");
        default:
          break;
      }

    } while (true);
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetNamespace(long state) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getElementNamespaceID();
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetName(long state) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getElementNameID();
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetText(long state) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getTextID();
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetLineNumber(long state) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getLineNumber();
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetAttributeCount(long state) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getAttributeCount();
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetAttributeNamespace(long state, int idx) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getAttributeNamespaceID(idx);
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetAttributeName(long state, int idx) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getAttributeNameID(idx);
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetAttributeResource(long state, int idx) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getAttributeNameResID(idx);
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetAttributeDataType(long state, int idx) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getAttributeDataType(idx);
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetAttributeData(long state, int idx) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getAttributeData(idx);
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetAttributeStringValue(long state, int idx) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    return resXMLParser.getAttributeValueStringID(idx);
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetIdAttribute(long state) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    int idx = resXMLParser.indexOfID();
    return idx >= 0 ? resXMLParser.getAttributeValueStringID(idx) : -1;
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetClassAttribute(long state) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    int idx = resXMLParser.indexOfClass();
    return idx >= 0 ? resXMLParser.getAttributeValueStringID(idx) : -1;
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetStyleAttribute(long state) {
    ResXMLParser resXMLParser = getResXMLParser(state);
    int idx = resXMLParser.indexOfStyle();
    if (idx < 0) {
      return 0;
    }

    Res_value value = new Res_value();
    if (resXMLParser.getAttributeValue(idx, value) < 0) {
      return 0;
    }

    return value.dataType == org.robolectric.res.android.ResourceTypes.Res_value.TYPE_REFERENCE
        || value.dataType == org.robolectric.res.android.ResourceTypes.Res_value.TYPE_ATTRIBUTE
        ? value.data : 0;
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static int nativeGetAttributeIndex(long token, String ns, String name) {
    ResXMLParser st = getResXMLParser(token);
    if (st == null || name == null) {
      throw new NullPointerException();
    }

    int nsLen = 0;
    if (ns != null) {
      nsLen = ns.length();
    }

    return st.indexOfAttribute(ns, nsLen, name, name.length());
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static void nativeDestroyParseState(long state) {
    NATIVE_RES_XML_PARSERS.unregister(state);
  }

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  public static void nativeDestroy(long obj) {
    NATIVE_RES_XML_TREES.unregister(obj);
  }

  private static ResXMLParser getResXMLParser(long state) {
    return NATIVE_RES_XML_PARSERS.getNativeObject(state);
  }
}
