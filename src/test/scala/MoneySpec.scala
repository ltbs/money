package money

import org.scalatest._
import scala.language.implicitConversions
import org.scalacheck._
import org.scalacheck.Prop.forAll
import org.scalacheck.support.cats.Instances._
import cats.instances._
import cats.syntax.cartesian._
import cats.kernel.laws.GroupLaws
import cats.Group
import org.typelevel.discipline.scalatest.Discipline
import cats.instances.int._

class MoneySpec extends FunSuite with Matchers with Discipline {

  implicit val arbitraryMoney: Arbitrary[Money] = Arbitrary{
    val genOneCurrency = {
      Gen.choose(-200, 200) |@| Gen.oneOf("EUR", "GBP", "CHF", "USD")
    }.tupled

    {Gen.listOf(genOneCurrency)}.map{Money(_ :_*)}
  }

  checkAll("Money", GroupLaws[Money].group(Group[Money]))

  test("combine currencies") {
    assert(Money(100 -> "EUR") + Money(50 -> "EUR") == Money(150 -> "EUR"))

    assert(Money(100 -> "GBP") + Money(50 -> "EUR") == Money(100 -> "GBP", 50 -> "EUR"))    

  }
}
