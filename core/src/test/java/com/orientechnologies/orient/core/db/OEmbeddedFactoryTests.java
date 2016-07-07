package com.orientechnologies.orient.core.db;

import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.exception.OStorageExistsException;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by tglman on 08/04/16.
 */
public class OEmbeddedFactoryTests {

  @Test
  public void createAndUseEmbeddedDatabase() {
    OrientDBFactory factory = OrientDBFactory.embedded(".", null);
    //    OrientDBFactory factory = OrientDBFactory.fromUrl("local:.", null);

    if (!factory.exist("test", "", ""))
      factory.create("test", "", "", OrientDBFactory.DatabaseType.MEMORY);

    ODatabaseDocument db = factory.open("test", "admin", "admin");
    db.save(new ODocument());
    db.close();
    factory.close();

  }

  @Test(expected = OStorageExistsException.class)
  public void testEmbeddedDoubleCreate() {
    OrientDBFactory factory = OrientDBFactory.embedded(".", null);
    try {
      factory.create("test", "", "", OrientDBFactory.DatabaseType.MEMORY);
      factory.create("test", "", "", OrientDBFactory.DatabaseType.MEMORY);
    } finally {
      factory.close();
    }
  }

  @Test
  public void createDropEmbeddedDatabase() {
    OrientDBFactory factory = OrientDBFactory.embedded(".", null);
    //    OrientDBFactory factory = OrientDBFactory.fromUrl("remote:localhost", null);
    try {
      factory.create("test", "", "", OrientDBFactory.DatabaseType.MEMORY);
      assertTrue(factory.exist("test", "", ""));
      factory.drop("test", "", "");
      assertFalse(factory.exist("test", "", ""));
    } finally {
      factory.close();
    }
  }

  @Test
  public void testPool() {
    OrientDBFactory factory = OrientDBFactory.embedded(".", null);
    //    OrientDBFactory factory = OrientDBFactory.fromUrl("local:.", null);

    if (!factory.exist("test", "", ""))
      factory.create("test", "", "", OrientDBFactory.DatabaseType.MEMORY);

    OPool<ODatabaseDocument> pool = factory.openPool("test", "admin", "admin", null);
    ODatabaseDocument db = pool.acquire();
    db.save(new ODocument());
    db.close();
    pool.close();
    factory.close();
  }

  @Test
  public void testListDatabases() {
    OrientDBFactory factory = OrientDBFactory.embedded(".", null);
    //    OrientDBFactory factory = OrientDBFactory.fromUrl("local:.", null);
    assertEquals(factory.listDatabases("", "").size(), 0);
    factory.create("test", "", "", OrientDBFactory.DatabaseType.MEMORY);
    Set<String> databases = factory.listDatabases("", "");
    assertEquals(databases.size(), 1);
    assertTrue(databases.contains("test"));
  }

}
