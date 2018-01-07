package com.charlie.world.bigtable

import java.io.IOException

import com.charlie.world.bigtable.mutate.Putter
import com.charlie.world.bigtable.read.Scanner
import com.google.cloud.bigtable.grpc.BigtableSession

/**
  * Writer Charlie Lee
  * Created at 2018. 1. 5.
  */
class Bigtable(projectId: String, instanceId: String)(implicit session: BigtableSession) {

  def getSession: BigtableSession = session

  def getProjectId: String = projectId

  def getInstanceId: String = instanceId

  @throws[IOException]
  def close(): Unit = {
    session.close()
  }

  /**
    * Craete Scanner of table
    *
    * @param table table name
    * @return Scanner
    */
  def scanner(table: String) = new Scanner(table)

  /**
    * Create Putter of table and row
    *
    * @param table table name
    * @return Putter
    */
  def bigtableWriter(table: String) = new Putter(table)
}
