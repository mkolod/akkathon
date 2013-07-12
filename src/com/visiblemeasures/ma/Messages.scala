package com.visiblemeasures.ma

sealed trait MAMessage
case class Symbol(name: String, sym: String) extends MAMessage
case class StockInit(sym: Symbol, initPrice: Double) extends MAMessage
case class Tick(symbol: Symbol, price: Double) extends MAMessage
case class Result(sym: String, window: Int, price: Double) extends MAMessage
object Start extends MAMessage