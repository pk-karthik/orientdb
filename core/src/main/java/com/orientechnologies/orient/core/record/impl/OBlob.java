package com.orientechnologies.orient.core.record.impl;

import com.orientechnologies.orient.core.record.ORecord;
import com.orientechnologies.orient.core.serialization.OSerializableStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tglman on 05/01/16.
 */
public interface OBlob extends ORecord, OSerializableStream{
  byte RECORD_TYPE = 'b';

  int fromInputStream(final InputStream in) throws IOException;

  int fromInputStream(final InputStream in, final int maxSize) throws IOException;

  void toOutputStream(final OutputStream out) throws IOException;
}
