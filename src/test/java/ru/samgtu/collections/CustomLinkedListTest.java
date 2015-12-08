package ru.samgtu.collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by Nikolay on 04.11.2015.
 */
public class CustomLinkedListTest {

    private static final Logger LOG = LogManager.getLogger(CustomLinkedListTest.class);

    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
    }

    @Test
    public void testAddGetIsEmptySize() {
        LOG.debug("start Add Get Size IsEmpty test\n");
        CustomLinkedList<ClassForTest> linkedList = new CustomLinkedList<>();

        try {
            linkedList.add(null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }
        try {
            linkedList.get(10);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }
        try {
            linkedList.get(-1);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }
        try {
            linkedList.getFirst();
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IllegalStateException);
        }
        try {
            linkedList.getLast();
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IllegalStateException);
        }

        boolean empty = linkedList.isEmpty();
        Assert.assertTrue(empty);
        ClassForTest c1 = new ClassForTest("test1", 1);
        linkedList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        linkedList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        linkedList.add(c3);
        ClassForTest c4 = new ClassForTest("test4", 4);
        linkedList.add(c4);
        ClassForTest c5 = new ClassForTest("test5", 5);
        linkedList.add(c5);
        empty = linkedList.isEmpty();
        Assert.assertFalse(empty);

        try {
            linkedList.get(linkedList.size());
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }

        Assert.assertTrue(linkedList.size() == 5);
        ClassForTest value1 = linkedList.get(2);
        Assert.assertTrue(c3 == value1);
        Assert.assertEquals(c3, value1);
        ClassForTest c6 = new ClassForTest("test6", 6);
        linkedList.add(2, c6);
        ClassForTest value2 = linkedList.get(2);
        Assert.assertTrue(c6 == value2);
        Assert.assertEquals(c6, value2);
        ClassForTest value3 = linkedList.get(3);
        Assert.assertTrue(c3 == value3);
        Assert.assertEquals(c3, value3);
        LOG.debug("end\n");
    }

    @Test
    public void testDelete() {
        LOG.debug("start Delete test\n");
        CustomLinkedList<ClassForTest> linkedList = new CustomLinkedList<>();

        try {
            linkedList.delete(null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }
        try {
            linkedList.delete(10);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }
        try {
            linkedList.delete(-1);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }
        try {
            linkedList.deleteFirst();
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IllegalStateException);
        }
        try {
            linkedList.deleteLast();
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IllegalStateException);
        }

        ClassForTest c1 = new ClassForTest("test1", 1);
        linkedList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        linkedList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        linkedList.add(c3);
        ClassForTest c4 = new ClassForTest("test4", 4);
        linkedList.add(c4);
        ClassForTest c5 = new ClassForTest("test5", 5);
        linkedList.add(c5);
        ClassForTest c6 = new ClassForTest("test6", 6);
        linkedList.add(c6);
        ClassForTest c7 = new ClassForTest("test7", 7);
        linkedList.add(c7);
        ClassForTest c8 = new ClassForTest("test8", 8);
        linkedList.add(c8);

        try {
            linkedList.delete(linkedList.size());
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }

        ClassForTest delete1 = linkedList.delete(1);
        Assert.assertTrue(linkedList.size() == 7);
        Assert.assertEquals(linkedList.get(1), c3);
        Assert.assertEquals(linkedList.get(1), new ClassForTest("test3", 3));
        Assert.assertEquals(delete1, c2);
        Assert.assertTrue(delete1 == c2);

        ClassForTest delete2 = linkedList.deleteFirst();
        Assert.assertTrue(linkedList.size() == 6);
        Assert.assertEquals(linkedList.getFirst(), c3);
        Assert.assertEquals(linkedList.getFirst(), new ClassForTest("test3", 3));
        Assert.assertEquals(delete2, c1);
        Assert.assertTrue(delete2 == c1);

        ClassForTest delete3 = linkedList.deleteLast();
        Assert.assertTrue(linkedList.size() == 5);
        Assert.assertEquals(linkedList.getLast(), c7);
        Assert.assertEquals(linkedList.getLast(), new ClassForTest("test7", 7));
        Assert.assertEquals(delete3, c8);
        Assert.assertTrue(delete3 == c8);

        Assert.assertTrue(linkedList.delete(c5));
        Assert.assertTrue(linkedList.size() == 4);
        Assert.assertEquals(linkedList.get(2), c6);
        Assert.assertEquals(linkedList.get(2), new ClassForTest("test6", 6));

        Assert.assertTrue(linkedList.delete(new ClassForTest("test6", 6)));
        Assert.assertTrue(linkedList.size() == 3);
        Assert.assertEquals(linkedList.get(2), c7);
        Assert.assertEquals(linkedList.get(2), new ClassForTest("test7", 7));

        Assert.assertFalse(linkedList.delete(c1));
        LOG.debug("end\n");
    }

    @Test
    public void testSet() {
        LOG.debug("start Set test\n");
        CustomLinkedList<ClassForTest> linkedList = new CustomLinkedList<>();

        try {
            linkedList.set(10, new ClassForTest("test1", 1));
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }
        try {
            linkedList.set(-1, new ClassForTest("test1", 1));
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }

        ClassForTest c1 = new ClassForTest("test1", 1);
        linkedList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        linkedList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        linkedList.add(c3);
        ClassForTest c4 = new ClassForTest("test4", 4);
        linkedList.set(1, c4);

        try {
            linkedList.set(1, null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }

        Assert.assertTrue(linkedList.get(1) == c4);
        Assert.assertEquals(linkedList.get(1), c4);
        LOG.debug("end\n");
    }

    @Test
    public void testIndexOf() {
        LOG.debug("start IndexOf test\n");
        CustomLinkedList<ClassForTest> linkedList = new CustomLinkedList<>();
        ClassForTest c1 = new ClassForTest("test1", 1);
        linkedList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        linkedList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        linkedList.add(c3);

        try {
            linkedList.indexOf(null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }

        Assert.assertTrue(linkedList.indexOf(c1) == 0);
        Assert.assertTrue(linkedList.indexOf(new ClassForTest("test1", 1)) == 0);
        Assert.assertTrue(linkedList.indexOf(c2) == 1);
        Assert.assertTrue(linkedList.indexOf(new ClassForTest("test2", 2)) == 1);
        Assert.assertTrue(linkedList.indexOf(c3) == 2);
        Assert.assertTrue(linkedList.indexOf(new ClassForTest("test3", 3)) == 2);
        ClassForTest c4 = new ClassForTest("test4", 4);
        linkedList.set(1, c4);
        Assert.assertTrue(linkedList.indexOf(c4) == 1);
        Assert.assertTrue(linkedList.indexOf(new ClassForTest("test4", 4)) == 1);
        Assert.assertTrue(linkedList.indexOf(new ClassForTest("test5", 5)) == -1);
        LOG.debug("end\n");
    }

    @Test
    public void testIterator() {
        LOG.debug("start Iterator test\n");
        CustomLinkedList<ClassForTest> linkedList = new CustomLinkedList<>();

        try {
            linkedList.iterator();
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IllegalStateException);
        }

        ClassForTest c1 = new ClassForTest("test1", 1);
        linkedList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        linkedList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        linkedList.add(c3);
        ClassForTest c4 = new ClassForTest("test4", 4);
        linkedList.add(c4);
        ClassForTest c5 = new ClassForTest("test5", 5);
        linkedList.add(c5);
        ClassForTest c6 = new ClassForTest("test6", 6);
        linkedList.add(c6);
        ClassForTest c7 = new ClassForTest("test7", 7);
        linkedList.add(c7);
        ClassForTest c8 = new ClassForTest("test8", 8);
        linkedList.add(c8);
        Iterator<ClassForTest> iterator = linkedList.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), c1);
        Assert.assertTrue(iterator.hasNext());
        iterator.remove();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), c3);
        ClassForTest c9 = new ClassForTest("test9", 9);
        linkedList.add(2, c9);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), c9);
        linkedList.delete(3);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), c5);
        ClassForTest c10 = new ClassForTest("test10", 10);
        linkedList.set(4, c10);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), c10);
        iterator.next();
        iterator.next();
        Assert.assertFalse(iterator.hasNext());
        LOG.debug("end\n");
    }

    @Test
    public void testClone() {
        LOG.debug("start Clone test\n");
        CustomLinkedList<ClassForTest> linkedList = new CustomLinkedList<>();

        try {
            linkedList.clone();
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IllegalStateException);
        }

        ClassForTest c1 = new ClassForTest("test1", 1);
        linkedList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        linkedList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        linkedList.add(c3);

        CustomLinkedList<ClassForTest> clone = (CustomLinkedList<ClassForTest>) linkedList.clone();
        Assert.assertFalse(clone == linkedList);
        Assert.assertTrue(clone.size() == linkedList.size());

        ClassForTest c4 = clone.get(0);
        ClassForTest c5 = clone.get(1);
        ClassForTest c6 = clone.get(2);

        Assert.assertFalse(c1 == c4);
        Assert.assertEquals(c1, c4);
        Assert.assertFalse(c2 == c5);
        Assert.assertEquals(c2, c5);
        Assert.assertFalse(c3 == c6);
        Assert.assertEquals(c3, c6);
        LOG.debug("end\n");
    }

    @Test
    public void testToArray() {
        LOG.debug("start ToArray test\n");
        CustomLinkedList<ClassForTest> linkedList = new CustomLinkedList<>();

        try {
            linkedList.toArray(null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IllegalStateException);
        }

        ClassForTest c1 = new ClassForTest("test1", 1);
        linkedList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        linkedList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        linkedList.add(c3);

        try {
            linkedList.toArray(null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }

        ClassForTest[] array = linkedList.toArray(new ClassForTest[0]);
        Assert.assertTrue(c1 == array[0]);
        Assert.assertEquals(c1, array[0]);
        Assert.assertTrue(c2 == array[1]);
        Assert.assertEquals(c2, array[1]);
        Assert.assertTrue(c3 == array[2]);
        Assert.assertEquals(c3, array[2]);
        LOG.debug("end\n");
    }
}
