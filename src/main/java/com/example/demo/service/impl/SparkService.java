package com.example.demo.service.impl;

import com.example.demo.entity.KafkaSender;
import com.example.demo.service.ISparkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("SparkService")
public class SparkService implements ISparkService {

    private static final Logger logger = LogManager.getLogger("SparkService");

    @Autowired
    public KafkaSender kafkaSender;
    @Value("${kafkaConf.queueName}")
    public String queue;
    @Value("${spring.datasource.url}")
    public String mysqlurl;
    @Value("${spring.datasource.username}")
    public String username;
    @Value("${spring.datasource.password}")
    public String password;

    @Override
    public String wordCount(Map<Object,Object> paramMap) throws Exception{

        logger.info("Spark计数!");
        SparkConf conf = new SparkConf();
        conf.setAppName("JAVA SPARK PI...");
        conf.setMaster("local[1]");
        conf.set("spark.ui.port","4046");

        JavaSparkContext jsc = new JavaSparkContext(conf);

        int slices = 2;

        int n = (int) Math.min(10000000L * slices,Integer.MAX_VALUE);
        List<Integer> list = new ArrayList<>(n);
        for (int i = 1; i < n; i++) {
            list.add(i);
        }

        JavaRDD<Integer> rdd = jsc.parallelize(list,slices);

        JavaRDD<Integer> maprdd = rdd.map(v1 ->{
            double x = Math.random() * 2 -1;
            double y = Math.random() * 2 -1;
            return (x*x+y*y<=1) ? 1 : 0;
        });

        int count = maprdd.reduce((v1,v2) -> v1+v2);

        logger.info("pi is roughly " +4.0*count/(n-1));
        jsc.stop();

        return Double.toString(4.0*count/(n-1));
    }

    @Override
    public String kafkaSend(Map<Object, Object> paramMap) throws Exception {

        kafkaSender.send(queue,"123");

        return null;
    }

    /**
     * 借款金额超过 1000 且购买商品总价值超过借款总金额的用户 ID，输出这些用户
     * 的性别和年龄分布，并选择合适的图标对结果可视化
     * @throws Exception
     */
    @Override
    public void analyse4_1() {
        SparkSession spark = createSparkSession();

        Dataset<Row> userDF = spark.read().option("header","true").csv("DATA/t_user.csv");
        Dataset<Row> orderDF = spark.read().option("header","true").csv("DATA/t_order.csv");
        Dataset<Row> loanDF = spark.read().option("header","true").csv("DATA/t_loan.csv");

        userDF.createOrReplaceTempView("user");
        loanDF.createOrReplaceTempView("loan");
        orderDF.createOrReplaceTempView("orders");

        Dataset<Row> specfiyUser =
                spark.sql("select u.age,u.sex,count(1) count from " +
                        "(select uid , sum(loan_amount) amount from loan group by uid) a " +
                        "left join " +
                        "(select uid , sum(price-discount) amount from orders group by uid) b " +
                        "on a.uid  = b.uid " +
                        "left join user u on u.uid = a.uid " +
                        "where b.amount>a.amount and a.amount>1 " +
                        "group by u.age,u.sex");

        write2Mysql(specfiyUser,"anaylze");

        spark.stop();
    }

    /**
     * 分析借款金额、年龄、性别的数据分布
     * @throws Exception
     */
    @Override
    public void analyse4_2(){

        SparkSession spark = createSparkSession();

        Dataset<Row> userDF = spark.read().option("header","true").csv("DATA/t_user.csv");
        Dataset<Row> loanDF = spark.read().option("header","true").csv("DATA/t_loan.csv");

        userDF.createOrReplaceTempView("user");
        loanDF.createOrReplaceTempView("loan");

        Dataset<Row> specfiyUser =
                spark.sql(
                        "select u.age,u.sex,min(l.loan_amount) mins,max(l.loan_amount) maxs,avg(l.loan_amount) avgs From user u " +
                        "left join loan l " +
                        "on l.uid = u.uid " +
                        "group by u.age,u.sex ");
        
        write2Mysql(specfiyUser,"anayzle2");

        spark.stop();
    }

    @Override
    public void analyse4_3() {

    }

    @Override
    public void analyse4_4() {
        SparkSession spark = createSparkSession();

        Dataset<Row> userDF = spark.read().option("header","true").csv("DATA/t_user.csv");
        Dataset<Row> loanDF = spark.read().option("header","true").csv("DATA/t_loan.csv");

        userDF.createOrReplaceTempView("user");
        loanDF.createOrReplaceTempView("loan");

        Dataset<Row> specfiyUser =
                spark.sql(
                        "select l.uid,datediff(min(to_date(l.loan_time)),to_date(u.active_date)) days " +
                                "from loan l " +
                                "left join user u " +
                                "on u.uid = l.uid " +
                                "group by l.uid,u.active_date");

        write2Mysql(specfiyUser,"anayzle4");

        spark.stop();
    }

    @Override
    public void analyse4_5() {
        SparkSession spark = createSparkSession();

        Dataset<Row> userDF = spark.read().option("header","true").csv("DATA/t_user.csv");
        Dataset<Row> loanDF = spark.read().option("header","true").csv("DATA/t_loan.csv");
        Dataset<Row> orderDF = spark.read().option("header","true").csv("DATA/t_order.csv");

        userDF.createOrReplaceTempView("user");
        loanDF.createOrReplaceTempView("loan");
        orderDF.createOrReplaceTempView("order");

        Dataset<Row> specfiyUser =
                spark.sql(
                        "select age,sex,count(1) count from user where uid not in ( " +
                                "select uid From order where discount!=0 " +
                                "union " +
                                "select uid from loan) " +
                                "group by age,sex");

        write2Mysql(specfiyUser,"anayzle5");

        spark.stop();
    }

    public SparkSession createSparkSession(){

        SparkSession spark = SparkSession.builder().master("local").getOrCreate();

        spark.sparkContext().setLogLevel("error");
        spark.sqlContext().initializeLogIfNecessary(false);
        spark.conf().set("spark.ui.port","4046");

        return spark;
    }

    public void write2Mysql(Dataset<Row> dataframe,String dbtable){
        dataframe
                .write()
                .mode(SaveMode.Overwrite)
                .format("jdbc")
                .option("url", mysqlurl)
                .option("dbtable", dbtable)
                .option("user", username)
                .option("password", password)
                .save();
    }

}
