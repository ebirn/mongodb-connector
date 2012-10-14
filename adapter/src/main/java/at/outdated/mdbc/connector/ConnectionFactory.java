/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.outdated.mdbc.connector;

import at.outdated.mdbc.api.MongoConnection;
import at.outdated.mdbc.api.MongoConnectionFactory;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.*;
import javax.security.auth.Subject;
import javax.validation.constraints.Size;

/**
 *
 * @author ebirn
 */

@ConnectionDefinition(connectionFactory = MongoConnectionFactory.class,
connectionFactoryImpl = JCAMongoConnectionFactory.class,
connection = MongoConnection.class,
connectionImpl = JCAMongoConnection.class)
public class ConnectionFactory implements ManagedConnectionFactory, ResourceAdapterAssociation, Serializable {
    
    private final Logger log = Logger.getLogger("at.outdated.mdbc");
    private PrintWriter writer;
    private MongoDbAdapter mongoAdapter;
    
    private Integer maxConnections;
    private MongoURI mongoURI;
    
    private Mongo mongo;
    private DB mongoDB;
    
    public ConnectionFactory() {
        log.fine("#ConnectionFactory.constructor");
    }

    @Override
    public Object createConnectionFactory() throws ResourceException {
        connect();
        return new JCAMongoConnectionFactory(this, null, log, mongoDB);
    }

    @Override
    public Object createConnectionFactory(ConnectionManager cm) throws ResourceException {
        connect();
        return new JCAMongoConnectionFactory(this, cm, log, mongoDB);
    }

    @Override
    public ManagedConnection createManagedConnection(Subject sbjct, ConnectionRequestInfo cri) throws ResourceException {
        connect();
        log.info("new managed connection to " + mongoURI.toString());
        return new Connection(log, this, cri, mongoDB);
    }

    @Override
    public ResourceAdapter getResourceAdapter() {
        return mongoAdapter;
    }

    @Override
    public void setLogWriter(PrintWriter writer) throws ResourceException {
        this.writer = writer;
    }
    
    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return writer;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
        this.mongoAdapter = (MongoDbAdapter) ra;
    }

    @Override
    public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, ConnectionRequestInfo info) throws ResourceException {
        log.info("#ConnectionFactory.matchManagedConnections Subject " + subject + " Info: " + info);
        for (Iterator it = connectionSet.iterator(); it.hasNext();) {
            Object object = it.next();
            if (object instanceof Connection) {
                Connection gmc = (Connection) object;
                ConnectionRequestInfo connectionRequestInfo = gmc.getConnectionRequestInfo();
                if ((info == null) || connectionRequestInfo.equals(info)) {
                    return gmc;
                }
            }else{
                log.info("#ConnectionFactory.matchManagedConnections " + object + " is not a Connection");
            }
        }
        return null;
    }

    @Size(min = 1)
    @ConfigProperty(defaultValue = "50", supportsDynamicUpdates = true, description = "Maximum number of concurrent connections from different processes that an EIS instance can supportMaximum number of concurrent connections from different processes that an EIS instance can support")
    public void setMaxConnections(Integer maxNumberOfConnections) {
        this.maxConnections = maxNumberOfConnections;
        
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    @Size(min = 1)
    @ConfigProperty(defaultValue = "mongodb://localhost/test", supportsDynamicUpdates = false, description = "Mongo DB connection url: mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]")
    public void setConnectionUrl(String url) {
        this.mongoURI = new MongoURI(url);
    }
    
    public String getConnectionUrl() {
        return this.mongoURI.toString();
    }
    
    
    private void connect() throws ResourceException {
        if(this.mongo!=null) return;
        
        try {
            this.mongo = new Mongo(mongoURI);
            this.mongoDB = mongo.getDB(mongoURI.getDatabase());
            
            String username = mongoURI.getUsername();
            if(username != null && username.isEmpty()!=true) {
                mongoDB.authenticate(username, mongoURI.getPassword());
            }
            log.info("MongoDB connector: " + mongoURI);
        }
        catch(Exception e) {
            throw new ResourceException("failed to connect to " + mongoURI);
        }
        
        
    }
    
}
