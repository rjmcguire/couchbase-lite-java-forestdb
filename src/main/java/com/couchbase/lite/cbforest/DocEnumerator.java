/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.couchbase.lite.cbforest;

public class DocEnumerator {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected DocEnumerator(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(DocEnumerator obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        cbforestJNI.delete_DocEnumerator(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  static public class Options {
    private transient long swigCPtr;
    protected transient boolean swigCMemOwn;
  
    protected Options(long cPtr, boolean cMemoryOwn) {
      swigCMemOwn = cMemoryOwn;
      swigCPtr = cPtr;
    }
  
    protected static long getCPtr(Options obj) {
      return (obj == null) ? 0 : obj.swigCPtr;
    }
  
    protected void finalize() {
      delete();
    }
  
    public synchronized void delete() {
      if (swigCPtr != 0) {
        if (swigCMemOwn) {
          swigCMemOwn = false;
          cbforestJNI.delete_DocEnumerator_Options(swigCPtr);
        }
        swigCPtr = 0;
      }
    }
  
    public Options() {
      this(cbforestJNI.new_DocEnumerator_Options(), true);
    }
  
    public ContentOptions getContentOption() {
      return ContentOptions.swigToEnum(cbforestJNI.DocEnumerator_Options_getContentOption(swigCPtr, this));
    }
  
    public void setContentOption(ContentOptions arg0) {
      cbforestJNI.DocEnumerator_Options_setContentOption(swigCPtr, this, arg0.swigValue());
    }
  
    public boolean isIncludeDeleted() {
      return cbforestJNI.DocEnumerator_Options_isIncludeDeleted(swigCPtr, this);
    }
  
    public void setIncludeDeleted(boolean arg0) {
      cbforestJNI.DocEnumerator_Options_setIncludeDeleted(swigCPtr, this, arg0);
    }
  
    public boolean isInclusiveEnd() {
      return cbforestJNI.DocEnumerator_Options_isInclusiveEnd(swigCPtr, this);
    }
  
    public void setInclusiveEnd(boolean arg0) {
      cbforestJNI.DocEnumerator_Options_setInclusiveEnd(swigCPtr, this, arg0);
    }
  
    public boolean isInclusiveStart() {
      return cbforestJNI.DocEnumerator_Options_isInclusiveStart(swigCPtr, this);
    }
  
    public void setInclusiveStart(boolean arg0) {
      cbforestJNI.DocEnumerator_Options_setInclusiveStart(swigCPtr, this, arg0);
    }
  
    public long getLimit() {
      return cbforestJNI.DocEnumerator_Options_getLimit(swigCPtr, this);
    }
  
    public void setLimit(long arg0) {
      cbforestJNI.DocEnumerator_Options_setLimit(swigCPtr, this, arg0);
    }
  
    public long getSkip() {
      return cbforestJNI.DocEnumerator_Options_getSkip(swigCPtr, this);
    }
  
    public void setSkip(long arg0) {
      cbforestJNI.DocEnumerator_Options_setSkip(swigCPtr, this, arg0);
    }
  
    public boolean isDescending() {
      return cbforestJNI.DocEnumerator_Options_isDescending(swigCPtr, this);
    }
  
    public void setDescending(boolean arg0) {
      cbforestJNI.DocEnumerator_Options_setDescending(swigCPtr, this, arg0);
    }
  
  }

  public DocEnumerator() throws java.lang.Exception {
    this(cbforestJNI.new_DocEnumerator__SWIG_0(), true);
  }

  public DocEnumerator(KeyStore store, Slice startKey, Slice endKey, DocEnumerator.Options options) throws java.lang.Exception {
    this(cbforestJNI.new_DocEnumerator__SWIG_1(KeyStore.getCPtr(store), store, Slice.getCPtr(startKey), startKey, Slice.getCPtr(endKey), endKey, DocEnumerator.Options.getCPtr(options), options), true);
  }

  public DocEnumerator(KeyStore store, VectorString docIDs, DocEnumerator.Options options) throws java.lang.Exception {
    this(cbforestJNI.new_DocEnumerator__SWIG_2(KeyStore.getCPtr(store), store, VectorString.getCPtr(docIDs), docIDs, DocEnumerator.Options.getCPtr(options), options), true);
  }

  public DocEnumerator(KeyStore store, java.math.BigInteger start, java.math.BigInteger end, DocEnumerator.Options options) throws java.lang.Exception {
    this(cbforestJNI.new_DocEnumerator__SWIG_3(KeyStore.getCPtr(store), store, start, end, DocEnumerator.Options.getCPtr(options), options), true);
  }

  public boolean next() throws java.lang.Exception {
    return cbforestJNI.DocEnumerator_next(swigCPtr, this);
  }

  public void seek(Slice key) throws java.lang.Exception {
    cbforestJNI.DocEnumerator_seek(swigCPtr, this, Slice.getCPtr(key), key);
  }

  public Document doc() throws java.lang.Exception {
    long cPtr = cbforestJNI.DocEnumerator_doc(swigCPtr, this);
    return (cPtr == 0) ? null : new Document(cPtr, false);
  }

  public void close() {
    cbforestJNI.DocEnumerator_close(swigCPtr, this);
  }

}
