/*
 * Copyright (c) 2009, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.fortuna.ical4j.connector.jcr;

import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;

import net.fortuna.ical4j.connector.CardStore;
import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;

import org.jcrom.Jcrom;

/**
 * 
 *
 * @author Ben
 *
 * Created on: 23/01/2009
 *
 * $Id$
 */
public class JcrCardStore extends AbstractJcrObjectStore<JcrCardCollection>
    implements CardStore<JcrCardCollection> {

    /**
     * @param repository
     * @param path
     * @param jcrom
     */
    public JcrCardStore(Repository repository, String path, Jcrom jcrom) {
        super(repository, path, jcrom);
        
        // ensure appropriate classes are mapped..
        jcrom.map(JcrCardCollection.class);
        jcrom.map(JcrCard.class);
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.connector.ObjectStore#getCollection(java.lang.String)
     */
    @Override
    public JcrCardCollection getCollection(String id) throws ObjectStoreException, ObjectNotFoundException {
        try {
            JcrCardCollection collection = getJcrom().fromNode(JcrCardCollection.class, getNode().getNode(id));
            collection.setStore(this);
            return collection;
        }
        catch (PathNotFoundException e) {
            throw new ObjectNotFoundException("Collection not found", e);
        }
        catch (RepositoryException e) {
            throw new ObjectNotFoundException("Error retrieving collection", e);
        }
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.connector.jcr.AbstractJcrObjectStore#newCollection()
     */
    @Override
    protected JcrCardCollection newCollection() {
        return new JcrCardCollection();
    }
}