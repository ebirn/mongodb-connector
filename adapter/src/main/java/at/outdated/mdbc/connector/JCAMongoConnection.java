/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.outdated.mdbc.connector;

import at.outdated.mdbc.api.MongoConnection;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DB;
import com.mongodb.DBObject;
import java.util.logging.Logger;
import javax.resource.spi.ConnectionRequestInfo;

/**
 *
 * @author ebirn
 */
public class JCAMongoConnection implements MongoConnection {
    
    private ConnectionRequestInfo connectionRequestInfo;
    private Connection genericManagedConnection;
    private Logger log;
    private ConnectionFactory gmcf;

    public JCAMongoConnection(Logger log, Connection genericManagedConnection, ConnectionFactory gmcf, ConnectionRequestInfo connectionRequestInfo) {
        this.log = log;
        log.info("#MongoConnection " + connectionRequestInfo + " " + toString());
        this.genericManagedConnection = genericManagedConnection;
        this.connectionRequestInfo = connectionRequestInfo;
        this.gmcf = gmcf;
    }

    @Override
    public DBObject findOne(String collection, DBObject query) {
        return getCollection(collection).findOne(query);
    }

    @Override
    public DBCursor find(String collection, DBObject query) {
        return getCollection(collection).find(query);
    }

    @Override
    public DBCollection getCollection(String collection) {
        return genericManagedConnection.getDB().getCollection(collection);
    }
    
    

    @Override
    public void close() {
        this.genericManagedConnection.close();
    }
    
       @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JCAMongoConnection other = (JCAMongoConnection) obj;
        if (this.connectionRequestInfo != other.connectionRequestInfo && (this.connectionRequestInfo == null || !this.connectionRequestInfo.equals(other.connectionRequestInfo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.connectionRequestInfo != null ? this.connectionRequestInfo.hashCode() : 0);
        return hash;
    }
    
}
