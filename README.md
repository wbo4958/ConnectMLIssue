# Spark Connect ML issue

The third-party Spark ML libraries may potentially not be running
on Spark Connect when playing with ``--jars`` on Spark standalone cluster.

This repo is a minimum project that could reproduce this issue.

## Compile the dummy ml project

```shell
mvn clean package
```

## Start a standalone cluster

```shell
$SPARK_HOME/sbin/start-master.sh -h localhost
$SPARK_HOME/sbin/start-worker.sh spark://localhost:7077
```

## Start a connect server connecting to the spark standalone cluster

```
./standalone.sh
```

## Play around the demo

Running the below code under the pyspark client environment.

```shell
python repro-issue.py
```
Then you're going to see the issue

``` console

Caused by: java.lang.ClassCastException: cannot assign instance of java.lang.invoke.SerializedLambda to field org.apache.spark.rdd.MapPartitionsRDD.f of type scala.Function3 in instance of org.apache.spark.rdd.MapPartitionsRDD
	at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2096)
	at java.io.ObjectStreamClass$FieldReflector.checkObjectFieldValueTypes(ObjectStreamClass.java:2060)
	at java.io.ObjectStreamClass.checkObjFieldValueTypes(ObjectStreamClass.java:1347)
	at java.io.ObjectInputStream$FieldValues.defaultCheckFieldValues(ObjectInputStream.java:2679)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:2486)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2257)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1733)
	at java.io.ObjectInputStream$FieldValues.<init>(ObjectInputStream.java:2606)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:2457)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2257)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1733)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:509)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:467)
	at org.apache.spark.serializer.JavaDeserializationStream.readObject(JavaSerializer.scala:88)
	at org.apache.spark.serializer.JavaSerializerInstance.deserialize(JavaSerializer.scala:136)
	at org.apache.spark.scheduler.ResultTask.runTask(ResultTask.scala:86)
	at org.apache.spark.TaskContext.runTaskWithListeners(TaskContext.scala:171)
	at org.apache.spark.scheduler.Task.run(Task.scala:147)
	at org.apache.spark.executor.Executor$TaskRunner.$anonfun$run$5(Executor.scala:645)
	at org.apache.spark.util.SparkErrorUtils.tryWithSafeFinally(SparkErrorUtils.scala:80)
	at org.apache.spark.util.SparkErrorUtils.tryWithSafeFinally$(SparkErrorUtils.scala:77)
	at org.apache.spark.util.Utils$.tryWithSafeFinally(Utils.scala:100)
	at org.apache.spark.executor.Executor$TaskRunner.run(Executor.scala:648)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	at java.lang.Thread.run(Thread.java:840)
```
