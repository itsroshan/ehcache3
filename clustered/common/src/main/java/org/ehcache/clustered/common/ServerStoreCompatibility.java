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

package org.ehcache.clustered.common;

/**
 * Provides configuration compatibility checks for {@link ServerStoreConfiguration}
 * between client and server specifications.
 */
public class ServerStoreCompatibility {

  /**
   * Ensure compatibility of a client {@link ServerStoreConfiguration} with an existing
   * server-side {@code ServerStoreConfiguration}.
   *
   * @param serverConfiguration the existing server-side {@code ServerStoreConfiguration}
   * @param clientConfiguration the desired client-side {@code ServerStoreConfiguration}
   *
   * @throws ClusteredStoreValidationException if {@code clientConfiguration} is not compatible with
   *          {@code serverConfiguration}
   */
  public void verify(ServerStoreConfiguration serverConfiguration, ServerStoreConfiguration clientConfiguration)
      throws ClusteredStoreValidationException {
    StringBuilder sb = new StringBuilder("Existing ServerStore configuration is not compatible with the desired configuration: ");

    boolean isCompatible;
    isCompatible = compareField(sb, "resourcePoolType",
        serverConfiguration.getPoolAllocation().getClass().getName(),
        clientConfiguration.getPoolAllocation().getClass().getName());

    isCompatible &= compareField(sb, "storedKeyType", serverConfiguration.getStoredKeyType(), clientConfiguration.getStoredKeyType());
    isCompatible &= compareField(sb, "storedValueType", serverConfiguration.getStoredValueType(), clientConfiguration.getStoredValueType());
    isCompatible &= compareField(sb, "actualKeyType", serverConfiguration.getActualKeyType(), clientConfiguration.getActualKeyType());
    isCompatible &= compareField(sb, "actualValueType", serverConfiguration.getActualValueType(), clientConfiguration.getActualValueType());
    isCompatible &= compareField(sb, "keySerializerType", serverConfiguration.getKeySerializerType(), clientConfiguration.getKeySerializerType());
    isCompatible &= compareField(sb, "valueSerializerType", serverConfiguration.getValueSerializerType(), clientConfiguration.getValueSerializerType());

    if (!isCompatible) {
      throw new ClusteredStoreValidationException(sb.toString());
    }
  }

  private static boolean compareField(StringBuilder sb, String fieldName, String serverConfigValue, String clientConfigValue) {
    if ((serverConfigValue == null && clientConfigValue == null)
        || (serverConfigValue != null && serverConfigValue.equals(clientConfigValue))) {
      return true;
    }

    sb.append("\n\t").append(fieldName)
        .append(" existing: ").append(serverConfigValue)
        .append(" desired: ").append(clientConfigValue);

    return false;
  }
}
