package com.example.notificacao;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private TextView tvTitulo, tvMensagem;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage notificacao) {

        if(notificacao.getNotification() != null){

            String titulo = notificacao.getNotification().getTitle();
            String conteudo = notificacao.getNotification().getBody();
            
            enviarNotificacao(titulo, conteudo);

        }
    }

    private void enviarNotificacao(String titulo, String conteudo) {

        //Configurações iniciais para criar uma notificação
        String canal = getString(R.string.default_notification_channel_id);
        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Cria a notificação
        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this, canal)
                .setContentTitle(titulo)
                .setContentText(conteudo)
                .setSmallIcon(R.drawable.ic_camera_black_24dp)
                .setSound(uriSom);

        //Chama o gerenciador de notificação, para usar um serviço de sistema
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Verifica versão do Android a partir da Oreo, para configurar canal de notificação
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(canal, canal, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        //Eniva a notificação
        notificationManager.notify(0, notificacao.build());


    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
