/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.outdated.mdbc.api;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import sun.security.acl.OwnerImpl;

/**
 *
 * @author ebirn
 */
public interface MongoConnection extends AutoCloseable {
    
    public DBCollection getCollection(String collection);
    public DBObject findOne(String collection, DBObject query);
    public DBCursor find(String collection, DBObject query);

    @Override
    void close();
    
}
