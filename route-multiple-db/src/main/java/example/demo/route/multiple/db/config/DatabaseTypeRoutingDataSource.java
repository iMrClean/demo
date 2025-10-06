package example.demo.route.multiple.db.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DatabaseTypeRoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<DatabaseType> CONTEXT_HOLDER = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return CONTEXT_HOLDER.get();
    }

    public static void setDatabase(DatabaseType database) {
        CONTEXT_HOLDER.set(database);
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

}
