CONNECT_JAR=`readlink -f $SPARK_HOME/jars/spark-connect_2.13*`
DEMO_JAR=target/com.example.ml-1.0-SNAPSHOT.jar

$SPARK_HOME/sbin/start-connect-server.sh\
  --master spark://localhost:7077 \
  --conf spark.task.maxFailures=1 \
  --conf spark.stage.maxAttempts=1 \
  --conf spark.stage.maxConsecutiveAttempts=1\
  --conf spark.task.cpus=1 \
  --jars $CONNECT_JAR,$DEMO_JAR
