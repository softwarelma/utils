package com.softwarelma.epe.p3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import com.softwarelma.epe.p1.app.EpeAppException;
import com.softwarelma.epe.p1.app.EpeAppUtils;
import com.softwarelma.epe.p2.exec.EpeExecParams;
import com.softwarelma.epe.p2.exec.EpeExecResult;

public final class EpeDbFinalDb_update extends EpeDbAbstract {

    private static final Map<String, DataSource> mapUrlAndDataSource = new HashMap<>();

    @Override
    public EpeExecResult doFunc(EpeExecParams execParams, List<EpeExecResult> listExecResult) throws EpeAppException {
        String postMessage = "db_update, expected the data source and the select.";
        DataSource dataSource = this.getDataSourceAt(listExecResult, 0, postMessage);
        String update = this.getStringAt(listExecResult, 1, postMessage);
        String modifiedRows = executeUpdate(dataSource, update) + "";
        this.log(execParams, modifiedRows);
        return this.createResult(modifiedRows);
    }

    /**
     * @return the number of modified rows
     */
    public static int executeUpdate(DataSource dataSource, String update) throws EpeAppException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            int modifiedRows = preparedStatement.executeUpdate();
            // connection.commit();
            return modifiedRows;
        } catch (Exception e) {
            throw new EpeAppException("Invalid execute update: " + update, e);
        }
    }

}
