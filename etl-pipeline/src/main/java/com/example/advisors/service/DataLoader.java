package com.example.advisors.service;

import org.springframework.ai.document.Document;

import java.util.List;

public interface DataLoader {

    public List<Document> loadDocumentsFromJson();

    public List<Document> loadDocumentsFromPdf();

}
