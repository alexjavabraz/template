package br.com.bjbraz.app.estabelecimentos.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;;

/**
 * Dao JDBC que utiliza SimpleJdbcDaoSupport para acessar o Banco.
 * @version $VERSION
 */
public abstract class GenericJdbcDao extends JdbcDaoSupport {

    @Autowired
    public void initDataSource(DataSource dataSource) {
        setDataSource(dataSource);
    }

}
