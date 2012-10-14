/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.outdated.mongotestapp;

import at.outdated.mdbc.api.MongoConnectionFactory;
import at.outdated.mdbc.api.MongoConnection;
import com.mongodb.DBCollection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;

/**
 *
 * @author ebirn
 */
@Singleton
@LocalBean
@Startup
public class MongoTest {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    static final Logger log = Logger.getLogger("MongoTest");
    
    @Resource(name="mongoDummy")
    MongoConnectionFactory factory;

    
    @PostConstruct
    public void startup() {
        log.info("MongoTest start");
        log.log(Level.INFO, "factory: {0}", factory);
        
        //
        MongoConnection connection = factory.getConnection();
        log.log(Level.INFO, "produced connection: {0}", connection);
        
        DBCollection collection = connection.getCollection("test");
        long count = collection.count();
        
        log.info("collection elements: " + count);

    }
}
