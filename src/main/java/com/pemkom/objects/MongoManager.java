package com.pemkom.objects;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.jsr310.Jsr310CodecProvider;

public class MongoManager {

    private static MongoClient mongoClient;
    private static final String DATABASE_NAME = "MBG";

    // URI sudah dienkripsi, tidak ada plaintext di kode!
    // Ganti nilai ini dengan hasil Encrypted dari Step 3
    private static final String ENCRYPTED_URI = "GGQfa4ThZf8eqxboImLe7Lyh2iHKDEi0HYTtxE6GEmI=";

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            try {
                String mongoUri = EncryptionUtils.decrypt(ENCRYPTED_URI);

                CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(new Jsr310CodecProvider()),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(
                                PojoCodecProvider.builder().automatic(true).build()
                        )
                );

                MongoClientSettings settings = MongoClientSettings.builder()
                        .applyConnectionString(new com.mongodb.ConnectionString(mongoUri))
                        .codecRegistry(pojoCodecRegistry)
                        .build();

                mongoClient = MongoClients.create(settings);

            } catch (Exception e) {
                throw new RuntimeException("Gagal dekripsi URI: " + e.getMessage());
            }
        }

        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromProviders(new Jsr310CodecProvider()),
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true).build()
                )
        );

        return mongoClient.getDatabase(DATABASE_NAME).withCodecRegistry(pojoCodecRegistry);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
