import com.github.nscala_time.time.Imports._
import java.text.SimpleDateFormat
import java.util.Date
import novoda.gitlog.Day
import org.eclipse.jgit.api._
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.revwalk.RevCommit
import scala.collection.JavaConverters._
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

object ImageLoader extends App {

  val repo = new FileRepositoryBuilder().setGitDir(new File("///home/acsia/dev/android/SQLiteProvider/.git")).readEnvironment().findGitDir.build
  val git = new Git(repo)
  val logs = git.log.call

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
