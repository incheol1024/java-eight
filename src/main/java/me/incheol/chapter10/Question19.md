
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
</pre><code>

이 자료구조에 올바른 요소가 포함되지 않게 하는 방법 두 가지를 설명하라.