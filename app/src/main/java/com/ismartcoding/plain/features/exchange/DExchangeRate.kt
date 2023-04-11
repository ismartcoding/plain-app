package com.ismartcoding.plain.features.exchange

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

data class DExchangeRate(
    val currency: String = "",
    val rate: Double = 0.0
)

class DExchangeRates {
    var date: String = ""
    val rates = mutableListOf<DExchangeRate>()

    fun getBaseRate(base: String): Double {
        return rates.find { it.currency == base }?.rate ?: 1.0
    }

    fun fromXml(xml: String) {
        val doc = Jsoup.parse(xml, "", Parser.xmlParser())
        val elements = doc.select("*|Envelope > Cube > Cube")
        date = elements.attr("time")
        rates.add(DExchangeRate("EUR", 1.0))
        elements.firstOrNull()?.childNodes()?.filterIsInstance<Element>()?.forEach { node ->
            rates.add(DExchangeRate(node.attr("currency"), node.attr("rate").toDoubleOrNull() ?: 0.0))
        }
    }
}