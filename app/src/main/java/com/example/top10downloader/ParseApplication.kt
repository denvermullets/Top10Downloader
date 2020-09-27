package com.example.top10downloader

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception

class ParseApplication {
    private val TAG = "ParseApplications"
    val applications = ArrayList<FeedEntry>()

    fun parse(xmlData: String): Boolean {
//        Log.d(TAG, "parse called with $xmlData")
        var status = true
        var inEntry = false
        var textValue = ""
        //var imgURL = ""

        try {

            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntry()
            while (eventType != XmlPullParser.END_DOCUMENT) {

                val tagName = xpp.name?.toLowerCase()
                when (eventType) {

                    XmlPullParser.START_TAG -> {
//                        Log.d(TAG, "parse: Starting tag for $tagName")
                        if (tagName == "item") {
                            inEntry = true
                        }

                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    //imgURL = xpp.getAttributeValue(null, "href") { return true }

                    XmlPullParser.END_TAG -> {
//                        Log.d(TAG,"parse: Ending tag for $tagName")
                        if (inEntry) {
                            when (tagName) {
                                "item" -> {
                                    applications.add(currentRecord)
                                    inEntry = false
                                    currentRecord = FeedEntry()   /// create a new object

                                }
                                // finds the end tag in XML and grabs the text  **** must be case sensitive as it's matching tag in XML.
                                //"name" -> currentRecord.name = textValue
                                "title" -> currentRecord.podcastTitle = textValue
                                //"episode" -> currentRecord.episodeNumber = textValue
                                "summary" -> currentRecord.summary = textValue
                                "duration" -> currentRecord.podcastLength = textValue
                                //"image" -> currentRecord.imageURL = textValue
                                "pubdate" -> currentRecord.pubDate = textValue


                            }
                        }
                    }
                }

                // nothing else to do
                eventType = xpp.next()

            }

//            for (app in applications) {
//                Log.d(TAG, "****************")
//                Log.d(TAG, app.toString())
//            }

        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return status
    }
}