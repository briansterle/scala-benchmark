name := """scala-benchmarks"""

version := "1.0.0"

scalaVersion := "2.13.7"

scalacOptions := Seq(
  "-opt:l:inline",
  "-opt-inline-from:**",
  "-deprecation",
//  "-Ypartial-unification",
  "-Ywarn-value-discard",
//  "-Ywarn-unused-import",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen"
)

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.27"
)
Def.settings(
  Global / onChangedBuildSource := ReloadOnSourceChanges
)

/* To run benchmarks:
    jmh:run -t 1 -f 1 -wi 5 -i 5 .*Bench.*
 */
enablePlugins(JmhPlugin)
