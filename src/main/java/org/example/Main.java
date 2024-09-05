package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Main {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL data sources example")
                .master("local[*]")
                .getOrCreate();

        runCsvDatasetExample(spark, "/opt/bitnami/spark/persons.csv");
    }

    private static void runCsvDatasetExample(SparkSession spark, String csvPath) {
        // $example on:csv_dataset$
        // A CSV dataset is pointed to by path.
        // The path can be either a single CSV file or a directory of CSV files

        Dataset<Row> df = spark.read().csv(csvPath);
        df.show();
        // +------------------+
        // |               _c0|
        // +------------------+
        // |      name;age;job|
        // |Jorge;30;Developer|
        // |  Bob;32;Developer|
        // +------------------+

        // Read a csv with delimiter, the default delimiter is ","
        Dataset<Row> df2 = spark.read().option("delimiter", ";").csv(csvPath);
        df2.show();
        // +-----+---+---------+
        // |  _c0|_c1|      _c2|
        // +-----+---+---------+
        // | name|age|      job|
        // |Jorge| 30|Developer|
        // |  Bob| 32|Developer|
        // +-----+---+---------+

        // Read a csv with delimiter and a header
        Dataset<Row> df3 = spark.read().option("delimiter", ";").option("header", "true").csv(csvPath);
        df3.show();
        // +-----+---+---------+
        // | name|age|      job|
        // +-----+---+---------+
        // |Jorge| 30|Developer|
        // |  Bob| 32|Developer|
        // +-----+---+---------+

        // You can also use options() to use multiple options
        java.util.Map<String, String> optionsMap = new java.util.HashMap<String, String>();
        optionsMap.put("delimiter",";");
        optionsMap.put("header","true");
        Dataset<Row> df4 = spark.read().options(optionsMap).csv(csvPath);

        // "output" is a folder which contains multiple csv files and a _SUCCESS file.
        df3.write().csv("output");

        // Read all files in a folder, please make sure only CSV files should present in the folder.
        String folderPath = "examples/src/main/resources";
        Dataset<Row> df5 = spark.read().csv(folderPath);
        df5.show();
        // Wrong schema because non-CSV files are read
        // +-----------+
        // |        _c0|
        // +-----------+
        // |238val_238|
        // |  86val_86|
        // |311val_311|
        // |  27val_27|
        // |165val_165|
        // +-----------+

        // $example off:csv_dataset$
    }
}