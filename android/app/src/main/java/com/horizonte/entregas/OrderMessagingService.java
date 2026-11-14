package com.horizonte.entregas;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class OrderMessagingService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "pedidos_entrega";
    @Override public void onNewToken(String token) {
        super.onNewToken(token);
        // TODO: enviar o token ao backend autenticado e associá-lo ao UID do entregador.
    }
    @Override public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);
        String title = message.getNotification() != null && message.getNotification().getTitle() != null
                ? message.getNotification().getTitle() : message.getData().getOrDefault("title", "Novo pedido disponível");
        String body = message.getNotification() != null && message.getNotification().getBody() != null
                ? message.getNotification().getBody() : message.getData().getOrDefault("body", "Abra o Horizonte Entregas para conferir.");
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= 26) manager.createNotificationChannel(
                new NotificationChannel(CHANNEL_ID, "Pedidos de entrega", NotificationManager.IMPORTANCE_HIGH));
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pending = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle(title).setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true).setContentIntent(pending);
        manager.notify((int)System.currentTimeMillis(), notification.build());
    }
}
