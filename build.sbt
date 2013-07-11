/////////////////////////////////// BEGIN GENERAL SBT CONFIG ///////////////////////////////////

name := "akka_hack"

version := "1.0"

organization := "com.visiblemeasures"

// this will pull in any version of scala-library.jar from the repo for this build, regardless of OS-level Scala installation
scalaVersion := "2.10.2"

// set the Scala compile source directory to be <base>/src
scalaSource in Compile <<= baseDirectory(_ / "src")

// set the Scala test source directory to be <base>/test
scalaSource in Test <<= baseDirectory(_ / "test")

// Add any Maven-compatible repos - need the Typesafe repo to get Akka
// For multiple repositories, it could be a sequence: resolvers ++= Seq("name" at "url")
resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  "Maven Central" at "http://repo1.maven.org")

// Add Akka dependencies 
// Could also include:
// "com.typesafe.akka"    %    "akka-durable-mailboxes"    %    akkaVersion
// "com.typesafe.akka"    %    "akka-amqp"                 %    akkaVersion

{
  val akkaVersion = "2.2.0"
  libraryDependencies ++= Seq(
    "com.typesafe.akka"    %    "akka-actor_2.10"                %    akkaVersion,
    "com.typesafe.akka"    %    "akka-remote_2.10"               %    akkaVersion,
    "com.typesafe.akka"    %    "akka-slf4j_2.10"                %    akkaVersion,
    "com.typesafe.akka"    %    "akka-testkit_2.10"              %    akkaVersion,
    "com.typesafe.akka"    %    "akka-kernel_2.10"               %    akkaVersion,
    "org.scalatest"        %    "scalatest_2.10"            %    "1.9.1"         %    "test"
  )
}

/////////////////////////////////// END GENERAL SBT CONFIG ///////////////////////////////////



/////////////////////////////////// BEGIN SBT-ECLIPSE CONFIG ///////////////////////////////////

// For more information, see https://github.com/typesafehub/sbteclipse/wiki/Using-sbteclipse

// default = Default, i.e. Unmanaged and Resource; options = {Unmanaged, Managed, Source,  Resource}
// You can add other options like this: EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
EclipseKeys.createSrc := EclipseCreateSrc.Default

// If set to Some("path"), it will put Eclipse output targets in the specified directory which can be different
// from the SBT target. If None, SBT and Eclipse targets are the same. Default = None
EclipseKeys.eclipseOutput := None

// Self-explanatory, it allows you to choose a different environment than the default Eclipse one
EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE17)

// Eclipse project natures, etc. Default = Scala, but could be Java as well
EclipseKeys.projectFlavor := EclipseProjectFlavor.Scala

// Skip Eclipse project definitions for parent projects, this should only be false if parent projects are just directories
// rather than parent modules - otherwise things can get complicated. When set to false, the Eclipse import wizard will
// import the parent projects along with the current project.
// Note: this is a build level setting, not a project level one!
// Default = true
EclipseKeys.skipParents in ThisBuild := true

// Skip creating Eclipse metadata for this project. Useful in some modular projects. Default = false.
EclipseKeys.skipProject := false

// Use relative paths for dependencies is absolute ones aren't given. Default = true. 
EclipseKeys.relativizeLibs := true

// Use the project ID instead of the project name for the name of the Eclipse project.
EclipseKeys.useProjectId := false

// Should sbteclipse should try to download source artifacts and create Eclipse source attachments 
// for library dependencies. Default = false
EclipseKeys.withSource := true

// The maximum number of errors shown by the Scala compiler
maxErrors := 20

// Time in ms between polling for file changes when using continuous execution
pollInterval := 1000

// Append options to the list of options passed to the Java compiler
javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

// Append options passed to the Scala compiler
scalacOptions += "-deprecation"

// set the main class for packaging the main jar
mainClass in (Compile, packageBin) := Some("akka_hack.com.visiblemeasures.akkahack.Main")

// set the prompt (for this build) to include the project name and git branch
shellPrompt <<= name(name => { state: State =>
	object devnull extends ProcessLogger {
		def info(s: => String) {}
		def error(s: => String) { }
		def buffer[T](f: => T): T = f
	}
	val current = """\*\s+(\w+)""".r
	def gitBranches = ("git branch --no-color" lines_! devnull mkString)
	"%s:%s>" format (
		name,
		current findFirstMatchIn gitBranches map (_.group(1)) getOrElse "-"
	)
})

// set the main class for packaging the main jar
// 'run' will still auto-detect and prompt
// change Compile to Test to set it for the test jar
//mainClass in (Compile, packageBin) := Some("myproject.MyMain")

// define the repository to publish to
//publishTo := Some("name" at "url")

// disable printing timing information, but still print [success]
//showTiming := false

// disable printing a message indicating the success or failure of running a task
//showSuccess := false

// change the format used for printing task completion time
//timingFormat := {
//	import java.text.DateFormat
//	DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
	
// disable using the Scala version in output paths and artifacts
//crossPaths := false

// fork a new JVM for 'run' and 'test:run'
//fork := true

// fork a new JVM for 'test:run', but not 'run'
//fork in Test := true

// add a JVM option to use when forking a JVM for 'run'
//javaOptions += "-Xmx2G"

// only use a single thread for building
//parallelExecution := false

// Execute tests in the current project serially
//   Tests from other projects may still run concurrently.
//parallelExecution in Test := false

// set the location of the JDK to use for compiling Java code.
// if 'fork' is true, this is used for 'run' as well
//javaHome := Some(file("/usr/lib/jvm/sun-jdk-1.6"))

// Use Scala from a directory on the filesystem instead of retrieving from a repository
//scalaHome := Some(file("/home/user/scala/trunk/"))

// don't aggregate clean (See FullConfiguration for aggregation details)
//aggregate in clean := false

// only show warnings and errors on the screen for compilations.
//  this applies to both test:compile and compile and is Info by default
//logLevel in compile := Level.Warn

// only show warnings and errors on the screen for all tasks (the default is Info)
//  individual tasks can then be more verbose using the previous setting
//logLevel := Level.Warn

// only store messages at info and above (the default is Debug)
//   this is the logging level for replaying logging with 'last'
//persistLogLevel := Level.Debug

// only show 10 lines of stack traces
//traceLevel := 10

// only show stack traces up to the first sbt stack frame
//traceLevel := 0

// add SWT to the unmanaged classpath
//unmanagedJars in Compile += Attributed.blank(file("/usr/share/java/swt.jar"))

// publish test jar, sources, and docs
//publishArtifact in Test := true

// disable publishing of main docs
//publishArtifact in (Compile, packageDoc) := false

// change the classifier for the docs artifact
artifactClassifier in packageDoc := Some("doc")

// Copy all managed dependencies to <build-root>/lib_managed/
//   This is essentially a project-local cache and is different
//   from the lib_managed/ in sbt 0.7.x.  There is only one
//   lib_managed/ in the build root (not per-project).
//retrieveManaged := true



// Directly specify credentials for publishing.
//credentials += Credentials("Sonatype Nexus Repository Manager", "nexus.scala-tools.org", "admin", "admin123")


/////////////////////////////////// END SBT-ECLIPSE CONFIG ///////////////////////////////////
