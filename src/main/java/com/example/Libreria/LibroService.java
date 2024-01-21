package com.example.Libreria;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    @Value("${google.books.api.key}")
    private String apiKey;
    private final String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=search+terms";

    public List<Libro> getAllBooks() {
        return libroRepository.findAll();
    }

    public void syncBooksFromExternalAPI(){
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "&key=" + apiKey;

        String jsonResponse = restTemplate.getForObject(url,String.class);
        System.out.print(jsonResponse);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode itemsNode = root.path("items");
            for (JsonNode itemNode : itemsNode) {
                String titolo = "", autore = "";
                double prezzo = 0.0;
                int annoPubblicazione = 0;

                JsonNode volumeInfoNode = itemNode.path("volumeInfo");
                if(volumeInfoNode.path("title") != null)
                {
                    titolo = volumeInfoNode.path("title").asText();
                }
                if(volumeInfoNode.path("authors").get(0) != null)
                {
                    autore = volumeInfoNode.path("authors").get(0).asText();
                }
                if(volumeInfoNode.path("publishedDate") != null)
                {
                    annoPubblicazione = volumeInfoNode.path("publishedDate").asInt();
                }

                JsonNode volumeInfoNode2 = itemNode.path("saleInfo");
                if(volumeInfoNode2.path("amount").asDouble() != 0)
                {
                    prezzo = volumeInfoNode2.path("amount").asDouble();
                }

                Libro libro = new Libro();
                libro.setTitolo(titolo);
                libro.setAutore(autore);
                libro.setAnnoPubblicazione(annoPubblicazione);
                libro.setPrezzo(prezzo);

                libroRepository.save(libro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Libro getBookById(Long id) {
        return libroRepository.findById(id).orElse(null);
    }
}
