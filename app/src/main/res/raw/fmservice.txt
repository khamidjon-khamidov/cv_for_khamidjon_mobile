package com.hamidjonhamidov.cvforkhamidjon.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.room.Room
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.ContactActivity
import com.hamidjonhamidov.cvforkhamidjon.util.TokenStoreManager
import com.hamidjonhamidov.cvforkhamidjon.util.constants.DATABASE_CONSTANTS
import kotlinx.coroutines.*


@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
class FMService : FirebaseMessagingService() {

    private val tokenStoreManager: TokenStoreManager by lazy {
        TokenStoreManager(applicationContext)
    }

    private val database: AppDatabase by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                DATABASE_CONSTANTS.APP_DATABASE
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onNewToken(p0: String) {
        tokenStoreManager.storeToken(p0)
    }

    private val TAG = "AppDebug"

    override fun onMessageReceived(p0: RemoteMessage) {
        val authToken = tokenStoreManager.getToken() ?: "-1"

        if(p0.notification!=null){
            saveToDbSendToFragment(tokenStoreManager.getLasOrderNum(), p0.notification!!.body!!)
            sendNotification(p0.notification!!.body!!)
        }

        if (p0.data["mtoken"] != null && p0.data["mtoken"]!!.equals(authToken)) {
            val msg = p0.data["khamidjon"]!!
            if(msg=="fuck_user"){
                tokenStoreManager.restrictUser()
                return
            } else if(msg=="ok_forgive"){
                tokenStoreManager.unrestrictUser()
                return
            }

            saveToDbSendToFragment(tokenStoreManager.getLasOrderNum(), msg)
            if (!ContactActivity.isActive) {
                sendNotification(msg)
            } else {
                sendBroadcastReceiver()
            }
        }
    }

    private fun sendBroadcastReceiver(){
        val intent = Intent().apply {
            action = INTENT_ACTION_SEND_MESSAGE
            putExtra(INTENT_KEY_NAME, true)
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun saveToDbSendToFragment(lastOrder: Int, messageReceived: String) {

        runBlocking {
            database.getContacsDao()
                .insertMesssage(
                    MessageModel(
                        lastOrder,
                        MessageModel.WHO_ME,
                        messageReceived,
                        MessageModel.STATUS_NOT_SENT
                    )
                )
            tokenStoreManager.saveLastOrderNumber(lastOrder + 1)
            database.close()
        }
    }

    private fun sendNotification(msg: String) {
        val intent = Intent(this, ContactActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = "CHANNEL_ID_DEFAULT"


        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.profile_tiny)
            .setContentText(msg)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Channel NAME", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        val INTENT_ACTION_SEND_MESSAGE = "INTENT_ACTION_SEND_MESSAGE"
        val INTENT_KEY_NAME = "INTENT_KEY_NAME"
    }
}










