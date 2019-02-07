package one;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class AddTwoNumbers {

    public static void main(String[] args){
        AddTwoNumbers a = new AddTwoNumbers();
        ListNode l1 = a.creatListNode(new int[]{5,6,4});
        ListNode l2 = a.creatListNode(new int[]{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1});
        a.show(l1);
        a.show(l2);
        ListNode result = a.addTwoNumbers(l1, l2);
        a.show(result);
    }

    private class ListNode{
        int val;
        ListNode next;
        ListNode(int x){ val = x;}

        public String toString(){
            return val+"";
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(Integer.MIN_VALUE);
        ListNode temp = result;
        int num1, num2;
        int carry = 0; //表示进位
        while (l1 != null || l2 != null){
            num1 = num2 = 0;
            if (l1 != null){
                num1 = l1.val;
                l1 = l1.next;
            }

            if (l2 != null){
                num2 = l2.val;
                l2 = l2.next;
            }

            int resultNumber = (num1 + num2 + carry)%10;
            carry = (num1 + num2 + carry)/10;
            temp.next = new ListNode(resultNumber);
            temp = temp.next;
        }

        if (carry != 0)   temp.next = new ListNode(carry);
        return result.next;
    }

    public ListNode creatListNode(int[] a){
        ListNode first = new ListNode(a[0]);
        ListNode temp = first;
        for (int i = 1; i < a.length; i++){
            temp.next = new ListNode(a[i]);
            temp = temp.next;
        }
        return first;
    }

    public void show(ListNode first){
        for (ListNode temp = first; temp != null; temp = temp.next)
            System.out.print(temp.val + "  ");
        System.out.println();
    }
}
