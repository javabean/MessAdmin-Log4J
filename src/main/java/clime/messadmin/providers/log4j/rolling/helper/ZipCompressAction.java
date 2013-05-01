/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package org.apache.log4j.rolling.helper;
package clime.messadmin.providers.log4j.rolling.helper;

import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.rolling.helper.ActionBase;

import clime.messadmin.utils.compress.zip.ZipUtils;

import java.io.File;
import java.io.IOException;


/**
 * Compresses a file using Zip compression.
 *
 * @author Curt Arnold
 * @author C&eacute;drik LIME
 */
public final class ZipCompressAction extends ActionBase {
  /**
   * Source file.
   */
  private final File source;

  /**
   * Destination file.
   */
  private final File destination;

  /**
   * If true, attempt to delete file on completion.
   */
  private final boolean deleteSource;


  /**
   * Create new instance of GZCompressAction.
   *
   * @param source file to compress, may not be null.
   * @param destination compressed file, may not be null.
   * @param deleteSource if true, attempt to delete file on completion.  Failure to delete
   * does not cause an exception to be thrown or affect return value.
   */
  public ZipCompressAction(
    final File source, final File destination, final boolean deleteSource) {
    if (source == null) {
      throw new NullPointerException("source");
    }

    if (destination == null) {
      throw new NullPointerException("destination");
    }

    this.source = source;
    this.destination = destination;
    this.deleteSource = deleteSource;
  }

  /**
   * Compress.
   * @return true if successfully compressed.
   * @throws IOException on IO exception.
   */
  public boolean execute() throws IOException {
    return execute(source, destination, deleteSource);
  }

  /**
   * Compress a file.
   *
   * @param source file to compress, may not be null.
   * @param destination compressed file, may not be null.
   * @param deleteSource if true, attempt to delete file on completion.  Failure to delete
   * does not cause an exception to be thrown or affect return value.
   * @return true if source file compressed.
   * @throws IOException on IO exception.
   */
  public static boolean execute(
    final File source, final File destination, final boolean deleteSource)
          throws IOException {
    if (source.exists()) {
      ZipUtils.compress(source, destination);

      if (deleteSource && !source.delete()) {
          LogLog.warn("Unable to delete " + source.toString() + ".");
      }

      return true;
    }

    return false;
  }

    /**
     * Capture exception.
     *
     * @param ex exception.
     */
    protected void reportException(final Exception ex) {
            LogLog.warn("Exception during compression of '" + source.toString() + "'.", ex);
    }
}
