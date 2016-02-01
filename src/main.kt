import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import scala.Serializable


/**
 * Created by bart on 30/01/16.
 */
fun main(args: Array<String>) {
    println("Hello Kotlin!")
    val actorSystem = ActorSystem.create("mine")

    val ref = actorSystem.actorOf(Props.create(MyActor::class.java,4), "hi")
    //val ref = actorSystem.actorOf(Props.create({ MyActor(4)}), "hi")
    ref.tell("1", null)
    Thread.sleep(100)
    ref.tell(1, null)
    Thread.sleep(100)
    ref.tell(MyMessage(1), null)
    Thread.sleep(100)
    val future = actorSystem.terminate()
    while(!future.isCompleted)
        Thread.sleep(1)
}

class MyMessage(val number: Int) : Serializable {
    override fun toString(): String {
        return "number: ${number.toString()}"
    }
}

class MyActor(val factor: Int) : UntypedActor() {
    override fun onReceive(message: Any?): Unit {
        println("Received ${message?.javaClass?.toGenericString()} ${message?.toString()}")
        if (message is String) {
            println(message)
        } else if (message is Int) {
            self.tell((message * factor).toString(), sender)
        } else if (message is MyMessage) {
            self.tell(message.number * factor, sender)
        }
    }
}
