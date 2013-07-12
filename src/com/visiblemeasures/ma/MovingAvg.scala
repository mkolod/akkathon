package com.visiblemeasures.ma

import akka.actor.ActorSystem
import akka.actor.Props

object MovingAvg extends App {
     
    val maSystem = ActorSystem("maSystem")
    val window = 10
    
    // create the master
    val master = maSystem.actorOf(Props(new Master(window)), name = "master")

    // start the calculation
    
    master ! Start
 

}