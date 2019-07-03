package com.example.demo.service.impl;

import com.example.demo.service.IHbaseService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("HbaseService")
public class HbaseService implements IHbaseService {

    private static final Logger logger = LogManager.getLogger("HbaseService");
    public static final String TABLE_NAME = "scores";
    public static final String FAMILY_NAME_1 = "course";
    public static final String FAMILY_NAME_2 = "profile";
    public static final String QUALIFIER_NAME_1_1 = "math";
    public static final String QUALIFIER_NAME_1_2 = "art";
    public static final String QUALIFIER_NAME_2_1 = "gender";
    public static final String QUALIFIER_NAME_2_2 = "name";

    @Value("${hbaseConf.zookeeper}")
    public String zookeeper;
    @Value("${hbaseConf.parent}")
    public String parent;

    @Override
    public void createtable() throws Exception{

        logger.info("创建hbase表");
        createTable(getHBaseConfiguration());

    }

    @Override
    public void putData() throws Exception{

        logger.info("hbase插入数据");
        put(getHBaseConfiguration());

    }

    @Override
    public void getData() throws Exception{

        logger.info("获取habse数据");
        get(getHBaseConfiguration());

    }

    public Configuration getHBaseConfiguration() {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum",zookeeper);
        conf.set("zookeeper.znode.parent", parent);
        conf.set("hbase.client.retries.number", "2");
        return conf;
    }

    public  void createTable(Configuration conf) throws Exception{
        Connection connection = null;
        Admin admin = null;
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
            if (!admin.tableExists(TableName.valueOf(TABLE_NAME))) {
                HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
                HColumnDescriptor columnDescriptor_1 = new HColumnDescriptor(Bytes.toBytes(FAMILY_NAME_1));
                HColumnDescriptor columnDescriptor_2 = new HColumnDescriptor(Bytes.toBytes(FAMILY_NAME_2));
                tableDescriptor.addFamily(columnDescriptor_1);
                tableDescriptor.addFamily(columnDescriptor_2);
                System.out.println("creating table");
                admin.createTable(tableDescriptor);
            }
        }catch (Exception e){
            logger.error("创建HBASE_TABLE失败!");
            e.printStackTrace();
        }finally {
            if (admin != null) admin.close();
            if (connection != null) connection.close();
        }
    }

    public void deleteTable(Configuration conf) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        if (admin.tableExists(TableName.valueOf(TABLE_NAME))) {
            if (!admin.isTableDisabled(TableName.valueOf(TABLE_NAME))) {
                admin.disableTable(TableName.valueOf(TABLE_NAME));
            }
            admin.deleteTable(TableName.valueOf(TABLE_NAME));
        }
    }

    public void put(Configuration conf) throws IOException {
        //Connection to the cluster.
        Connection connection = null;
        //A lightweight handler for a specific table.
        Table table = null;
        try {
            //establish the connection to the cluster.
            connection = ConnectionFactory.createConnection(conf);
            //retrieve a handler to the target table
            table = connection.getTable(TableName.valueOf(TABLE_NAME));
            //describe the data
            Put put = new Put(Bytes.toBytes("row1"));
            put.addColumn(Bytes.toBytes(FAMILY_NAME_1), Bytes.toBytes(QUALIFIER_NAME_1_1), Bytes.toBytes(0));
            put.addColumn(Bytes.toBytes(FAMILY_NAME_1), Bytes.toBytes(QUALIFIER_NAME_1_2), Bytes.toBytes(0));
            put.addColumn(Bytes.toBytes(FAMILY_NAME_2), Bytes.toBytes(QUALIFIER_NAME_2_1), Bytes.toBytes(0));
            put.addColumn(Bytes.toBytes(FAMILY_NAME_2), Bytes.toBytes(QUALIFIER_NAME_2_2), Bytes.toBytes(0));
            //send the data
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close
            if (table != null) table.close();
            if (connection != null) connection.close();
        }
    }

    public static void get(Configuration conf) throws IOException {
        Connection connection = null;

        connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
        Get get = new Get(Bytes.toBytes("row1"));
        get.addColumn(Bytes.toBytes(FAMILY_NAME_1), Bytes.toBytes(QUALIFIER_NAME_1_1));
        get.addColumn(Bytes.toBytes(FAMILY_NAME_2), Bytes.toBytes(QUALIFIER_NAME_2_1));
        Result result = table.get(get);
        while (result.advance()) {
            System.out.println(result.current());
        }
        table.close();
        connection.close();
    }

}
