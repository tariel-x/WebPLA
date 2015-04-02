name := """jsconll"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "commons-lang" % "commons-lang" % "2.6",
  "org.apache.thrift" % "libthrift" % "0.9.2"
)

