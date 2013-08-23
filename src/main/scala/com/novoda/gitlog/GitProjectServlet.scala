package com.novoda.gitlog

import java.util.Date
import java.text.SimpleDateFormat
import scala.xml.{Node, NodeSeq}
import novoda.gitlog.Day

import scala.collection.JavaConverters._
import org.eclipse.jgit.revwalk.RevCommit
import com.github.nscala_time.time.Imports._
import novoda.gitlog.Day.Day

// JSON-related libraries

import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra

import org.scalatra.json._

class GitProjectServlet extends GitlogStack with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say
        <a href="hello-scalate">hello to Scalate</a>
        .
      </body>
    </html>
  }

  //get("/hello/:git/")


  implicit class RichCommit(commit: RevCommit) {
    def time: DateTime = new DateTime(commit.getCommitTime * 1000L)

    def day: Day = Day(time)
  }

  case class CommitsPerDay(day: Int, commits: Int)

  get("/datas-years.json") {
    val listOfCommits: List[CommitsPerDay] = T.logs.asScala.toList.foldRight(Map.empty[Day, List[RevCommit]]) {
      (commit, map) => {
        map.+(commit.day -> (map get commit.day).getOrElse(List.empty[RevCommit]).::(commit))
      }
    }.map((a) => CommitsPerDay((a._1.start.millis / 1000L).toInt, a._2.size)).toList

    listOfCommits.foldRight(Map.empty[String, Int]) {
      (commitPd, map) => {
        map.+(commitPd.day.toString -> commitPd.commits)
      }
    }
  }



  get("/git") {
    val format = new SimpleDateFormat("EEE, d MMM yyyy")
    foo(T.logsPerUser)
  }

  def foo(data: Seq[(Day.Day, Map[T.Email, Int])]): NodeSeq = (Seq[Node]() /: data) {
    (seq, node) => seq ++ <div>
      {val format = new SimpleDateFormat("EEE, d MMM yyyy")
      val (time, mapUserCommit) = node
      val s = mapUserCommit + "  " + format.format(new Date(time.start.millis))
      s}
    </div>
  }

  object T {

    import com.github.nscala_time.time.Imports._
    import java.text.SimpleDateFormat
    import java.util.Date
    import novoda.gitlog.Day
    import org.eclipse.jgit.api._
    import org.eclipse.jgit.revwalk.RevCommit
    import scala.collection.JavaConverters._
    import org.eclipse.jgit.storage.file.FileRepositoryBuilder
    import java.io.File

    lazy val repo = new FileRepositoryBuilder().setGitDir(new File("///home/acsia/dev/android/ImageLoader/.git")).readEnvironment().findGitDir.build
    lazy val git = new Git(repo)

    def logs = git.log.call

    def dayAsInterval(when: Long) = {
      val start = new DateTime(when)
      val end = start + (1.day - 1.milli)
      new Interval(start, end)
    }

    val format = new SimpleDateFormat("EEE, d MMM yyyy")

    val logsPerUser = git.log.call.asScala.toList
      .groupBy(a => Day(a.getCommitTime * 1000L))
      .mapValues(commitPerUser).toSeq.sortBy(a => a._1.start.millis)


    logsPerUser.filter(_._1.isBefore(format.parse("Thu, 01 Sep 2011").getTime)).foreach(a => {
      val (time, mapUserCommit) = a
      println(mapUserCommit + "  " + format.format(new Date(time.start.millis)))
    })

    //  logsPerUser.foreach(a => {
    //    val (time, mapUserCommit) = a
    //    println(mapUserCommit + "  " + format.format(new Date(time.start.millis)))
    //  })

    type Email = String

    def commitPerUser: List[RevCommit] => Map[Email, Int] = {
      commits =>
        commits.groupBy(_.getCommitterIdent.getEmailAddress).map(x => (x._1, x._2.size))
    }
  }

}
