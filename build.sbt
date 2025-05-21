// Projects
lazy val sparkProject = (project in file("."))
  .enablePlugins(PackPlugin)
  .settings(
    name := "spark-sbt-project",
    version := "0.0.1",
    Test / parallelExecution := false,
    Test / javaOptions ++= Seq("-Xmx2G", "-XX:+UseG1GC"),
    Test / fork := true,
    assembly / test := {},
    assembly / assemblyOption ~= { _.withIncludeScala(false) },
    publish / skip := true)
  .settings(noWarningInConsole)

packExcludeJars := Seq("scala-.*\\.jar")

// Remove warning when using the sbt console
lazy val noWarningInConsole = Seq(
  Compile / console / scalacOptions ~= {
    _.filterNot(
      Set("-Ywarn-unused-import", "-Ywarn-unused:imports", "-Xlint", "-Xfatal-warnings"))
  },
  Test / console / scalacOptions := (Compile / console / scalacOptions).value)

val sparkVersion = "3.5.2"
val hadoopVersion = "3.3.4"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-connect" % sparkVersion,
  "org.apache.hadoop" % "hadoop-aws" % hadoopVersion % Provided,
  "commons-cli" % "commons-cli" % "1.6.0"
)


// Dependency repositories
ThisBuild / resolvers ++= Seq(Resolver.mavenLocal, Resolver.mavenCentral)

// GitHub package registry resolver
credentials := Seq(Credentials(Path.userHome / ".sbt" / ".credentials"))
ThisBuild / resolvers += "GitHub Package Registry" at "https://maven.pkg.github.com/qbeast-io/qbeast-spark-private"

// Scalafmt settings
Compile / compile := (Compile / compile).dependsOn(Compile / scalafmtCheck).value
Test / compile := (Test / compile).dependsOn(Test / scalafmtCheck).value

// Scala compiler settings
ThisBuild / scalaVersion := "2.12.12"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / scalacOptions += "-Ywarn-unused-import"
ThisBuild / scalafixOnCompile := true
