package com.cahyono.tokoonline.activity

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import com.cahyono.tokoonline.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.Executors


class Throughput : AppCompatActivity() {
    private lateinit var startTestButton: Button
    private lateinit var packetLossBtn: Button
    private lateinit var resultTextView: TextView
    private lateinit var resultPacketLoss: TextView

    private lateinit var downloadManager: DownloadManager
    private var downloadId: Long = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_throughput)

        startTestButton = findViewById(R.id.startTestButton)
        packetLossBtn = findViewById(R.id.packetLossBtn)
        resultTextView = findViewById(R.id.resultTextView)
        resultPacketLoss = findViewById(R.id.resultPacketLoss)

        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        //Packet Loss
        val packetLossTest = Throughput()

        val destinationIpAddress = "https://api.readytowork.site/" // Replace with the IP address or hostname you want to test
        val pingCount = 10 // Number of ping requests to send

        packetLossBtn.setOnClickListener {
            testPacketLoss(destinationIpAddress, pingCount)
        }

        startTestButton.setOnClickListener {
            // Start the throughput test when the button is clicked
            ThroughputTestTask().execute()
        }
    }

    fun testPacketLoss(destinationIpAddress: String, pingCount: Int) {
        // Execute the ping command
        val process = Runtime.getRuntime().exec("ping -c $pingCount $destinationIpAddress")

        // Read the output
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?

        // Variables to keep track of statistics
        var receivedPackets = 0
        var lostPackets = 0

        // Read each line of the command output
        while (reader.readLine().also { line = it } != null) {
            // Check if the line contains statistics
            if (line!!.contains("packets transmitted,")) {
                val parts = line!!.split(",".toRegex()).toTypedArray()
                val transmitted = parts[0].trim().split(" ")[0].toInt()
                val received = parts[1].trim().split(" ")[0].toInt()
                receivedPackets = received
                lostPackets = transmitted - received
            }
        }

        // Calculate packet loss percentage
        val packetLossPercentage = (lostPackets.toDouble() / pingCount) * 100

        // Print results
        println("Packet Loss Test Results:")
        println("Destination IP Address: $destinationIpAddress")
        println("Ping Count: $pingCount")
        println("Packets Transmitted: ${pingCount + lostPackets}")
        println("Packets Received: $receivedPackets")
        println("Packets Lost: $lostPackets")
        println("Packet Loss Percentage: $packetLossPercentage%")

        resultPacketLoss.text = "Result: ${packetLossPercentage} %"
    }

    private inner class ThroughputTestTask : AsyncTask<Void, Void, Double>() {

        override fun doInBackground(vararg params: Void?): Double {
            val startTime = System.currentTimeMillis()

            val fileUrl = "https://api.readytowork.site/testing_lossPacket.zip"

            val request = DownloadManager.Request(Uri.parse(fileUrl))
                .setTitle("My File")
                .setDescription("Downloading...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "testing_lossPacket.zip")

            downloadId = downloadManager.enqueue(request)

            val endTime = System.currentTimeMillis()
            val elapsedTime = endTime - startTime

            val fileSizeInBytes = 1024 * 1024 // 1 MB
            val throughputMbps = (fileSizeInBytes.toDouble() / elapsedTime.toDouble()) * 1000.0 / 1e6

            return throughputMbps
        }

        override fun onPostExecute(result: Double?) {
            super.onPostExecute(result)
            // Display the throughput result in the TextView


            println("Throughput Result: ${result?.toString()}")
            resultTextView.text = "Throughput Result: ${result?.toString()}"
        }
    }
}