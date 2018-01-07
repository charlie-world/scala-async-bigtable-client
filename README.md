# scala-async-bigtable-client

## Overview

[Google Cloud Platform Bigtable official library](https://github.com/GoogleCloudPlatform/cloud-bigtable-client) does not have an
official asynchronous client. also it is a java library, not a scala library so that clients have to modify or add some codes while
using this official library on scala projects. so that these needs, scala-async-bigtable-client library for user level project is 
started. 

### To use this library, add this on your build.sbt.
```
libraryDependencies += "com.charlie.world" %% "scala-async-bigtable-client" % "0.1.1"
```

## Quick Start

### Create a Bigtable
```
val projectId = "<SOME_PROJECT_ID>"
val instanceId = "<SOME_BIGTABLE_INSTANCE_ID>"

implicit val session: BigtableSession = <BIGTABLE_SESSION>

val bigtable = new Bigtable(projectId, instanceId)
```

### Create a Scanner with Query
```
implicit val executeContext = scala.concurrent.ExecutionContext.Implicits.global

val tableName = "<SOME_TABLE_NAME>"
val scanner = bigtable.scanner(tableName)  // follow this steps from `create a bigtable` part

val startRowKey = "<SOME_START_ROW_KEY>"
val endRowKey = "<SOME_END_ROW_KEY>"
val rowSet = scanner.createRowSet(startRowKey, endRowKey)

val results: Seq[Row] = scanner.executeAsync(rowSet) onComplete {
  case Success(seqOfResults) => seqOfResults
}
```

### Create a Putter with Entries
```
implicit val executeContext = scala.concurrent.ExecutionContext.Implicits.global

val tableName = "<SOME_TABLE_NAME>"
val columnFamily = "<SOME_COLUMN_FAMILY>"
val columnQualifier = "<SOME_COLUMN_QUALIFIER>"
val value = <SOME_VALUE_OF_BYTESTRING>
val rowKey = "<SOME_ROW_KEY>"

val putter = bigtable.putter(tableName)
val mutation = putter.createMutation(columnFamily, columnQualifier, value)
val entry = putter.createEntry(rowKey, Seq(mutation))
val mutateRowsRequest = putter.createMutateRowsRequest(Seq(entry))

val results: Seq[MutateRowsResponse] = putter.executeAsync(mutateRowsRequest) onComplete {
  caes Success(seqOfResults) => seqOfResults
}
```


