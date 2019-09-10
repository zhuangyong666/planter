package cn.edu.tjpu.utils;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DruidDataSourceFactory implements DataSourceFactory {
    private Properties props;
    private DruidDataSource dds;

    @Override
    public DataSource getDataSource() {
        if (dds == null) {
            dds = new DruidDataSource();
            dds.setDriverClassName(props.getProperty("driverClassName"));
            dds.setUrl(props.getProperty("url"));
            dds.setUsername(props.getProperty("username"));
            dds.setPassword(props.getProperty("password"));
            //配置初始化大小、最小、最大
            dds.setInitialSize(Integer.parseInt(props.getProperty("initialSize")));
            dds.setMinIdle(Integer.parseInt(props.getProperty("minIdle")));
            dds.setMaxActive(Integer.parseInt(props.getProperty("maxActive")));
            //配置获取连接等待超时的时间
            dds.setMaxWait(Integer.parseInt(props.getProperty("maxWait")));
            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            dds.setTimeBetweenEvictionRunsMillis(Integer.parseInt(props.getProperty("timeBetweenEvictionRunsMillis")));
            //配置一个连接在池中最小生存的时间，单位是毫秒
            dds.setMinEvictableIdleTimeMillis(Integer.parseInt(props.getProperty("minEvictableIdleTimeMillis")));
            dds.setTestWhileIdle(Boolean.parseBoolean(props.getProperty("testWhileIdle")));
            //这里建议配置为TRUE，防止取到的连接不可用
            dds.setTestOnBorrow(Boolean.parseBoolean(props.getProperty("testOnBorrow")));
            dds.setTestOnReturn(Boolean.parseBoolean(props.getProperty("testOnReturn")));
            //打开PSCache，并且指定每个连接上PSCache的大小
            dds.setPoolPreparedStatements(Boolean.parseBoolean(props.getProperty("poolPreparedStatements")));
            dds.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(props.getProperty("maxPoolPreparedStatementPerConnectionSize")));
            //这里配置提交方式，默认就是TRUE，可以不用配置
            dds.setDefaultAutoCommit(Boolean.parseBoolean(props.getProperty("defaultAutoCommit")));
            //验证连接有效与否的SQL，不同的数据配置不同
            dds.setValidationQuery(props.getProperty("validationQuery"));
            Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
            List<Filter> filters = new ArrayList<>();
            dds.setProxyFilters(filters);
            try {
                dds.setFilters(props.getProperty("filters"));
                dds.init();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dds;
    }

    @Override
    public void setProperties(Properties props) {
        this.props = props;
    }
}