package com.ltrha.ticket.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.GmailScopes;
import com.ltrha.ticket.config.google.GoogleServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
//@RequiredArgsConstructor
//public class Init implements CommandLineRunner {
//    private final GoogleServiceProvider googleServiceProvider;
//    @Override
//    public void run(String... args) throws Exception {
//        List<String> SCOPES = List.of(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_SEND, GmailScopes.GMAIL_MODIFY);
//        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//
//    }
//}
