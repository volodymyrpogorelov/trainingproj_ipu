package model;

import java.io.File;
import java.lang.reflect.*;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class DAO <T> {

    private final Class<T> cl;

    private final Connection connection;
    private final String tblName;
    private final Field[] fields; //COLUMNS
    private final Field[] fieldsWithoutPk;
    private final int pkInd; //index of PK inside fields array

    public DAO(Connection connection, Class<T> cl) {
        this.cl = cl;
        this.connection = connection;
        TableName tn = cl.getAnnotation(TableName.class);
        tblName = tn.tablename();
        fields = cl.getDeclaredFields();
        pkInd = getPkInd();
        fieldsWithoutPk = getFieldsWithouPk();
    }

    public List<T> getObjects() {
        List<T> res = null;
        try {
            String query = String.format("SELECT * FROM %s", tblName);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            res = new LinkedList<>();
            while(resultSet.next()){
                T instance = cl.newInstance();
                for(int i = 0; i < fields.length; i++) {
                    String fName=fields[i].getName();
                    Object obj = resultSet.getObject(fName);
                    fields[i].setAccessible(true);
                    fields[i].set(instance, obj);
                    fields[i].setAccessible(false);
                }
                res.add(instance);
            }
        } catch (SQLException e) {  // ASK: WHERE SHOULD I HANDLE EXCEPTIONS?
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return res;
    }

    public boolean insertObject(T obj){ //NOTE: ID field should be excluded from fields, because autoincrement on table
        StringBuilder values = new StringBuilder("(");
        StringBuilder names = new StringBuilder("(");

        for(int i = 0; i < fieldsWithoutPk.length; i++) {
            fieldsWithoutPk[i].setAccessible(true);
            names.append(fieldsWithoutPk[i].getName());
            names.append(",");
            try {
                values.append("'");
                values.append(fieldsWithoutPk[i].get(obj).toString());
                values.append("'");
                values.append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }finally {
                fieldsWithoutPk[i].setAccessible(false);
            }
        }
        values.deleteCharAt(values.length() - 1); // Removes last unneeded ','
        names.deleteCharAt(names.length() - 1); // Removes last unneeded ','
        values.append(")");
        names.append(")");
        String query = String.format("INSERT INTO %s %s VALUES %s", tblName, names, values);
        try {
            Statement stmt = connection.createStatement();
            return stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateObject(T obj) { // UPDATES OBJECT BY PrimaryKey
        Field pkField = fields[pkInd];
        Object pk = null;
        try {
            pkField.setAccessible(true);
            pk = pkField.get(obj);
            pkField.setAccessible(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        StringBuilder sb = new StringBuilder("");
        for(Field field : fieldsWithoutPk) {
            sb.append(field.getName());
            sb.append(" = ");
            try {
                sb.append("'");
                field.setAccessible(true);
                sb.append(field.get(obj));
                field.setAccessible(false);
                sb.append("'");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String query = String.format("UPDATE %s SET %s WHERE ID=%s", tblName, sb, pk);
        System.out.println(query);
        try {
            Statement stmt = connection.createStatement();
            return stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;


    }

    public boolean deleteObject(T obj) { // DELETES OBJECT BY PrimaryKey
        Field idField = fields[pkInd];
        try {
            idField.setAccessible(true);
            int id = idField.getInt(obj);
            idField.setAccessible(false);
            String query = String.format("DELETE FROM %s WHERE ID = %s", tblName, id);
            System.out.println(query);
            Statement stmt = connection.createStatement();
            return stmt.execute(query);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getPkInd() {
        for(int i = 0; i<fields.length; i++){
            if(fields[i].isAnnotationPresent(PrimaryKey.class)){
                return i;
            }
        }
        throw new RuntimeException("No @PrimaryKey annotation found for class: " + cl.getName());
    }

    private Field[] getFieldsWithouPk() {
        Field [] res = new Field[fields.length -1];
        int i = 0;
        for(; i < pkInd; i++){
            res[i] = fields[i];
        }
        int j = i+1;
        for(; j<fields.length; j++){
            res[i] = fields[j];
            i++;
        }
        return res;
    }

}
