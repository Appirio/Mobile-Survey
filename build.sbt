name := "diageo-services"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.newrelic.agent.java" % "newrelic-agent" % "3.5.1"
)     

play.Project.playJavaSettings
