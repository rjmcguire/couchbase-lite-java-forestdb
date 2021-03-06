/**
 * Created by Hideki Itakura on 10/20/2015.
 * Copyright (c) 2015 Couchbase, Inc All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.couchbase.cbforest;

import java.io.File;
import java.util.Arrays;

public class C4ViewTest extends C4TestCase {

    public static final String TAG = C4ViewTest.class.getSimpleName();

    public static final String VIEW_INDEX_FILENAME = "forest_temp.view.index";

    protected View view = null;
    protected File indexFile = null;
    protected Indexer indexer = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        indexFile = new File(mContext.getFilesDir(), VIEW_INDEX_FILENAME);
        if (indexFile.exists()) {
            if (!indexFile.delete()) {
                fail();
            }
        }
        view = new View(db, indexFile.getPath(), Database.Create, 0, null, "myview", "1");
        assertNotNull(view);
    }

    @Override
    protected void tearDown() throws Exception {
        if (view != null) {
            view.delete();
            view = null;
        }
        super.tearDown();
    }

    public void testEmptyState() {
        assertEquals(0, view.getTotalRows());
        assertEquals(0, view.getLastSequenceIndexed());
        assertEquals(0, view.getLastSequenceChangedAt());
    }

    protected void createIndex() throws ForestException {
        for (int i = 1; i <= 100; i++) {
            String docID = String.format("doc-%03d", i);
            createRev(docID, kRevID, kBody.getBytes());
        }
        updateIndex();
    }

    protected void updateIndex() throws ForestException {
        boolean commit = false;
        View[] views = {view};
        indexer = new Indexer(views);
        try {
            DocumentIterator itr = indexer.iterateDocuments();
            Document doc;
            while ((doc = itr.nextDocument()) != null) {
                try {
                    // Index 'doc':
                    Object[] keys = new Object[2];
                    byte[][] values = new byte[2][];
                    keys[0] = doc.getDocID();
                    keys[1] = doc.getSelectedSequence();
                    values[0] = "1234".getBytes();
                    values[1] = "1234".getBytes();
                    indexer.emit(doc, 0, keys, values);
                } finally {
                    doc.free();
                }
            }
            commit = true;
        } finally {
            indexer.endIndex(commit);
        }
    }

    public void testCreateIndex() throws ForestException {
        createIndex();

        assertEquals(200, view.getTotalRows());
        assertEquals(100, view.getLastSequenceIndexed());
        assertEquals(100, view.getLastSequenceChangedAt());
    }

    public void testQueryIndex() throws ForestException {
        createIndex();

        QueryIterator e = view.query();
        assertNotNull(e);

        int i = 0;
        while (e.next()) {
            ++i;
            String buff;
            if (i <= 100) {
                buff = String.format("%d", i);
                assertEquals(i, e.sequence());
            } else {
                buff = String.format("\"doc-%03d\"", i - 100);
                assertEquals(i - 100, e.sequence());
            }
            assertEquals(buff, new String(e.keyJSON()));
            assertTrue(Arrays.equals("1234".getBytes(), e.valueJSON()));
        }
        assertEquals(200, i);
    }

    public void testIndexVersion() throws ForestException {
        createIndex();

        // Reopen view with same version string:
        view.close();
        view = null;
        view = new View(db, indexFile.getPath(), Database.Create, 0, null, "myview", "1");
        assertNotNull(view);

        assertEquals(200, view.getTotalRows());
        assertEquals(100, view.getLastSequenceIndexed());
        assertEquals(100, view.getLastSequenceChangedAt());

        // Reopen view with different version string:
        view.close();
        view = null;
        view = new View(db, indexFile.getPath(), Database.Create, 0, null, "myview", "2");
        assertNotNull(view);

        assertEquals(0, view.getTotalRows());
        assertEquals(0, view.getLastSequenceIndexed());
        assertEquals(0, view.getLastSequenceChangedAt());
    }

    public void testDocPurge() throws ForestException {
        testDocPurge(false);
    }

    public void testDocPurgeWithCompact() throws ForestException {
        testDocPurge(true);
    }

    protected void testDocPurge(boolean compactAfterPurge) throws ForestException {
        createIndex();

        long lastIndexed = view.getLastSequenceIndexed();
        long lastSeq = db.getLastSequence();
        assertEquals(lastIndexed, lastSeq);

        boolean commit = false;
        db.beginTransaction();
        try {
            db.purgeDoc("doc-023");
            commit = true;
        } finally {
            db.endTransaction(commit);
        }

        if (compactAfterPurge)
            db.compact();

        // ForestDB assigns sequences to deletions, so the purge bumped the db's sequence,
        // invalidating the view index:
        lastIndexed = view.getLastSequenceIndexed();
        lastSeq = db.getLastSequence();
        assertTrue(lastIndexed < lastSeq);

        updateIndex();

        // Verify that the purged doc is no longer in the index:
        QueryIterator itr = view.query();
        assertNotNull(itr);
        int i = 0;
        while (itr.next())
            ++i;
        assertEquals(198, i); // 2 rows of doc-023 are gone
    }

    /**
     * @param odd 0 or 1
     */
    protected void createIndex(int odd) throws ForestException {
        for (int i = 1; i <= 100; i++) {
            String docID = String.format("doc-%03d", i);
            createRev(docID, kRevID, kBody.getBytes());
        }

        boolean commit = false;
        View[] views = {view};
        indexer = new Indexer(views);
        try {
            DocumentIterator itr = indexer.iterateDocuments();
            try {
                int i = 1;
                Document doc;
                while ((doc = itr.nextDocument()) != null) {
                    if (i % 2 == odd) {
                        Object[] keys = new Object[2];
                        byte[][] values = new byte[2][];
                        keys[0] = doc.getDocID();
                        keys[1] = doc.getSelectedSequence();
                        values[0] = "1234".getBytes();
                        values[1] = "1234".getBytes();
                        indexer.emit(doc, 0, keys, values);
                    } else {
                        indexer.emit(doc, 0, new Object[0], null);
                    }
                    i++;
                }
            } finally {
            }
            commit = true;
        } finally {
            indexer.endIndex(commit);
        }
    }

    public void testCreateIndexOdd() throws ForestException {
        // Index Odd number document
        createIndex(1);
        assertEquals(100, view.getTotalRows());
        assertEquals(100, view.getLastSequenceIndexed());
        assertEquals(99, view.getLastSequenceChangedAt());
    }

    public void testCreateIndexEven() throws ForestException {
        // Index Even number document
        createIndex(0);
        assertEquals(100, view.getTotalRows());
        assertEquals(100, view.getLastSequenceIndexed());
        assertEquals(100, view.getLastSequenceChangedAt());
    }
}
