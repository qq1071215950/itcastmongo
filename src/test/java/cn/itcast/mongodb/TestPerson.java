package cn.itcast.mongodb;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

public class TestPerson {
    MongoCollection<Person> personCollection;

    @Before
    public void init() {
        //定义对象的解码注册器
        CodecRegistry pojoCodecRegistry = CodecRegistries. fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()) );
        // 建立连接
        MongoClient mongoClient = MongoClients.create("mongodb://47.94.150.254:27017");
        // 选择数据库 并且 注册解码器
        MongoDatabase mongoDatabase = mongoClient.getDatabase("testdb") .withCodecRegistry(pojoCodecRegistry);
        // 选择表
        this.personCollection = mongoDatabase .getCollection("person", Person.class);
    }

    @Test
    public void testInsert() {
        Person person = new Person(ObjectId.get(), "张三", 20, new Address("人民路", "上海市", "666666"));
        this.personCollection.insertOne(person);
        System.out.println("插入数据成功");
    }

    @Test
    public void testQuery() {
        this.personCollection.find(Filters.eq("name", "张三")).forEach((Consumer<? super Person>) person -> {
            System.out.println(person);
        });
    }

    @Test
    public void testUpdate() {
        UpdateResult updateResult = this.personCollection.updateMany(Filters.eq("name", "张三"), Updates.set("age", 22));
        System.out.println(updateResult);
    }
}
