/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.couchbase.lite.cbforest;

public class KvsInfo {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected KvsInfo(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(KvsInfo obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        cbforestJNI.delete_KvsInfo(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public KvsInfo() {
    this(cbforestJNI.new_KvsInfo(), true);
  }

  public String getName() {
    return cbforestJNI.KvsInfo_getName(swigCPtr, this);
  }

  public java.math.BigInteger getLastSeqnum() {
    return cbforestJNI.KvsInfo_getLastSeqnum(swigCPtr, this);
  }

  public java.math.BigInteger getDocCount() {
    return cbforestJNI.KvsInfo_getDocCount(swigCPtr, this);
  }

  public java.math.BigInteger getSpaceUsed() {
    return cbforestJNI.KvsInfo_getSpaceUsed(swigCPtr, this);
  }

}
