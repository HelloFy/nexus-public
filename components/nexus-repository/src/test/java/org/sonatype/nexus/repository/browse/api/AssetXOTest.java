/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.repository.browse.api;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.Asset;

import com.orientechnologies.orient.core.id.ORID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

public class AssetXOTest
    extends TestSupport
{
  @Mock
  Repository repository;

  @Mock
  Asset assetOne;

  @Mock
  ORID assetOneORID;

  @Mock
  EntityMetadata assetOneEntityMetadata;

  @Mock
  EntityId assetOneEntityId;

  @Before
  public void setup() {
    when(assetOne.name()).thenReturn("nameOne");
    when(assetOne.getEntityMetadata()).thenReturn(assetOneEntityMetadata);

    when(assetOneORID.toString()).thenReturn("assetOneORID");

    when(assetOneEntityMetadata.getId()).thenReturn(assetOneEntityId);
    when(assetOneEntityId.getValue()).thenReturn("assetOne");

    when(repository.getName()).thenReturn("maven-releases");
    when(repository.getUrl()).thenReturn("http://localhost:8081/repository/maven-releases");
    when(repository.getFormat()).thenReturn(new Format("maven2") {});
  }

  @Test
  public void fromAsset() {
    AssetXO assetXO = AssetXO.fromAsset(assetOne, repository);

    assertThat(assetXO.getId(), notNullValue());
    assertThat(assetXO.getPath(), is("nameOne"));
    assertThat(assetXO.getDownloadUrl(), is("http://localhost:8081/repository/maven-releases/nameOne"));
  }
}
