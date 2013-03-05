package it.fe.cllmhl.sql;

import it.fe.cllmhl.Installation;
import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.util.StringUtil;
import it.fe.cllmhl.util.XMLUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

final class DatasourceManager {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(DatasourceManager.class);

    private static Map<String, DataSource> mDatasources = new HashMap<String, DataSource>();

    static {
        initialize();
    }

    private static DatasourceBean buildDatasourceBean(String pStrDatasorceName, NodeList pNodeList) {
        mLogger.debug("Start buildDatasourceBean ", pStrDatasorceName);
        DatasourceBean lDatasourceBean = new DatasourceBean();
        lDatasourceBean.setName(pStrDatasorceName);
        for (int i = 0; i < pNodeList.getLength(); i++) {
            Element lElement = (Element) pNodeList.item(i);
            // type <type>POOL</type>
            String lStrValue = extractValueFromElement(lElement, "type");
            if (lStrValue != null) {
                lDatasourceBean.setType(DatasourceBean.DatasourceType.valueOf(lStrValue));
            }

            // jndiName <jndiName>java:comp/env/jdbc/et</jndiName>
            lDatasourceBean.setJndiName(extractValueFromElement(lElement, "jndiName"));
            
            // driverClassName <driverClassName>com.mysql.jdbc.Driver</driverClassName>
            lDatasourceBean.setDriverClassName(extractValueFromElement(lElement, "driverClassName"));

            // url <url>jdbc:mysql://localhost:3306/test</url>
            lDatasourceBean.setUrl(extractValueFromElement(lElement, "url"));

            // username <username>root</username>
            lDatasourceBean.setUsername(extractValueFromElement(lElement, "username"));

            // password <password>root</password>
            lDatasourceBean.setPassword(extractValueFromElement(lElement, "password"));

            // initialSize <initialSize>2</initialSize>
            lDatasourceBean.setInitialSize(extractValueFromElement(lElement, "initialSize"));

            // maxActive <maxActive>2</maxActive>
            lDatasourceBean.setMaxActive(extractValueFromElement(lElement, "maxActive"));

            // minIdle <minIdle>2</minIdle>
            lDatasourceBean.setMinIdle(extractValueFromElement(lElement, "minIdle"));

            // validationQuery <validationQuery>SELECT 1</validationQuery>
            lDatasourceBean.setValidationQuery(extractValueFromElement(lElement, "validationQuery"));
        }
        mLogger.debug("Finish buildDatasourceBean", lDatasourceBean);
        return lDatasourceBean;
    }

    private static String extractValueFromElement(Element lElement, String pStrName) {
        String lStrValue = XMLUtil.extractValueByXpath(lElement, pStrName);
        mLogger.debug(pStrName , ":", lStrValue);
        if (StringUtil.isNotBlank(lStrValue)) {
            return lStrValue;
        }
        return null;
    }

    private static DataSource buildPooledDatasource(DatasourceBean pDatasourceBean) {
        mLogger.info("Start buildPooledDatasource ", pDatasourceBean);
        PoolProperties lPoolProperties = new PoolProperties();
        lPoolProperties.setUrl(pDatasourceBean.getUrl());
        lPoolProperties.setDriverClassName(pDatasourceBean.getDriverClassName());
        lPoolProperties.setUsername(pDatasourceBean.getUsername());
        lPoolProperties.setPassword(pDatasourceBean.getPassword());
        lPoolProperties.setValidationQuery(pDatasourceBean.getValidationQuery());
        lPoolProperties.setMaxActive(Integer.valueOf(pDatasourceBean.getMaxActive()));
        lPoolProperties.setInitialSize(Integer.valueOf(pDatasourceBean.getInitialSize()));
        lPoolProperties.setMinIdle(Integer.valueOf(pDatasourceBean.getMinIdle()));
        DataSource lDatasource = new org.apache.tomcat.jdbc.pool.DataSource(lPoolProperties);
        mLogger.info("Finish buildPooledDatasource ", pDatasourceBean);
        return lDatasource;
    }

    public static Connection getConnection(String pStrConnectionId) {
        try {
            return mDatasources.get(pStrConnectionId).getConnection();
        } catch (SQLException e) {
            mLogger.error(e);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());

        }
    }

    private static DataSource getDatasourceFromContainer(DatasourceBean pDatasourceBean) throws NamingException {
        mLogger.info("Start getDatasourceFromContainer ", pDatasourceBean);
        InitialContext lInitialContext = new InitialContext();
        DataSource lDataSource = (DataSource) lInitialContext.lookup(pDatasourceBean.getJndiName());
        mLogger.info("Finish getDatasourceFromContainer", pDatasourceBean);
        return lDataSource;
    }

    private static void initialize() {
        List<DatasourceBean> lDatasources = readConfiguration();
        for (DatasourceBean lDatasourceBean : lDatasources) {
            if (lDatasourceBean.getType() == DatasourceBean.DatasourceType.CONTAINER) {
                try {
                    mDatasources.put(lDatasourceBean.getName(), getDatasourceFromContainer(lDatasourceBean));
                } catch (NamingException e) {
                    mLogger.error(e);
                }
            }
            if (lDatasourceBean.getType() == DatasourceBean.DatasourceType.POOL) {
                mDatasources.put(lDatasourceBean.getName(), buildPooledDatasource(lDatasourceBean));
            }
        }
    }

    private static List<DatasourceBean> readConfiguration() {
        mLogger.debug("Start readConfiguration ");
        File lFileDatasources = new File(Installation.getConfigurationDirectory().concat("datasources.xml"));
        Document lDocument = XMLUtil.readXmlFile(lFileDatasources);
        NodeList lNodeList = XMLUtil.extractNodesByXpath(lDocument, "//datasource");
        List<DatasourceBean> lDatasourceList = new ArrayList<DatasourceBean>();
        for (int i = 0; i < lNodeList.getLength(); i++) {
            Element lElement = (Element) lNodeList.item(i);
            String lStrDatasourceName = XMLUtil.extractValueByXpath(lElement, "@name");
            mLogger.debug("Found datasource ", lStrDatasourceName);
            DatasourceBean lDatasourceBean = buildDatasourceBean(lStrDatasourceName, XMLUtil.extractNodesByXpath(lDocument, "//datasource[@name='" + lStrDatasourceName + "']"));
            lDatasourceList.add(lDatasourceBean);
        }
        mLogger.debug("Finish readConfiguration");
        return lDatasourceList;
    }

    private DatasourceManager(){}
}
