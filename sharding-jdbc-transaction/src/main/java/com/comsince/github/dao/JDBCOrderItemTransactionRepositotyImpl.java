package com.comsince.github.dao;

import com.comsince.github.dao.entity.OrderItem;
import com.comsince.github.dao.impl.JDBCOrderItemRepositoryImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-30 上午10:48
 **/
public final class JDBCOrderItemTransactionRepositotyImpl implements OrderItemRepository {

    private final JDBCOrderItemRepositoryImpl jdbcOrderItemRepository;

    private Connection insertConnection;

    public JDBCOrderItemTransactionRepositotyImpl(final DataSource dataSource) {
        this.jdbcOrderItemRepository = new JDBCOrderItemRepositoryImpl(dataSource);
    }

    @Override
    public void createTableIfNotExists() {
        jdbcOrderItemRepository.createTableIfNotExists();
    }

    @Override
    public void dropTable() {
        jdbcOrderItemRepository.dropTable();
    }

    @Override
    public void truncateTable() {
        jdbcOrderItemRepository.truncateTable();
    }

    @Override
    public Long insert(final OrderItem orderItem) {
        String sql = "INSERT INTO t_order_item (order_id, user_id, status) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = insertConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, orderItem.getOrderId());
            preparedStatement.setInt(2, orderItem.getUserId());
            preparedStatement.setString(3, orderItem.getStatus());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    orderItem.setOrderItemId(resultSet.getLong(1));
                }
            }
        } catch (final SQLException ignored) {
        }
        return orderItem.getOrderItemId();
    }

    @Override
    public void delete(final Long id) {
        jdbcOrderItemRepository.delete(id);
    }

    @Override
    public List<OrderItem> selectAll() {
        return jdbcOrderItemRepository.selectAll();
    }

    @Override
    public List<OrderItem> selectRange() {
        return jdbcOrderItemRepository.selectRange();
    }

    public void setInsertConnection(final Connection insertConnection) {
        this.insertConnection = insertConnection;
    }

}
