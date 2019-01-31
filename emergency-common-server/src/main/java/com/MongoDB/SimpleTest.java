package com.MongoDB;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

/**
 * @author LI.HU
 * @date 2019/1/31   9:31
 * <p>Description:
 *
 * </p>
 */
public class SimpleTest {

    public static void main(String[] args) {
        Mongo mg = new Mongo("111.230.52.115", 27017);
        //查询所有的Database
        for (String name : mg.getDatabaseNames()){
            System.out.println("dbName:"+name);
        }
        //查询所以的聚集集合
        DB db = mg.getDB("local");
        for (String name : db.getCollectionNames()){
            System.out.println("collectionName:" + name);
        }

        DBCollection users = db.getCollection("users");
        //查询所有数据
        DBCursor cur = users.find();

        while(cur.hasNext()){
            System.out.println(cur.next());
        }
        System.out.println(cur.count());
        System.out.println(cur.getCursorId());
        System.out.println(JSON.serialize(cur));
    }

}
