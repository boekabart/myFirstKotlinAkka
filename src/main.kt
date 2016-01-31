package myFirstAkkaKotlin
import akka.actor.ActorSystem
import akka.actor.Props


/**
 * Created by bart on 30/01/16.
 */
fun main(args: Array<String>) {
    println("Hello Kotlin!")
    val actorSystem = ActorSystem.create("mine")

    val ref = actorSystem.actorOf(Props.create(MyActor::class.java),"hi")
    ref.tell(1,null)
    ref.tell(5,null)
    Thread.sleep(5000)
    actorSystem.awaitTermination()
}

fun MakeActor( multi: Int ) : () -> MyActor {
    val clo = multi
   return { MyActor() }
}

class MyActor : akka.actor.UntypedActor() {
    override fun onReceive(message: Any?): Unit {
        if (message is String) {
            println(message)
        } else if (message is Int) {
            self.tell((message * 5).toString(), sender)
        }
    }
}
