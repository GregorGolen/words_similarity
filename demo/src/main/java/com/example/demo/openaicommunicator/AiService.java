package com.example.demo.openaicommunicator;

import com.theokanning.openai.embedding.Embedding;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
class AiService {

    @Autowired
    private ResourceLoader resourceLoader;

    public List<List<Double>> checkEmbeddings(List<String> texts) throws IOException {
        try {
            Properties prop = new Properties();
            try (InputStream input = resourceLoader.getResource("classpath:config.properties").getInputStream()) {
                prop.load(input);
            } catch (IOException e) {
                log.error("Error loading properties file: ", e);
            }
            String openAIKey = prop.getProperty("openai.api.key");

            OpenAiService openAiService = new OpenAiService(openAIKey);

            EmbeddingRequest request = EmbeddingRequest.builder()
                    .model("text-embedding-ada-002")
                    .input(texts)
                    .build();

            return openAiService.createEmbeddings(request)
                    .getData()
                    .stream()
                    .sorted(Comparator.comparing(Embedding::getIndex))
                    .map(Embedding::getEmbedding)
                    .toList();

        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}

