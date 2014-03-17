import android.Keys._
 
android.Plugin.androidBuild

//import AgileAndroidKeys._

//defaultAgileAndroidSettings

name := "crud-prototype"

scalaVersion := "2.10.3"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

//libraryDependencies += "com.typesafe.play" %% "play-json" % "2.2.1"

proguardCache := Seq(
  ProguardCache("org.scaloid") % "org.scaloid"
)

scalacOptions in Compile ++= Seq("-feature") // "-deprecation"

proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize")

//proguardOptions in Android ++= Seq("-keep public class play.api.libs.json.** {*;}")

proguardOptions in Android ++= Seq("-dontwarn **")

resolvers += "Mandubian repository snapshots" at "https://github.com/mandubian/mandubian-mvn/raw/master/snapshots/"

libraryDependencies += "org.scaloid" %% "scaloid" % "3.2-8"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.4"

//libraryDependencies += "play" % "play-json_2.10" % "2.2-SNAPSHOT"

run <<= run in Android

install <<= install in Android

platformTarget := "android-17"