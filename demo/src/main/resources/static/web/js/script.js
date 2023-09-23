function analyzeSimilarity() {
    const word1 = document.getElementById("word1").value;
    const word2 = document.getElementById("word2").value;

    fetch('/api/analyze-similarity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ word1, word2 })
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById("display-word1").textContent = word1;
            document.getElementById("display-word2").textContent = word2;
            document.getElementById("similarity-result").textContent = `Similarity: ${data.similarity}`;
            document.getElementById("embeddings1").value = data.embeddings1.join(', ');
            document.getElementById("embeddings2").value = data.embeddings2.join(', ');
        })
        .catch(error => {
            console.error("There was an error analyzing the similarity:", error);
        });
}
