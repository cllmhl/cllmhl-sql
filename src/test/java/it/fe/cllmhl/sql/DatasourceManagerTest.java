package it.fe.cllmhl.sql;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

public class DatasourceManagerTest {

    @Test
    public void testContainer() {
        Connection lConnection = DatasourceManager.getConnection("CONTAINER");
        Assert.assertNotNull(lConnection);
    }

    @Test
    public void testPool() {
        Connection lConnection = DatasourceManager.getConnection("POOL");
        Assert.assertNotNull(lConnection);
    }
}
