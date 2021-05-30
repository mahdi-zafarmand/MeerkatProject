/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.general;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author aabnar
 * @param <E>
 */
public class DynamicArray<E> {
    
    /**
     *
     */
    public static int DEFAULT_SIZE = 200;
    
    /*with out the maximum array size we will be getting heap size error*/

    /**
     *
     */
    
    public static int MAX_ARRAY_SIZE = 10000000;
    
    LinkedList<Object[]> dynamicArray = new LinkedList<>();
    LinkedList<Integer> arrayMaxIndex = new LinkedList<>();
    //LinkedList<Integer> freeSpace = new LinkedList<>();
    
    int size = 0;
    
    /**
     *
     * @param pinitialSize
     */
    public DynamicArray(int pinitialSize) {
        int size = Math.min(MAX_ARRAY_SIZE, pinitialSize);
        E[] arr = (E[]) new Object[size];
        dynamicArray.add(arr);
        arrayMaxIndex.add(size - 1);
    }
    
    /**
     *
     * @param pintSize
     */
    public void increaseSize(int pintSize) {
        E[] newArray = (E[]) new Object[pintSize];
        dynamicArray.add(newArray);
        int prevMax = -1;
        if (arrayMaxIndex.size() > 0) {
            prevMax = arrayMaxIndex.getLast();
        }
        arrayMaxIndex.add(prevMax + pintSize);
    }
    
    
    /**
     *
     */
    public void increaseSize() {
        increaseSize(DEFAULT_SIZE);
    }
    
    /**
     *
     * @param eid
     * @param e
     */
    public void add(int eid, E e) {
        int listIndex = 0;
        while (eid > arrayMaxIndex.getLast()) {
            increaseSize();
        }
        
        for (int maxIndex : arrayMaxIndex) {
            if (eid <= maxIndex) {
                break;
            }
            listIndex++;
        }

        int offset = 0;
        if (listIndex > 0) {
            offset += arrayMaxIndex.get(listIndex - 1) + 1;
        }
        if (dynamicArray.get(listIndex)[eid - offset] == null) {
            dynamicArray.get(listIndex)[eid - offset] = e;
            size++;    
        }
    }
        
    /**
     *
     * @param eid
     */
    public void remove(int eid) {
        int index = 0;
        for (int maxIndex : arrayMaxIndex) {
            if (eid <= maxIndex) {
                break;
            }
            index++;
        }
        
        int offset = 0;
        if (index > 0) {
            offset += arrayMaxIndex.get(index - 1) + 1;
        }
        dynamicArray.get(index)[eid - offset] = null;
        size--;
    }
    
    /**
     *
     * @param pintIndex
     * @return
     */
    public E get(int pintIndex) {
        int lstindex = 0;
        boolean inrange = false;
        for (int maxIndex : arrayMaxIndex) {
            if (pintIndex <= maxIndex) {
                lstindex = arrayMaxIndex.indexOf(maxIndex);
                inrange = true;
                break;
            }
        }
        if (!inrange) {
            return null;
        }
        int offset = 0;
        if (lstindex > 0) {
            offset = arrayMaxIndex.get(lstindex - 1) + 1;
        }
        
        
        try {
            return (E) dynamicArray.get(lstindex)[pintIndex - offset];
        } catch (Exception e) {
            System.out.println("Index : " + pintIndex);
            System.out.print("offset : " + offset);
            e.printStackTrace();
            System.out.println("NULL Edge");
            return null;
        }
        
        
    }
    
    /**
     *
     * @return
     */
    public int getMaxIndex() {
        return arrayMaxIndex.getLast();
    }
    
    /**
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     *
     */
    public void clear() {
        dynamicArray = new LinkedList<>();
        arrayMaxIndex = new LinkedList<>();
        size = 0;
    }

    /**
     *
     * @return
     */
    public List<Integer> getIds() {
        if (getMaxIndex() > -1 ) {
            List<Integer> ids = new ArrayList<>(size());

            for (int i = 0 ; i <= getMaxIndex(); i++) {
                if (get(i) != null) {
                    ids.add(i);
                }
            }
            return ids;
        }
        return new ArrayList<Integer>();
    }
    
    /**
     *
     * @return
     */
    public List<E> getObjects() {
        if (getMaxIndex() > -1 ) {
            List<E> ids = new ArrayList<>(size());

            for (int i = 0 ; i <= getMaxIndex(); i++) {
                if (get(i) != null) {
                    ids.add(get(i));
                }
            }
            return ids;
        }
        return new ArrayList<>();
    }
}
