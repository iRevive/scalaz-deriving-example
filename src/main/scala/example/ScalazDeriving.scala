package example

import cats.{Eq, Hash, Show}
import cats.instances.list._
import cats.instances.string._
import io.circe.Codec
import io.circe.syntax._

object ScalazDeriving {

  @scalaz.annotation.deriving(Eq, Hash, Show, Codec)
  final case class User(name: String, password: String)

  @scalaz.annotation.deriving(Eq, Hash, Show, Codec)
  final case class Client(user: User, jobs: List[String])


  def main(args: Array[String]): Unit = {
    val user   = User("John", "pwd")
    val client = Client(user, List("Functional expert"))

    println(Show[Client].show(client))
    println(client.asJson.noSpaces)
  }

}
