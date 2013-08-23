import novoda.gitlog.Day
import org.joda.time.DateTime
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.github.nscala_time.time.Imports._

class ADayShould extends FlatSpec with ShouldMatchers {

  "A Day" should "remain the same day" in {
    val today = DateTime.now
    val aBitLater = today + 2.seconds
    Day(today) should equal(Day(aBitLater))
  }

  "Today" should "not be as yesterday" in {
    val today = DateTime.now
    val aBitLater = today - 2.days
    Day(today) should not equal(Day(aBitLater))
  }
}
