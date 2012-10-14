/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.outdated.mdbc.connector;

import at.outdated.mdbc.api.MongoConnection;
import at.outdated.mdbc.api.MongoConnectionFactory;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;
import com.mongodb.DB;

/**
 *
 * @author ebirn
 */
public class JCAMongoConnectionFactory implements MongoConnectionFactory {

    private ManagedConnectionFactory mcf;
    private Reference reference;
    private ConnectionManager cm;
    private Logger log;
    
    private DB mongoDB;

    public JCAMongoConnectionFactory(ManagedConnectionFactory mcf, ConnectionManager cm, Logger log, DB mongoDB) {
        this.mcf = mcf;
        this.cm = cm;
        this.log = log;
        this.mongoDB = mongoDB;
    }
    
    
    
    @Override
    public MongoConnection getConnection() {
        log.info("#MongoConnectionFactory.getConnection " + this.cm + " MCF: " + this.mcf);
        try {
            return ((MongoConnection) cm.allocateConnection(mcf, null));
        } catch (ResourceException ex) {
            throw new RuntimeException(ex.getMessage(),ex);
        }
    }

    @Override
    public Reference getReference() throws NamingException {
        return reference;
    }

    @Override
    public void setReference(Reference rfrnc) {
        this.reference = rfrnc;
    }
    
    
    
}
