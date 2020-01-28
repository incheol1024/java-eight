
*다음 스택 구현이 있다고 하자.*

<pre><code>
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
</pre></code>

#####이 자료구조에 올바른 요소가 포함되지 않게 하는 방법 두 가지를 설명하라.

1. Not Thread Safe 
    * 위 스택 클래스는 *스레드 세이프* 하지 않다. 
    * 따라서 스레드 10개를 생성하여 스레별로 한번 씩 push 메서드를 호출한 뒤
    * 해당 스택 객체에 데이터가 10개가 적재 되어 있는지 확인해보자.
    
     