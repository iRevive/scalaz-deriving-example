package example

import cats.Show
import magnolia.{CaseClass, Magnolia, SealedTrait}

object CustomShowMagnoliaDerivation {

  type Typeclass[T] = Show[T]

  def combine[T](ctx: CaseClass[Typeclass, T]): Typeclass[T] = value => {
    if (ctx.isValueClass) {
      ctx.parameters.headOption.fold("")(param => param.typeclass.show(param.dereference(value)))
    } else {
      val paramStrings = ctx.parameters.flatMap { param =>
        param.dereference(value) match {
          case v: Option[_] if v.isEmpty => Nil
          case v: Seq[_] if v.isEmpty    => Nil
          case other                     => List(s"${param.typeclass.show(other)}")
        }
      }

      s"${ctx.typeName.short}(${paramStrings.mkString(", ")})"
    }
  }

  def dispatch[T](ctx: SealedTrait[Typeclass, T]): Typeclass[T] = value => {
    ctx.dispatch(value) { sub =>
      sub.typeclass.show(sub.cast(value))
    }
  }

  implicit def derive[T]: Typeclass[T] = macro Magnolia.gen[T]

}
