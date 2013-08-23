package novoda.gitlog.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.revwalk.RevCommit
import scala.collection.JavaConverters._
import org.joda.time.Interval

class Log(git: Git) {
  def commits: Commits = Commits(git)
}

class Commits(commits: Iterable[RevCommit]) {

  def per(interval: Interval): Map[Interval, List[RevCommit]] = {
    commits.foldLeft(Map.empty[Interval, List[RevCommit]]) {
      (map, commit) => {
        ???
      }
    }
  }
}

object Commits {

  def apply(what: Git) = new Commits(what.log.call().asScala)

  //def per[RevCommit <% Interval](a: RevCommit) = a.ov

  // val git = new Git(...)

  // git commits per day --- Day(12/-3/2013) => 34 commits, Day(13/-3/2013) => 24 commits
  // RevCommit => Map [ Day, Int]

  // git commits per day per user ---    Day(12/-3/2013) => (carl -> 20 commits; jon -> 14 commits)
  // RevCommit => Map[Day, List[User => Int]] =>

  // git commits per day per user per function(....) ---

  // fn RevCommit => A

//  sealed trait GroupBy[+A, B] {
//    def group(f: A => GroupBy[A, B]): GroupBy[A, B]
//  }
//
//  case class IntervalGrouping(commit: Interval) extends GroupBy[RevCommit, Interval] {
//    def group(f: (RevCommit) => GroupBy[RevCommit, Interval]): GroupBy[RevCommit, Interval] = ???
//  }
//
//  case class UserGrouping(commit: RevCommit) extends GroupBy[RevCommit, String] {
//    def group(f: (RevCommit) => GroupBy[RevCommit, String]): GroupBy[RevCommit, String] = f(commit)
//  }
//
//  sealed trait Per[+A] {
//    def flatMap[B](f: A => Per[B]): Per[B]
//  }
//
//  case class Day(a: Interval) extends Per[Interval] {
//    def flatMap[B](f: (Interval) => Per[B]): Per[B] = ???
//  }
//
//  case class User(user: User) extends Per[User] {
//    def flatMap[B](f: (User) => Per[B]): Per[B] = ???
//  }
//

}

