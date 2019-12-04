package cn.itcast.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;

public class TestCRUD {
    private MongoCollection<Document> mongoCollection;

    @Before
    public void init() {
        // 建立连接
        MongoClient mongoClient = MongoClients.create("mongodb://172.16.55.185:27017");
        // 选择数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("testdb");
        // 选择表
        this.mongoCollection = mongoDatabase.getCollection("user");
    }

}
