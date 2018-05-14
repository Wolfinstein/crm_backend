package com.crm.application.repository.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.crm.application.model.Client;
import com.crm.application.repository.ClientRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ClientDaoImpl extends JdbcDaoSupport implements ClientDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);
    final private DataSource dataSource;
    final private ClientRepository clientRepository;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ClientDaoImpl(@Qualifier("dataSource") DataSource dataSource, ClientRepository clientRepository) {
        this.dataSource = dataSource;
        this.clientRepository = clientRepository;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void insert(List<? extends Client> Clients) {

        for (Client client : Clients) {
            if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
                Clients.remove(client);
            }
        }

        String sql = "INSERT INTO clients " + "(client_type, phone, email,description,first_name,last_name,pesel,name,nip,regon,type,website,trade, create_timestamp)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Client client = Clients.get(i);
                ps.setString(1, client.getClient_type());
                ps.setString(2, client.getPhone());
                ps.setString(3, client.getEmail());
                ps.setString(4, client.getDescription());
                ps.setString(5, client.getFirstName());
                ps.setString(6, client.getLastName());
                ps.setString(7, client.getPesel());
                ps.setString(8, client.getName());
                ps.setString(9, client.getNip());
                ps.setString(10, client.getRegon());
                ps.setString(11, client.getType());
                ps.setString(12, client.getWebsite());
                ps.setString(13, client.getTrade());
                ps.setDate(14, Date.valueOf(LocalDate.now()));
            }

            public int getBatchSize() {
                return Clients.size();
            }
        });
    }


}

