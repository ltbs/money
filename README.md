# money
Money as a datatype. Born out of the observation that money in different currencies 
and other fungible goods (oil, gold, shares) can be represented as an abelian group
(vector space) and as such it has been coded in cats. 

Also includes protection to ensure that monetary values are only represented using 'safe'
data types (integers, strings, big decimals) and not floating points.

## Update
A week or so on, I'm not actually very happy with this library and don't recommend 
its use. For as start it is too specific in constraining the use of Strings and 
BigDecimal. I generalised to use any key rather than String, and any value that is 
itself a group. With hindsight it is an assumption that the user doesn't know what 
they're doing in forcing the use of BigDecimal, which I wouldn't consider acceptable
were I the user. And of course BigDecimal is arbitrary, not infinite, precision. 

After generalising I decided the enclosing case class itself was rather pointless and 
rewrote the library to be effectively replaced with this - 


```scala
implicit def mapGroup[K,V](implicit g: Group[V]): Group[Map[K,V]] = new MapGroup[K,V]

class MapGroup[K, V](implicit V: Group[V]) extends MapMonoid[K, V] with Group[Map[K,V]] {

  def inverse(x: Map[K,V]) = x.mapValues(_.inverse)

  override def combine(xs: Map[K, V], ys: Map[K, V]): Map[K,V] = {
    super.combine(xs,ys).filter(_._2 != V.empty)
  }

  override def combineAll(xss: TraversableOnce[Map[K, V]]): Map[K, V] = {
    super.combineAll(xss).filter(_._2 != V.empty)
  }
}
```

Note that the filtering out of empty elements is necessary to avoid violating the group
laws (otherwise α |-| α ≠ Map.empty where α ≠ Map.empty).

## Update 2

I discovered that [spire](https://github.com/non/spire) has a typeclass defined for Maps as 
vector-spaces which can be used as below -

```scala 
import spire.algebra._
import spire.math._
import spire.syntax._
import spire.implicits.{MapEq => _, _}

import spire.std.MapVectorEq
import spire.algebra.{AdditiveMonoid, Eq}
implicit def MapVectorEq[K, V](
  implicit V0: Eq[V],
  scalar: AdditiveMonoid[V]
): Eq[Map[K, V]] = new MapVectorEq[K, V]

// ----------------

Map[String,Int]() === Map("GBP" -> 0)    // true
Map("GBP" -> 12) :* 2                    // Map(GBP -> 24)

```
