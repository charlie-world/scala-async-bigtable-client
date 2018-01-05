package com.charlie.world.bigtable

import java.io.IOException

import com.charlie.world.bigtable.mutate.Putter
import com.charlie.world.bigtable.read.Scanner
import com.google.cloud.bigtable.grpc.BigtableSession

/**
  * Writer Charlie Lee
  * Created at 2018. 1. 5.
  */
class Bigtable(projectId: String,
                     instanceId: String)
                    (implicit session: BigtableSession) {

  def getSession: BigtableSession = session

  def getProjectId: String = projectId

  def getInstanceId: String = instanceId

  @throws[IOException]
  def close(): Unit = {
    session.close()
  }

  /**
    * Read some data from a Bigtable table.
    *
    * @param table table name
    * @return TableRead
    */
  def scanner(table: String) = new Scanner(table)

  /**
    * Perform mutations on a row atomically.
    *
    * @param table table name
    * @param row   row key
    * @return BigtableMutation
    */
  def bigtableWriter(table: String, row: String) = new Putter(table)
}
