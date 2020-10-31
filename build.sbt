
// vim: shiftwidth=2

scalaVersion := "2.13.3"

scalaSource in Test := baseDirectory.value / "test"

scalaSource in Compile := baseDirectory.value / "src"

libraryDependencies ++= Seq(
  "com.google.apis" % "google-api-services-books" % "v1-rev20200925-1.30.10",
  "com.google.auth" % "google-auth-library-oauth2-http" % "0.21.1",
  "com.google.api-client" % "google-api-client" % "1.30.10",
  "com.google.cloud" % "libraries-bom" % "12.0.0",
  "com.google.oauth-client" % "google-oauth-client" % "1.31.1",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.31.1",
  "com.google.oauth-client" % "google-oauth-client-java6" % "1.31.1",
)

fork := true

