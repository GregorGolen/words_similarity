package com.example.demo.openaicommunicator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api/")
@RestController
@RequiredArgsConstructor
class AiController {

    @Autowired
    private final AiService aiService;

    @PostMapping("/analyze-similarity")
    public ResponseEntity<Map<String, Object>> analyzeSimilarity(@RequestBody TextRequestDTO request) throws Exception {
        List<String> texts = List.of(request.getWord1(), request.getWord2());
        List<List<Double>> embeddings = aiService.checkEmbeddings(texts);

        Map<String, Object> response = new HashMap<>();
        response.put("embeddings1", embeddings.get(0));
        response.put("embeddings2", embeddings.get(1));

        return ResponseEntity.ok(response);
    }
}

