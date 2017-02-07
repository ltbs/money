package money

import simulacrum._
import scala.language.implicitConversions

@typeclass trait BigDecimalLike[A] {
  def toBigDecimal(i: A): BigDecimal
}
