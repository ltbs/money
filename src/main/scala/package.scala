import cats.implicits._
import cats.{Group, Eq}

package object money {

  implicit val BigLikeBD: BigDecimalLike[BigDecimal] = new BigDecimalLike[BigDecimal] {
    def toBigDecimal(i: BigDecimal) = i
  }

  implicit val StringLikeBD: BigDecimalLike[String] = new BigDecimalLike[String] {
    def toBigDecimal(i: String) = BigDecimal(i)
  }

  implicit val IntLikeBD: BigDecimalLike[Int] = new BigDecimalLike[Int] {
    def toBigDecimal(i: Int) = BigDecimal(i)
  }

  implicit val moneyAbelian: Group[Money] = new Group[Money] {
    def combine(x: Money, y: Money) = Money(
      {x.currMap |+| y.currMap}.filter(_._2 != 0)
    )
    def empty: Money = Money()
    def inverse(x: Money) = Money(x.currMap.mapValues(_.inverse))    
  }

  implicit val moneyEq: Eq[Money] = new Eq[Money] {
    def eqv(x: Money, y: Money): Boolean = x.currMap === y.currMap
  }
  
}
