name := """scala-benchmark"""

version := "1.0.0"

//scalaVersion := "2.13.7"
scalaVersion := "3.1.0"

scalacOptions := Seq(
//  "-opt:l:inline",
//  "-opt-inline-from:**",
  "-deprecation",
//  "-Ypartial-unification",
//  "-Ywarn-value-discard",
//  "-Ywarn-unused-import",
//  "-Ywarn-dead-code",
//  "-Ywarn-numeric-widen"
)

libraryDependencies ++= Seq(
  //  "org.scalaz" %% "scalaz-core" % "7.2.27"
  // https://mvnrepository.com/artifact/org.scalaz/scalaz-core
  "org.scalaz" %% "scalaz-core" % "7.4.0-M9"
)

Global / onChangedBuildSource := ReloadOnSourceChanges

/* To run benchmarks:
    jmh:run -t 1 -f 1 -wi 5 -i 5 .*Bench.*
 */
enablePlugins(JmhPlugin)
