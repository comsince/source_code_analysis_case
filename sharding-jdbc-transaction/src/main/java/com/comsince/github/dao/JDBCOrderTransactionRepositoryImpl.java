package com.comsince.github.dao;

import com.comsince.github.dao.entity.Order;
import com.comsince.github.dao.impl.JDBCOrderRepositoryImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-30 上午10:50
 **/
public final class JDBCOrderTransactionRepositoryImpl implements OrderRepository {

    private final JDBCOrderRepositoryImpl jdbcOrderRepository;

    private Connection insertConnection;

    public JDBCOrderTransactionRepositoryImpl(final DataSource dataSource) {
        this.jdbcOrderRepository = new JDBCOrderRepositoryImpl(dataSource);
    }

    @Override
    public void createTableIfNotExists() {
        jdbcOrderRepository.createTableIfNotExists();
    }

    @Override
    public void dropTable() {
        jdbcOrderRepository.dropTable();
    }

    @Override
    public void truncateTable() {
        jdbcOrderRepository.truncateTable();
    }

    @Override
    public Long insert(final Order order) {
        String sql = "INSERT INTO t_order (user_id, status) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = insertConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setString(2, order.getStatus());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    order.setOrderId(resultSet.getLong(1));
                }
            }
        } catch (final SQLException ignored) {
        }
        return order.getOrderId();
    }

    @Override
    public void delete(final Long id) {
        jdbcOrderRepository.delete(id);
    }

    @Override
    public List<Order> selectAll() {
        return jdbcOrderRepository.selectAll();
    }

    @Override
    public List<Order> selectRange() {
        return jdbcOrderRepository.selectRange();
    }

    public void setInsertConnection(final Connection insertConnection) {
        this.insertConnection = insertConnection;
    }
}