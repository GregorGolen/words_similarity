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

            const embeddings1 = data.embeddings1;
            const embeddings2 = data.embeddings2;

            const similarity = cosineSimilarity(embeddings1, embeddings2);

            document.getElementById("similarity-result").textContent = `Similarity: ${similarity.toFixed(2)}`;
            document.getElementById("embeddings1").value = embeddings1.join(', ');
            document.getElementById("embeddings2").value = embeddings2.join(', ');
        })
        .catch(error => {
            console.error("There was an error analyzing the similarity:", error);
        });
}

function cosineSimilarity(A, B) {
    var dotproduct = 0;
    var mA = 0;
    var mB = 0;

    for(var i = 0; i < A.length; i++) {
        dotproduct += A[i] * B[i];
        mA += A[i] * A[i];
        mB += B[i] * B[i];
    }

    mA = Math.sqrt(mA);
    mB = Math.sqrt(mB);
    var similarity = dotproduct / (mA * mB);

    return similarity;
}

