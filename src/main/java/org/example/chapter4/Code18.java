package org.example.chapter4;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 which is appropriate for use inheritance
    1.subclass is really ubtype of super class ans they has a 'is-a' relation;
        there is a example of vilation of this principle in java platform, Stack extends Vector, but actually Stack is not a Vector;
    2.It is safe to use inheritance within a package
        cause subclass and superclass are under control of the same programmers
    3.some specific class which is designed for extending
        cause the purposes of these classes are just for extending.

 besides the few situations listed above, use inheritance may bring damage of your system,
 cause inheritance will violates encapsulation, expose the internal detail to other clients.

 one method to solve this problem is use composition, that is use a private field that references an
 instance of the existing class. This design is called composition because the existing class becomes a component,
 the related operations are all forwarded to it.
 */

public class Code18 {





    // Wrapper class - uses composition in place of inheritance
    // be careful, the class it extended is Forwarding class, not Set
     class InstrumentedSet<E> extends ForwardingSet<E> {
        private int addCount = 0;
        public InstrumentedSet(Set<E> s) {
            super(s);
        }
        @Override public boolean add(E e) {
            addCount++;
            return super.add(e);
        }
        @Override public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }
        public int getAddCount() {
            return addCount;
        }
    }


    // 转发类的实例
     class ForwardingSet<E> implements Set<E> {
        private final Set<E> s;

        public ForwardingSet(Set<E> s) {
            this.s = s;
        }

        public void clear() {
            s.clear();
        }

        public boolean contains(Object o) {
            return s.contains(o);
        }

        public boolean isEmpty() {
            return s.isEmpty();
        }

        public int size() {
            return s.size();
        }

        public Iterator<E> iterator() {
            return s.iterator();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        public boolean add(E e) {
            return s.add(e);
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        public <T> T[] toArray(T[] a) {
            return s.toArray(a);
        }

        @Override
        public boolean equals(Object o) {
            return s.equals(o);
        }

        @Override
        public int hashCode() {
            return s.hashCode();
        }

        @Override
        public String toString() {
            return s.toString();
        }
    }
}
