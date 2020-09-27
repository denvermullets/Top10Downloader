package com.example.top10downloader

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates

class FeedEntry {
    //var name: String = ""
    var podcastTitle: String = ""
    //var episodeNumber: String = ""
    var summary: String = ""
    var podcastLength = ""
    //var imageURL: String = ""
    var pubDate: String = ""


    override fun toString(): String {
        return """
            podcastTitle = $podcastTitle
            podcastLength = $podcastLength
            summary = $summary
            pubDate = $pubDate
        """.trimIndent() // removed:             imageURL = $imageURL episodeNumber = $episodeNumber
    }
}

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val downloadData by lazy { DownloadData(this, demoListView) }  //need to read up on being 'lazy'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate called")
     //   val downloadData = DownloadData(this, demoListView)
        downloadData.execute("https://rss.art19.com/the-bill-simmons-podcast") //url for rss feed


    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData.cancel(true)
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView) : AsyncTask<String, Void, String>() {

            private val TAG = "DownloadData"

            var propContext : Context by Delegates.notNull()
            var propListView : ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }
                // AsyncTask:
                // 1st parameter is the data url as String
                // 2nd parameter would be for progress bar, not used so Void
                // 3rd parameter type of result to get back - xml as String

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val parseApplications = ParseApplication()
                parseApplications.parse(result)

                val feedAdapter = FeedAdapter(propContext, R.layout.list_record, parseApplications.applications)
                propListView.adapter = feedAdapter
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG,"doInBackground: starts with ${url[0]}")
                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                return URL(urlPath).readText()

            }
        }
    }


}
