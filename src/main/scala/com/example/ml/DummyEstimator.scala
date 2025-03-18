package com.example.ml

import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.{Estimator, Model}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Dataset}

class DummyModel(override val uid: String) extends Model[DummyModel] {

  def this() = this("abcd")

  override def copy(extra: ParamMap): DummyModel = this

  override def transform(dataset: Dataset[_]): DataFrame = {
    println("--------------------------------------------------------- DummyModel ")
    dataset.toDF()
  }

  override def transformSchema(schema: StructType): StructType = schema
}


/**
 * A dummy estimator that repros the issue
 *
 * @param uid unique ID of the estimator
 */
class DummyEstimator(override val uid: String) extends Estimator[DummyModel] {

  def this() = this("abcd")

  override def fit(dataset: Dataset[_]): DummyModel = {
    println("--------------------------------------------------------- DummyEstimator ")

    // This piece of code could repro the issue
    dataset.rdd.mapPartitions { iter =>
      iter
    }.collect()

    new DummyModel(this.uid)
  }

  override def copy(extra: ParamMap): Estimator[DummyModel] = this

  override def transformSchema(schema: StructType): StructType = schema
}


