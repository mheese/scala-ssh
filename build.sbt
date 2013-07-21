name := "scala-ssh"

version := "0.7.0-SNAPSHOT"

organization := "com.decodified"

organizationHomepage := Some(new URL("http://decodified.com"))

description := "A Scala library providing remote shell access via SSH"

homepage := Some(new URL("https://github.com/sirthias/scala-ssh"))

startYear := Some(2011)

licenses := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scalaVersion := "2.10.2"

scalacOptions <<= scalaVersion map {
  case x if x startsWith "2.9" =>
    Seq("-unchecked", "-deprecation", "-encoding", "utf8")
  case x if x startsWith "2.10" =>
    Seq("-feature", "-language:implicitConversions", "-unchecked", "-deprecation", "-encoding", "utf8")
}

libraryDependencies ++= Seq(
	"net.schmizz" % "sshj" % "0.8.1",
	"org.slf4j" % "slf4j-api" % "1.7.5",
	"org.bouncycastle" % "bcprov-jdk16" % "1.46" % "provided",
	"com.jcraft" % "jzlib" % "1.1.2" % "provided",
  "org.specs2" %% "specs2" % "[1.12,)" % "test",
	"ch.qos.logback" % "logback-classic" % "1.0.13" % "test"
)


///////////////
// publishing
///////////////

crossScalaVersions := Seq("2.9.2", "2.10.2")

scalaBinaryVersion <<= scalaVersion(sV => if (CrossVersion.isStable(sV)) CrossVersion.binaryScalaVersion(sV) else sV)

publishMavenStyle := true

//publishTo <<= version { version =>
//  Some {
//    "spray repo" at {
//      // public uri is repo.spray.io, we use an SSH tunnel to the nexus here
//      "http://localhost:42424/content/repositories/" + {
//        if (version.trim.endsWith("SNAPSHOT")) "snapshots/" else"releases/"
//      }
//    }
//  }
//}

credentials += Credentials(Path.userHome / ".sbt" / "artifactoryCredentials")

publishTo <<= version { version =>
  Some {
    "Artifactory Local" at {
      "http://localhost:8081/artifactory/" + {
        if (version.trim.endsWith("SNAPSHOT"))
          "libs-snapshot-local/"
        else
          "libs-release-local/"
      }
    }
  }
}


///////////////
// ls-sbt
///////////////

seq(lsSettings:_*)

(LsKeys.tags in LsKeys.lsync) := Seq("ssh")

(LsKeys.docsUrl in LsKeys.lsync) := Some(new URL("https://github.com/sirthias/scala-ssh/"))

(externalResolvers in LsKeys.lsync) := Seq("spray repo" at "http://repo.spray.io")
