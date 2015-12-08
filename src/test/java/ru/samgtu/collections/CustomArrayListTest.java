package ru.samgtu.collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;

/**
 * Created by Nikolay on 04.11.2015.
 */
public class CustomArrayListTest {

    private static final Logger LOG = LogManager.getLogger(CustomArrayListTest.class);

    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
    }

    @Test
    public void testAddGetSize() {
        LOG.debug("start Add Get Size test\n");
        CustomArrayList<ClassForTest> arrayList = new CustomArrayList<>();

        try {
            arrayList.add(null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }
        try {
            arrayList.get(10);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }
        try {
            arrayList.get(-1);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }

        boolean empty = arrayList.isEmpty();
        Assert.assertTrue(empty);
        ClassForTest c1 = new ClassForTest("test1", 1);
        arrayList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        arrayList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        arrayList.add(c3);
        empty = arrayList.isEmpty();
        Assert.assertFalse(empty);

        try {
            arrayList.get(arrayList.size());
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }

        ClassForTest value1 = arrayList.get(0);
        Assert.assertEquals(c1, value1);
        Assert.assertTrue(c1 == value1);

        ClassForTest c4 = new ClassForTest("test4", 4);
        arrayList.add(1, c4);
        ClassForTest value2 = arrayList.get(1);
        Assert.assertEquals(c4, value2);
        Assert.assertTrue(c4 == value2);
        LOG.debug("end\n");
    }

    @Test
    public void testDelete() {
        LOG.debug("start Delete test\n");
        CustomArrayList<ClassForTest> arrayList = new CustomArrayList<>();

        try {
            arrayList.delete(null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }
        try {
            arrayList.delete(10);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }
        try {
            arrayList.delete(-1);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }

        ClassForTest c1 = new ClassForTest("test1", 1);
        arrayList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        arrayList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        arrayList.add(c3);
        ClassForTest c4 = new ClassForTest("test4", 4);
        arrayList.add(c4);
        ClassForTest c5 = new ClassForTest("test5", 5);
        arrayList.add(c5);

        try {
            arrayList.delete(arrayList.size());
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }

        ClassForTest delete1 = arrayList.delete(1);
        Assert.assertTrue(arrayList.size() == 4);
        Assert.assertEquals(arrayList.get(1), c3);
        Assert.assertEquals(arrayList.get(1), new ClassForTest("test3", 3));
        Assert.assertEquals(delete1, c2);
        Assert.assertTrue(delete1 == c2);

        Assert.assertTrue(arrayList.delete(c3));
        Assert.assertTrue(arrayList.size() == 3);
        Assert.assertEquals(arrayList.get(1), c4);
        Assert.assertEquals(arrayList.get(1), new ClassForTest("test4", 4));

        Assert.assertTrue(arrayList.delete(new ClassForTest("test4", 4)));
        Assert.assertTrue(arrayList.size() == 2);
        Assert.assertEquals(arrayList.get(1), c5);
        Assert.assertEquals(arrayList.get(1), new ClassForTest("test5", 5));

        Assert.assertFalse(arrayList.delete(c3));
        LOG.debug("end\n");
    }

    @Test
    public void testSet() {
        LOG.debug("start Set test\n");
        CustomArrayList<ClassForTest> arrayList = new CustomArrayList<>();

        try {
            arrayList.set(10, new ClassForTest("test1", 1));
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }
        try {
            arrayList.set(-1, new ClassForTest("test1", 1));
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof IndexOutOfBoundsException);
        }

        ClassForTest c1 = new ClassForTest("test1", 1);
        arrayList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        arrayList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        arrayList.add(c3);
        ClassForTest c4 = new ClassForTest("test4", 4);
        arrayList.set(1, c4);

        try {
            arrayList.set(1, null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }

        Assert.assertTrue(arrayList.get(1) == c4);
        Assert.assertEquals(arrayList.get(1), c4);
        LOG.debug("end\n");
    }

    @Test
    public void testIndexOf() {
        LOG.debug("start IndexOf test\n");
        CustomArrayList<ClassForTest> arrayList = new CustomArrayList<>();

        try {
            arrayList.indexOf(null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }

        ClassForTest c1 = new ClassForTest("test1", 1);
        arrayList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        arrayList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        arrayList.add(c3);
        Assert.assertTrue(arrayList.indexOf(c1) == 0);
        Assert.assertTrue(arrayList.indexOf(new ClassForTest("test1", 1)) == 0);
        Assert.assertTrue(arrayList.indexOf(c2) == 1);
        Assert.assertTrue(arrayList.indexOf(new ClassForTest("test2", 2)) == 1);
        Assert.assertTrue(arrayList.indexOf(c3) == 2);
        Assert.assertTrue(arrayList.indexOf(new ClassForTest("test3", 3)) == 2);
        ClassForTest c4 = new ClassForTest("test4", 4);
        arrayList.set(1, c4);
        Assert.assertTrue(arrayList.indexOf(c4) == 1);
        Assert.assertTrue(arrayList.indexOf(new ClassForTest("test4", 4)) == 1);
        Assert.assertTrue(arrayList.indexOf(new ClassForTest("test5", 5)) == -1);
        LOG.debug("end\n");
    }

    @Test
    public void testIterator() {
        LOG.debug("start Iterator test\n");
        CustomArrayList<ClassForTest> arrayList = new CustomArrayList<>();
        ClassForTest c1 = new ClassForTest("test1", 1);
        arrayList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        arrayList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        arrayList.add(c3);
        ClassForTest c4 = new ClassForTest("test4", 4);
        arrayList.add(c4);
        ClassForTest c5 = new ClassForTest("test5", 5);
        arrayList.add(c5);
        ClassForTest c6 = new ClassForTest("test6", 6);
        arrayList.add(c6);
        ClassForTest c7 = new ClassForTest("test7", 7);
        arrayList.add(c7);
        ClassForTest c8 = new ClassForTest("test8", 8);
        arrayList.add(c8);
        Iterator<ClassForTest> iterator = arrayList.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), c1);
        Assert.assertTrue(iterator.hasNext());
        iterator.remove();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), c3);
        ClassForTest c9 = new ClassForTest("test9", 9);
        arrayList.add(2, c9);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), c9);
        arrayList.delete(3);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(iterator.next(), c5);
        ClassForTest c10 = new ClassForTest("test10", 10);
        arrayList.set(4, c10);
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
        CustomArrayList<ClassForTest> arrayList = new CustomArrayList<>();
        ClassForTest c1 = new ClassForTest("test1", 1);
        arrayList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        arrayList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        arrayList.add(c3);

        CustomArrayList<ClassForTest> clone = (CustomArrayList<ClassForTest>) arrayList.clone();
        Assert.assertFalse(clone == arrayList);
        Assert.assertTrue(clone.size() == arrayList.size());

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
        CustomArrayList<ClassForTest> arrayList = new CustomArrayList<>();

        try {
            arrayList.toArray(null);
        } catch (Exception ex) {
            LOG.error("", ex);
            Assert.assertTrue(ex instanceof NullPointerException);
        }

        ClassForTest c1 = new ClassForTest("test1", 1);
        arrayList.add(c1);
        ClassForTest c2 = new ClassForTest("test2", 2);
        arrayList.add(c2);
        ClassForTest c3 = new ClassForTest("test3", 3);
        arrayList.add(c3);
        ClassForTest[] array = arrayList.toArray(new ClassForTest[0]);
        Assert.assertTrue(c1 == array[0]);
        Assert.assertEquals(c1, array[0]);
        Assert.assertTrue(c2 == array[1]);
        Assert.assertEquals(c2, array[1]);
        Assert.assertTrue(c3 == array[2]);
        Assert.assertEquals(c3, array[2]);
        LOG.debug("end\n");
    }
}
