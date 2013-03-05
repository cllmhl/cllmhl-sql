package it.fe.cllmhl.sql;

import it.fe.cllmhl.util.BaseBean;

class DatasourceBean extends BaseBean {

    public enum DatasourceType {
        CONTAINER, POOL
    };

    private String name;
    private DatasourceType type;
    // For containers
    private String jndiName;
    // For pools
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String initialSize;
    private String maxActive;
    private String minIdle;
    private String validationQuery;

    public final String getDriverClassName() {
        return driverClassName;
    }

    public String getInitialSize() {
        return initialSize;
    }

    public final String getJndiName() {
        return jndiName;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public final String getName() {
        return name;
    }

    public final String getPassword() {
        return password;
    }

    public final DatasourceType getType() {
        return type;
    }

    public final String getUrl() {
        return url;
    }

    public final String getUsername() {
        return username;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public final void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setInitialSize(String initialSize) {
        this.initialSize = initialSize;
    }

    public final void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public final void setType(DatasourceType type) {
        this.type = type;
    }

    public final void setUrl(String url) {
        this.url = url;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }
}
