package example

import cats.{Eq, Hash, Show}
import cats.instances.list._
import cats.instances.string._
import io.circe.Codec
import io.circe.syntax._

object Original {

  final case class User(name: String, password: String)

  object User {
    implicit val userCodec: Codec[User] = io.circe.generic.semiauto.deriveCodec
    implicit val userEq: Eq[User]       = Eq.fromUniversalEquals
    implicit val userHash: Hash[User]   = Hash.fromUniversalHashCode
    implicit val userShow: Show[User]   = CustomShowMagnoliaDerivation.derive
  }

  final case class Client(user: User, jobs: List[String])

  object Client {
    implicit val clientCodec: Codec[Client] = io.circe.generic.semiauto.deriveCodec
    implicit val clientEq: Eq[Client]       = Eq.fromUniversalEquals
    implicit val clientHash: Hash[Client]   = Hash.fromUniversalHashCode
    implicit val clientShow: Show[Client]   = CustomShowMagnoliaDerivation.derive
  }


  def main(args: Array[String]): Unit = {
    val user   = User("John", "pwd")
    val client = Client(user, List("Functional expert"))

    println(Show[Client].show(client))
    println(client.asJson.noSpaces)
  }

}
