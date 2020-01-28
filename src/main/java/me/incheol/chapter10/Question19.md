
*다음 스택 구현이 있다고 하자.*

```java
    public class Stack{
        class Node{ Object value; Object next; }
        private Node top;
        
        public void push(Object newValue) {
            Node n = new Node();
            n.value = newValue;
            n.next = top;
            top = n;
        }
        
        public Object pop() {
            if(top == null) return null;
            Node n = top;
            top = n.next;
            return n.value;
        }    
    }
```

* 이 자료구조에 올바른 요소가 포함되지 않게 하는 방법 두 가지를 설명하라.

1. Not Thread Safe 
    * 위 스택 클래스는 *스레드 세이프* 하지 않다. 
    * 따라서 스레드 10개를 생성하여 스레별로 한번 씩 push 메서드를 호출한 뒤
    * 해당 스택 객체에 데이터가 10개가 적재 되어 있는지 확인해보자.
    


```java
package me.incheol.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Question19 {


    public static void main(String[] args) throws InterruptedException {
        Question19 question19 = new Question19();
        question19.runTest();
    }


    private void runTest() throws InterruptedException {

        Stack stack = new Stack();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //실행자를 이용하여 Object객체 100개를 넣었다. 과연 100개 모두 정상적으로 들어갔을까?
        for(int i = 0; i < 100; i++) {
            executorService.submit(() -> {
               stack.push(new Object());
            });
        }
        

        Thread.sleep(1000);
        
        // Stack 클래스에 들어간 데이터를 꺼내보자 100개를 넣었으니 100개를 출력하면
        // 모두 오브젝트 객체의 주소가 나와야 한다.
        // null 값이 출력되면 덜 들어갔다는 얘기이다.
        int count = 0;
        for(int i = 0; i < 100; i++) {
            System.out.println(stack.pop());
            ++count;
        }
        
        
        // null 값이 출력 되면 안된다.
        System.out.println(count);

    }
    
    public class Stack{
        class Node{ Object value; Object next; }
        private Node top;

        public void push(Object newValue) {
            Node n = new Node();
            n.value = newValue;
            n.next = top;
            top = n;
        }

        public Object pop() {
            if(top == null) return null;
            Node n = top;
            top = (Node) n.next;
            return n.value;
        }
    }
}
```
     
* 결과
```java
.
.
.
java.lang.Object@238e0d81
java.lang.Object@31221be2
java.lang.Object@377dca04
null
null
100
```

콘솔로 출력한 결과값이다. 총 100번 루프를 돌았고 마지막 결과값 2개는 null이 나왔다.
이는 스택에 여러개의 스레드가 100개의 데이터를 저장하려고 시도했으나 *어떤 이유*에서 98개만 저장이 되었다.
*어떤 이유*는 무엇일까?

```java
        public void push(Object newValue) {
            Node n = new Node();
            n.value = newValue;
            n.next = top;
            top = n;
        }
```
Stack 클래스의 push 메서드를 다시 살펴보자.  
메서드 안에서는 Node 객체를 생성해서 node의 value와 next 상태를 변경한 뒤
stack 객체의 top 필드의 상태를 변경하려고 한다.
여기서 Node 객체는 메서드안에서 지역적(스레드별 스택프레임에서) 생성되므로 
스레드 간 공유되지 않는다.   
그러나 top 필드는 stack 객체 안에서 갖고있는 상태값이므로 push 메서드를 사용하고
 있는 스레드들이 공통으로 사용중이다. 
 결국 top 필드의 상태값의 원자성이 지켜지지 않으므로 생겨난 문제이다.
