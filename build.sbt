name := "GMM"

version := "1.0"

scalaVersion := "2.11.8"

val sparkVers = "2.0.0"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.5"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.5"

libraryDependencies  ++= Seq(
  // Last stable release
  "org.scalanlp" %% "breeze" % "0.12",

  // Native libraries are not included by default. add this if you want them (as of 0.7)
  // Native libraries greatly improve performance, but increase jar sizes. 
  // It also packages various blas implementations, which have licenses that may or may not
  // be compatible with the Apache License. No GPL code, as best I know.
  "org.scalanlp" %% "breeze-natives" % "0.12",

  // The visualization library is distributed separately as well.
  // It depends on LGPL code
  "org.scalanlp" %% "breeze-viz" % "0.12"
)

// https://mvnrepository.com/artifact/commons-io/commons-io
libraryDependencies += "commons-io" % "commons-io" % "2.5"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test"

libraryDependencies ++= Seq(
  "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0" classifier "models",
  "edu.stanford.nlp" % "stanford-parser" % "3.6.0"
)

// https://mvnrepository.com/artifact/org.apache.opennlp/opennlp-tools
libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "1.5.3"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.0.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "0.4-rc3.9"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.0"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-io-extra" % "2.1.0"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-filters" % "2.1.0"

// https://mvnrepository.com/artifact/com.datumbox/libsvm
libraryDependencies += "com.datumbox" % "libsvm" % "3.21"

// https://mvnrepository.com/artifact/nz.ac.waikato.cms.weka/weka-stable
libraryDependencies += "nz.ac.waikato.cms.weka" % "weka-stable" % "3.8.0"

//http://search.maven.org/#artifactdetails%7Ccom.google.apis%7Cgoogle-api-services-plus%7Cv1-rev451-1.22.0%7Cjar
libraryDependencies += "com.google.apis" % "google-api-services-plus" % "v1-rev451-1.22.0"

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"



