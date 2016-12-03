package com.zagrebelnyy.spray

import org.json4s.ShortTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization._

/**
 * Created by Pavel on 02.12.2016.
 */
trait Drug
  case class DrugFirst (name: String) extends Drug
  case class DrugSecond(name:String, age:Int)extends Drug
  case class DrugThird(name:String, age:Int, married: Boolean)extends Drug



object Drug {
  val drugs = List[Drug](
  DrugFirst(name = "DrugFirst"),
  DrugSecond(name ="DrugSecond",age = 10 ),
  DrugThird(name ="DrugThird",age = 20,married = true)
  )
  private implicit val formats = Serialization.formats(ShortTypeHints(List (classOf[DrugFirst],classOf[DrugSecond],classOf[DrugThird])))
  def toJson(drugs:List[Drug]):String = writePretty(drugs)
  def toJson(drug:Drug):String = writePretty(drug)

}