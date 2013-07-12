package com.visiblemeasures.ma

import akka.actor.ActorSystem
import akka.actor.Props

object MovingAvg extends App {
     
    val maSystem = ActorSystem("maSystem")
    val window = 10
    val stocks = List(StockInit(Symbol("Apple", "AAPL"), 100.0), StockInit(Symbol("Amazon", "AMZN"), 200.0))
    val master = maSystem.actorOf(Props(new Master(window, stocks)), name = "master")

    // start the calculation   
    master ! Start
 

}