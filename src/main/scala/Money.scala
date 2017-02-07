package money
import cats.implicits._

case class Money private (currMap: Map[String,BigDecimal]) {
  def *(bd: BigDecimal) = Money(currMap.mapValues(_ * bd))
  def /(bd: BigDecimal) = Money(currMap.mapValues(_ / bd))
  def +(m2: Money) = Money(currMap |+| m2.currMap)
  def -(m2: Money) = Money(currMap |+| m2.currMap.mapValues(_ * -1))  
}

object Money {

  val defaultCurrency = "GBP"

  def apply(): Money = new Money(Map.empty)
  def apply(bd: BigDecimal): Money = apply(bd -> defaultCurrency)
  def apply[A](n: (A, String)*)(implicit toBd: BigDecimalLike[A]): Money = Money(n.map(
    x => (x._2, toBd.toBigDecimal(x._1))
  ).toMap)
}
