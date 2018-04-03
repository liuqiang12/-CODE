package utils.typeHelper;

import java.util.HashMap;
import java.util.Map;

/** 
 * 循环队列 
 * @author WWX 
 */  
public class CirQueue<E> {  
    //对象数组，队列最多存储a.length-1个对象  
    E[] a;  
    //默认初始化大小  
    private static final int DEFAULT_SIZE=10;  
    //对首下标  
    int front;  
    //队尾下标  
    int rear;  
      
    public CirQueue(){  
        this(DEFAULT_SIZE);  
    }  
    /** 
     * 初始化指定长度的队列 
     * @param size 
     */  
    @SuppressWarnings("unchecked")  
    public CirQueue(int size){  
        a=(E[])(new Object[size]);  
        front=0;  
        rear=0;  
    }  
      
    /** 
     * 将一个对象追加到队列尾部 
     * @param obj 
     * @return 队列满时返回false,否则返回true 
     * @author WWX 
     */  
    public boolean enqueue(E obj){  
        if((rear+1)%a.length==front){  
            return false;  
        }else{  
            a[rear]=obj;  
            rear=(rear+1)%a.length;  
            return true;  
        }  
    }  
      
    /** 
     * 队列头部出队 
     * @return 
     * @author WWX 
     */  
    public E dequeue(){  
        if(rear==front)  
            return null;  
        else{  
            E obj =a[front];  
            front=(front+1)%a.length;  
            return obj;  
        }  
    }  
      
    /** 
     * 队列长度 
     * @return 
     * @author WWX 
     */  
    public  int size(){  
        return (rear-front)&(a.length-1);  
    }  
    //队列长度（另一种方法）  
    public int length(){  
        if(rear>front){  
            return rear-front;  
        }else  
            return a.length-1;  
    }  
      
    /** 
     * 判断是否为空  
     * @return 
     * @author WWX 
     */  
    public boolean isEmpty(){  
        return rear==front;  
    }  
      
  
  
    public static void main(String[] args) {  
        CirQueue<Map<Integer, Integer>> queue=new CirQueue<Map<Integer, Integer>>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(); //zgid 行号
        Map<Integer, Integer> map1 = new HashMap<Integer, Integer>(); //zgid 行号
        Map<Integer, Integer> map2 = new HashMap<Integer, Integer>(); //zgid 行号
        Map<Integer, Integer> map3 = new HashMap<Integer, Integer>(); //zgid 行号
        
        map.put(1, 0);
        map1.put(1, 1);
        map2.put(1, 2);
        map3.put(1, 3);
        queue.enqueue(map);  
        queue.enqueue(map1);  
        queue.enqueue(map2);  
        queue.enqueue(map3);  
        System.out.println("size="+queue.size());  
        int size=queue.size();  
        System.out.println("*******出栈操作*******");  
//        for(int i=0; i<size;i++){  
            System.out.print(queue.dequeue()+" ");  
//        }  
          
    }  
      
}  
