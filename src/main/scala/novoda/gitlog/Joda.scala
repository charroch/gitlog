package novoda.gitlog

import org.joda.time.{Interval, DateTime}
import com.github.nscala_time.time.Imports._

class Joda {
}

object Day {

  type Day = Interval

  def apply(a: DateTime) = {
    val start = a.withTimeAtStartOfDay()
    val end = start + 1.day - 1.milli
    new Day(start, end)
  }

  def apply(when: Long) = {
    val a = new DateTime(when)
    val start = a.withTimeAtStartOfDay()
    val end = start + 1.day - 1.milli
    new Day(start, end)
  }
}


object Week {

  type Week = Interval

  def apply(a: DateTime) = {
    val start = a.withTimeAtStartOfDay()
    val end = start + 1.week - 1.milli
    new Week(start, end)
  }

  def apply(when: Long) = {
    val a = new DateTime(when)
    val start = a.withTimeAtStartOfDay()
    val end = start + 1.week - 1.milli
    new Week(start, end)
  }
}