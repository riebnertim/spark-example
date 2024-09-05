``` bash
docker network create spark-network
```

``` bash
docker run -d --name spark-master --network spark-network -h spark-master -p 8080:8080 -p 7077:7077 bitnami/spark:latest /opt/bitnami/spark/bin/spark-class org.apache.spark.deploy.master.Master
```

``` bash
docker run -d --name spark-worker --network spark-network -h spark-worker -p 8081:8081 bitnami/spark:latest /opt/bitnami/spark/bin/spark-class org.apache.spark.deploy.worker.Worker spark://spark-master:7077
```

Spark Master UI: http://localhost:8080
Spark Worker UI: http://localhost:8081

```bash
docker cp persons.csv spark-master:/opt/bitnami/spark/
```

```bash
docker cp spark-test-1.0-SNAPSHOT.jar spark-master:/opt/bitnami/spark/
```

```bash
docker exec -it spark-master bash
```

```bash
docker exec -it spark-master /opt/bitnami/spark/bin/spark-submit --master spark://spark-master:7077 --class org.example.Main /opt/bitnami/spark/spark-test-1.0-SNAPSHOT.jar
```


