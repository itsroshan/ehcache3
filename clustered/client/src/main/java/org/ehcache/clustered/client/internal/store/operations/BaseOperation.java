/*
 * Copyright Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ehcache.clustered.client.internal.store.operations;

/**
 * Base class that represents a {@link org.ehcache.Cache} operation
 *
 * @param <K> key type
 */
public abstract class BaseOperation<K> {

  protected final K key;

  public BaseOperation(final K key) {
    this.key = key;
  }

  public abstract OperationCode getOpCode();

  public K getKey() {
    return key;
  }
}
