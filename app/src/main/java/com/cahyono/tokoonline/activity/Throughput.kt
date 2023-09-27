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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

import java.util.concurrent.Executors


class Throughput : AppCompatActivity() {
    private lateinit var startTestButton: Button
    private lateinit var packetLossBtn: Button
    private lateinit var delayBtn: Button
    private lateinit var resultTextView: TextView
    private lateinit var resultPacketLoss: TextView
    private lateinit var resultDelay: TextView


    private lateinit var downloadManager: DownloadManager
    private var downloadId: Long = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_throughput)

        startTestButton = findViewById(R.id.startTestButton)
        packetLossBtn = findViewById(R.id.packetLossBtn)
        delayBtn = findViewById(R.id.delayBtn)
        resultTextView = findViewById(R.id.resultTextView)
        resultDelay = findViewById(R.id.resultDelay)
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

        delayBtn.setOnClickListener {
            mainDelay()
        }
    }

    suspend fun simulateNetworkRequest(): String {
        delay(3000) // Simulate a 3-second delay (adjust as needed)
        return "Network response data"
    }

    fun delayTest(host: String) : Long? {
        try {
            val process = ProcessBuilder("ping", "-c", "4", host).start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }

            process.waitFor()

            // Extract the latency from the ping output
            val latencyMatch = Regex("time=([0-9.]+)").find(output.toString())
            val latency = latencyMatch?.groupValues?.get(1)?.toDoubleOrNull()

            return (latency?.times(1000))?.toLong()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun mainDelay () {
        val host = "153.92.13.60" // Replace with the host you want to ping
        val latency = delayTest(host)

        if (latency != null) {
            println("Network latency to $host is ${latency}ms")
            resultDelay.text = "Result: ${latency}"
        } else {
            println("Failed to measure network latency.")
            resultDelay.text = "Failed to measure network latency."
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