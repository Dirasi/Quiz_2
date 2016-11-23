package com.example.pustikom.adapterplay.com.example.pustikom.user;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by pustikom on 04/11/16.
 */

public class StudentList implements List<Student> {
    private static ArrayList<Student> studentList;

    public StudentList() {
        studentList = new ArrayList<>();
    }

    @Override
    public int size() {
        return studentList.size();
    }

    @Override
    public boolean contains(Object o) {
        //loop through all of the content to check if the o
        return studentList.contains(o);
    }

    @NonNull
    @Override
    public Iterator<Student> iterator() {
        return studentList.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return studentList.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] a) {
        return studentList.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return studentList.remove(o);
    }


    @Override
    public boolean containsAll(Collection<?> c) {
        return studentList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Student> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Student> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    /**
     *  if current list is empty return true, false otherwise
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        if (studentList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  When adding student set the id of the student according to the current row index allocated
     *
     * @param student
     * @return
     */
    @Override
    public boolean add(Student student) {
        student.setId(studentList.size());
        studentList.add(student);
        return true;
    }

    /**
     *  clear the list
     */
    @Override
    public void clear() {
        studentList.clear();
    }

    /**
     *  return current student
     *
     * @param index
     * @return
     */
    @Override
    public Student get(int index) {
        Student student = studentList.get(index);
        return student;
    }

    /**
     *  Replace current Element of the given index with passed element
     *
     * @param index
     * @param element
     * @return previous student element before replaced
     */
    @Override
    public Student set(int index, Student element) {
        //Don't forget you need to change student id
        Student prevstudent =  studentList.get(index);
        studentList.set(index, element);
        element.setId(index);
        return prevstudent;
    }

    /**
     *  Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, Student element) {
        //add new element at certain index affect all elements post given index (the id)
        studentList.add(index, element);
        resetOrder(index);
    }

    /**
     * : Remove student of given index, then recalculate all affected student ids
     *
     * @param index
     * @return
     */
    @Override
    public Student remove(int index) {
        Student student = studentList.remove(index);
        resetOrder(index);
        return student;
    }

    public void resetOrder(int index) {
        for(int j = index; j<studentList.size();j++)
        {
            studentList.get(j).setId(j);
        }
    }

    @Override
    public int indexOf(Object o) {
        return studentList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return studentList.lastIndexOf(o);
    }

    @Override
    public ListIterator<Student> listIterator() {
        return studentList.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<Student> listIterator(int index) {
        return studentList.listIterator(index);
    }

    @NonNull
    @Override
    public List<Student> subList(int fromIndex, int toIndex) {
        return studentList.subList(fromIndex,toIndex);
    }
}
