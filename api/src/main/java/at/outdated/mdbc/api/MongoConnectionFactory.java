/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.outdated.mdbc.api;

import java.io.Serializable;
import javax.resource.Referenceable;

/**
 *
 * @author ebirn
 */
public interface MongoConnectionFactory extends Serializable, Referenceable {
    
    MongoConnection getConnection();
}
