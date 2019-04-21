package com.homework7.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Realization object output stream for appending object to file.
 * This do, because before appending new data to file ObjectOutputStream write
 * header AC, which cause StreamCorruptedException in reading
 */
public class AppendingObjectOutputStream extends ObjectOutputStream {
  public AppendingObjectOutputStream(OutputStream out) throws IOException {
    super(out);
  }

  @Override
  protected void writeStreamHeader() throws IOException {
    reset();
  }
}
