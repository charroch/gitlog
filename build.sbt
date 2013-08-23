name := "gitlog"
 
version := "1.0"
 
scalaVersion := "2.10.2"

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
    "org.eclipse.jgit" % "org.eclipse.jgit" % "3.0.0.201306101825-r",
    "com.github.nscala-time" %% "nscala-time" % "0.4.2",
    "com.github.mfornos" % "humanize-slim" % "1.1.3",
    "org.scalatra" %% "scalatra-json" % "2.2.1",
    "org.json4s"   %% "json4s-jackson" % "3.2.4",
    "org.scalatest" %% "scalatest" % "1.9.1" % "test"
)