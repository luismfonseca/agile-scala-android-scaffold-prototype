import android.Keys._

import AgileAndroidKeys._

defaultAgileAndroidSettings

android.Plugin.androidBuild

name := "crud-prototype"

scalaVersion := "2.10.3"

//proguardCache in Android ++= Seq(
//  ProguardCache("org.scaloid") % "org.scaloid"
//)

//proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize")

libraryDependencies += "org.scaloid" %% "scaloid" % "3.2-8"

scalacOptions in Compile += "-feature"

//run <<= run in Android

//install <<= install in Android


platformTarget := "android-17"