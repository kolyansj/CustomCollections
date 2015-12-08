package ru.samgtu.collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Iterator;

/**
 * Created by Nikolay on 04.11.2015.
 */
public abstract class CustomAbstractList<T> implements CustomCollection<T> {

    private static final Logger LOG = LogManager.getLogger(CustomAbstractList.class);

    private boolean modification;

    protected void modify() {
        LOG.trace("modify list");
        modification = true;
    }

    protected abstract class AbstractIterator<T> implements Iterator<T> {

        protected int current;

        protected AbstractIterator() {
            current = 0;
            update();
        }

        protected void check() {
            if (modification)  {
                update();
                modification = false;
            }
        }

        protected abstract void update();

        @Override
        public void remove() {
            check();
            delete(current);
        }
    }
}
