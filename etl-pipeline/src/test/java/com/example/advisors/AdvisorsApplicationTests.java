package com.example.advisors;

import com.example.advisors.helper.Helper;
import com.example.advisors.service.ChatService;
import com.example.advisors.service.DataLoader;
import com.example.advisors.service.DataTransformer;
import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdvisorsApplicationTests {

    @Autowired
    private ChatService chatService;

    @Autowired
    private DataLoader dataLoader;

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private DataTransformer dataTransformer;

    @Test
    void saveDataToVectorDatabase(){
        System.out.println("saving data to database");
        this.chatService.saveData(Helper.getData());
        System.out.println("data is saved successfully");
    }

    @Test
    void saveJsonDataToVectorDatabase(){
        System.out.println("saving json data to database");
        var documents = this.dataLoader.loadDocumentsFromJson();
        System.out.println(documents + " " + documents.size());
        System.out.println("data is saved successfully");
    }

    @Test
    void savePdfDataToVectorDatabase(){
        System.out.println("saving pdf data to database");
        var documents = this.dataLoader.loadDocumentsFromPdf();
        System.out.println(documents + " " + documents.size());
        System.out.println("data is saved successfully");

        var transformedDocument = this.dataTransformer.transform(documents);
        System.out.println("Transformed data " + transformedDocument.size());

//        Adding data to database now
        this.vectorStore.add(transformedDocument);
        System.out.println("Done");
    }

}
