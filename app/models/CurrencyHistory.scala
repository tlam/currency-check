package models

import java.util.Calendar
import scala.concurrent.Future

import play.api.libs.json.Json
import play.api.libs.ws._
import play.api.Play.current


object CurrencyHistory {

  def currency(date: String): Future[WSResponse] = {
    return WS.url(s"http://api.fixer.io/$date?base=CAD").get()
  }

  def calculate(eur: Double, usd: Double): String = {
    // TODO: Use SimpleDateFormat to format the dates
    val today = Calendar.getInstance().getTime()
    val lastYear = ""
    val twoYearsAgo = ""
    return s"Calculated EUR $eur, USD $usd"
  }
}
