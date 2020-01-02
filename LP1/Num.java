package vxk180003;

import java.util.*;

//Starter code for lp1.
//Version 1.0 (8:00 PM, Wed, Sep 5).

//Change following line to your NetId

public class Num implements Comparable<Num> {

    static long defaultBase = 10; // Change as needed
    long base = defaultBase; // Change as needed
    long[] arr; // array to store arbitrarily large integers
    boolean isNegative; // boolean flag to represent negative numbers
    int len; // actual number of elements of array that are used; number is stored in
    // arr[0..len-1]

    /**
     * @author saumya
     * @param s
     */
//    public Num(String s) {
//        int count = 9;
//        isNegative = (s.charAt(0) == '-') ? true : false;
//        int stringLength = (isNegative) ? s.length() - 1 : s.length();
//        int size = lengthInDiffBase(stringLength, base);
//        this.arr = new long[size];
//        int index = 0;
//
//        for (int i = 0; i < stringLength; i = i + count) {
//            if (stringLength - i <= count) {
//                this.arr[index++] = (isNegative) ? Long.parseLong(s.substring(1, s.length() - i))
//                        : Long.parseLong(s.substring(0, s.length() - i));
//                this.len++;
//            } else {
//                this.arr[index++] = Long.parseLong(s.substring(s.length() - i - count, s.length() - i));
//                this.len++;
//
//            }
//        }
//
//    }
    public Num(String s) {
        isNegative = (s.charAt(0) == '-') ? true : false;
        s = (isNegative) ? s.substring(1) : s;
        int size = lengthInDiffBase(s.length(), base);
        this.arr = new long[size];
        for (int i = 0; i < s.length(); i++) {
            this.arr[s.length()-1-i] = Long.parseLong(String.valueOf(s.charAt(i)));
            this.len++;
        }

    }

    /**
     * @author saumya
     * @param a
     */
    private Num(Num a) {

        this.len = a.len;
        this.arr = new long[a.arr.length];
        this.base = a.base;
        for (int i = 0; i < len; i++) {
            this.arr[i] = a.arr[i];
        }
        this.isNegative = a.isNegative;

    }

    /**
     * @author saumya
     * @param x
     */
    public Num(long x) {
        isNegative = (x < 0) ? true : false;
        long num = Math.abs(x);
        int digit_count = 0;
        while (num != 0) {
            num /= 10;
            ++digit_count;
        }
        len = digit_count;
        if (x == 0) {
            len = 1;
        }

        int length = (int) Math.ceil((len + 1 / Math.log10(this.base())) + 1);
        arr = new long[length];
        num = Math.abs(x);
        int i = 0;
        while (num != 0) {
            arr[i] = num % 10;
            num /= 10;
            i++;
        }

    }

    /**
     * @author saumya
     * @param size
     */
    private Num(int size) {
        arr = new long[size];
        len = 0;
        isNegative = false;

    }

    /**
     * @author saumya
     * @param x
     * @param b
     */
    private Num(long x, long b) {
        long temp = x;
        this.base = b;
        Deque<Long> stack = new ArrayDeque<>();
        while (true) {
            stack.push(temp % b);
            temp = temp / b;
            if (temp == 0) {
                break;
            }
        }

        arr = new long[stack.size() + 2];
        len = 0;
        isNegative = false;
        int i = 0;
        while (!stack.isEmpty()) {
            this.arr[i] = stack.removeLast();
            this.len++;
            i++;
        }

    }

    public Num() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @param len
     * @param base
     * @return
     */
    private int lengthInDiffBase(int len, long base) {
        double Log_10B = Math.log10(base);
        int diffBaseLength = (int) Math.ceil(((len + 1) / Log_10B) + 1);
        return diffBaseLength;
    }

    /**
     * @author vedant
     * @param a
     * @param b a+b
     * @return
     */
    public static Num add(Num a, Num b) {
        if (a.isNegative && b.isNegative) {
            a.isNegative = false;
            b.isNegative = false;
            Num num = add(a, b);
            num.isNegative = true;
            return num;
        } else if (a.isNegative) {
            a.isNegative = false;
            return subtract(b, a);
        } else if (b.isNegative) {
            b.isNegative = false;
            return subtract(a, b);
        } else {
            long[] revA = cropArray(a.arr, a.len);
            long[] revB = cropArray(b.arr, b.len);
            long[] sum = new long[Math.max(revA.length, revB.length) + 1];
            long temp;
            int i = 0;
            while (i < Math.min(revA.length, revB.length)) {
                temp = sum[i] + revA[i] + revB[i];
                sum[i] = temp % defaultBase;
                sum[i + 1] = temp / defaultBase;
                i++;
            }
            if (revA.length > revB.length) {
                while (i < revA.length) {
                    temp = sum[i] + revA[i];
                    sum[i] = temp % defaultBase;
                    sum[i + 1] = temp / defaultBase;
                    i++;
                }
            } else {
                while (i < revB.length) {
                    temp = sum[i] + revB[i];
                    sum[i] = temp % defaultBase;
                    sum[i + 1] = temp / defaultBase;
                    i++;
                }
            }
			/*sum = reverseArray(sum, sum.length);
			boolean flag = false;
			for (i = 0; i < sum.length; i++) {
				if (sum[i] != 0 && !flag)
					break;
			}
			long[] finalSum = Arrays.copyOfRange(sum, i, sum.length);
			finalSum = reverseArray(finalSum, finalSum.length);*/
            Num num = new Num();
            num.arr = sum;
            num.base = a.base;
            num.len = i;
            return num;
        }
    }

    /**
     * @author vedant
     * @param arr
     * @param len
     * @return
     */
    private static long[] cropArray(long[] arr, int len) {
        long[] rev = new long[len];
        int j = 0;
        for (int i = 0; i < len; i++)
            rev[j++] = arr[i];
        return rev;
    }

    /**
     * @author vedant
     * @param arr
     * @param len
     * @return
     */
    private static long[] reverseArray(long[] arr, int len) {
        long[] rev = new long[len];
        int j = 0;
        for (int i = len - 1; i >= 0; i--)
            rev[j++] = arr[i];
        return rev;
    }

    /**
     * @author vedant a-b
     * @param a
     * @param b
     * @return
     */
//    public static Num subtract(Num a, Num b) {
//        if (a.isNegative && b.isNegative) {
//            a.isNegative = false;
//            b.isNegative = false;
//            return subtract(b, a);
//        } else if (a.isNegative) {
//            a.isNegative = false;
//            Num num = add(a, b);
//            num.isNegative = true;
//            return num;
//        } else if (b.isNegative) {
//            b.isNegative = false;
//            return add(a, b);
//        } else {
//            if (b.len > a.len) {
//                Num num = subtract(b, a);
//                num.isNegative = true;
//                return num;
//            } else if (b.len == a.len) {
//                for (int i = 0; i < a.len; i++) {
//                    if (b.arr[i] > a.arr[i])
//                        subtract(b, a);
//                }
//            }
//            long[] revA = cropArray(a.arr, a.len);
//            long[] revB = cropArray(b.arr, b.len);
//            long[] sum = new long[Math.max(revA.length, revB.length)];
//            long temp;
//            int i = 0;
//            while (i < Math.min(revA.length, revB.length)) {
//                temp = sum[i] + revA[i] - revB[i];
//                sum[i] = temp;
//                if (temp < 0) {
//                    sum[i] = defaultBase + temp;
//                    sum[i + 1] = -1;
//                }
//                i++;
//            }
//            while (i < revA.length) {
//                temp = sum[i] + revA[i];
//                sum[i] = temp;
//                if (temp < 0) {
//                    sum[i] = defaultBase + temp;
//                    sum[i + 1] = -1;
//                }
//                i++;
//            }
//            sum = reverseArray(sum, sum.length);
//            boolean flag = false;
//
//            for (i = 0; i < sum.length; i++) {
//                if (sum[i] != 0 && !flag)
//                    break;
//            }
//            long[] finalSum = Arrays.copyOfRange(sum, i, sum.length);
//            Num num = new Num();
//            num.arr = finalSum;
//            num.base = a.base;
//            num.len = finalSum.length;
//            return num;
//        }
//    }
    public static Num subtract(Num a, Num b) {


        Num result;

        Num a1 = new Num(a);
        a1.isNegative = false;
        Num b1 = new Num(b);
        b1.isNegative = false;


        if (b.isNegative && !a.isNegative) {
            result= add(a1, b1);
            result.isNegative = false;
            return result;
        }
        else if (a.isNegative && b.isNegative)
            subtract(b1, a1);
        else if (a.isNegative && !b.isNegative) {
            result = add(a1, b1);
            result.isNegative = true;
            return result;
        }


        if (a.len > b.len) {
            result = minus(a1, b1);
            result.isNegative = false;
        } else if (a.len < b.len) {
            result = minus(b1, a1);
            result.isNegative = true;
        } else {
            int index = a.len - 1;
            while (index >= 0 && a.arr[index] == b.arr[index])
                index--;
            if (a.arr[index] > b.arr[index]) {
                result = minus(a1, b1);
                result.isNegative = false;
            } else if (a.arr[index] < b.arr[index]) {
                result = minus(b1, a1);
                result.isNegative = true;
            } else {
                result = new Num((long)0);
            }
        }

        return result;
    }

    private static Num minus(Num a, Num b) {
        boolean borrow = false;
        Num result = new Num(a.arr.length);
        result.len = a.len;
        for (int i = 0; i < a.len; i++) {
            if (i < b.len) {
                if (borrow) {
                    a.arr[i]--;

                }

                long temp = a.arr[i] - b.arr[i];
                if (temp >= 0) {
                    result.arr[i] = temp;
                    borrow = false;
                } else {
                    result.arr[i] = temp + a.base;
                    borrow = true;
                }


            } else {
                if (borrow) {
                    a.arr[i]--;
                    if (a.arr[i] < 0) {
                        result.arr[i] = a.arr[i] + a.base;
                    } else {
                        result.arr[i] = a.arr[i];
                        borrow = false;
                    }
                } else {
                    result.arr[i] = a.arr[i];
                }


            }
        }


        while (result.len + 1 > 0 && result.arr[result.len - 1] == 0)
            result.len--;
        return result;
    }

    /**
     * @author Pratik
     * @param a
     * @param b
     * @return
     */
    public static Num product(Num a, Num b) {
        long arr1[] = new long[a.len];
        long arr2[] = new long[b.len];
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < a.len; i++) {
            arr1[i] = a.arr[i];
        }
        for (int i = 0; i < b.len; i++) {
            arr2[i] = b.arr[i];
        }

        long prod[] = new long[a.len + b.len];

        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                prod[i + j] += arr1[i] * arr2[j];
            }
        }

        for (int i = 0; i < prod.length; i++) {
            long carry = prod[i] / a.base;
            long mod = prod[i] % a.base;

            if ((i + 1) < prod.length) {
                prod[i + 1] += carry;
            }
            stringBuilder.insert(0, mod);
        }

        while (stringBuilder.charAt(0) == '0' && stringBuilder.length() > 1)
            stringBuilder.deleteCharAt(0);

        Num product = new Num(stringBuilder.toString());

        if ((a.isNegative && b.isNegative) || (!a.isNegative && !b.isNegative))
            product.isNegative = false;
        else
            product.isNegative = true;

        return product;
    }

    /**
     * @author saumya
     * @param a
     * @param n
     * @return
     */
    // Use divide and conquer
    public static Num power(Num a, long n) {
        Num pow;

        if (n < 0)
            return new Num(0L);
        if (n == 0)
            return new Num(1L);
        else if (n == 1)
            return new Num(a);
        else if (n == 2)
            return product(a, a);
        else {
            if (n % 2 == 0) {
                Num p = power(a, n / 2);
                pow = product(p, p);
            } else
                pow = product(power(a, (n - 1) / 2), power(a, (n + 1) / 2));
        }
        return pow;
    }

    private static Num power(Num a, Num n)
    {
        Num pow;
        if (n.compareTo(new Num(0, a.base)) < 0)
            {
                return new Num(0L);
            }

        if (n.compareTo(new Num(0, a.base())) == 0)
            {
                return new Num(1L);
            }
        else
            {
                pow = power(product(a, a), n.by2());
                if (mod(n, new Num(2, n.base)).compareTo(new Num(0, n.base)) == 0)
                {
                    return pow;
                }
                else
                {
                    return product(pow, a);
                }
            }
    }

    private static Num div(Num a, Num b) {


        if (a.compareTo(b) < 0)
            return new Num((long)0);

        else if (b.compareTo(new Num((long)1)) == 0)
            return new Num(a);


        else {

            Num begin = new Num((long)0);
            Num end = new Num(a);

            while (true) {

                Num mid = add(begin, end).by2();

                int comp = a.compareTo(product(mid, b));
                if (comp == 0) {
                    return mid;
                } else if (comp < 0) {
                    end = mid;

                } else {
                    if (b.compareTo(subtract(a, product(mid, b))) > 0)
                        return mid;
                    else
                        begin = mid;

                }
            }

        }
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
        Num result = null;
        if (b.compareTo(new Num(0L)) == 0)
            return result;

        else {
            Num a1 = new Num(a);
            a1.isNegative = false;
            Num b1 = new Num(b);
            b1.isNegative = false;

            result = div(a1, b1);

            if (a.isNegative ^ b.isNegative)
                if(!(result.compareTo(new Num(0L))==0))
                    result.isNegative = true;
        }

        return result;
    }

    // return a%b
    /**
     * @author saumya
     * @param a
     * @param b
     * @return
     */
    public static Num mod(Num a, Num b) {
        Num answer;

        Num x1 = new Num(a);
        x1.isNegative = false;
        Num y1 = new Num(b);
        y1.isNegative = false;

        answer = modulus(x1, y1);

        return answer;
    }

    /**
     * @author saumya Function to calculate modulus
     * @param x
     * @param y
     * @return
     */
    private static Num modulus(Num x, Num y) {

        Num begin = new Num(0L);
        Num end = new Num(x);

        if (y.compareTo(begin) == 0 || y.compareTo(begin) < 0) {
            return null;
        } else if (x.compareTo(begin) == 0 || y.compareTo(new Num("1")) == 0)
            return begin;

        else if (x.compareTo(new Num("1")) == 0 || x.compareTo(y) < 0)
            return new Num(x);

        else {
            while (true) {
                Num temp = add(begin, end);
                Num mid = temp.by2();

                int comp = x.compareTo(product(mid, y));
                if (comp == 0) {
                    return new Num(0L);
                } else if (comp < 0) {
                    end = mid;

                } else {
                    if (y.compareTo(subtract(x, product(mid, y))) > 0)
                        return subtract(x, product(mid, y));
                    else
                        begin = mid;
                }
            }

        }
    }

    /**
     * @author vishal
     * @param a
     * @return
     * @throws ArithmeticException
     */
    // Use binary search
    public static Num squareRoot(Num a) throws ArithmeticException {
        if (a.isNegative) {
            throw new ArithmeticException("Square Root of Negative number is undefined!");
        }
        Num zero = new Num(0);
        Num one = new Num(1);
        if (a.compareTo(zero) == 0 || a.compareTo(one) == 0)
            return zero;
        Num sum = new Num(0);
        Num low = new Num(0);
        Num high = new Num();
        Num pivot = new Num();
        high.len = a.len;

        int i = 0;
        for (long z : a.arr) {
            high.arr[i++] = z;
        }
        while (low.compareTo(high) < 0) {

            sum = add(low, high);
            pivot = sum.by2();

            if (low.compareTo(pivot) == 0)
                return pivot;

            Num product = product(pivot, pivot);

            if (product.compareTo(a) == 0)
                return pivot;

            else if (product.compareTo(a) < 0)
                low = pivot;
            else
                high = pivot;
        }
        return pivot;
    }

    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1
    // otherwise
    /**
     * @author saumya
     */
    public int compareTo(Num other) {
        if (this.isNegative && !other.isNegative) {
            return -1;
        } else if (!this.isNegative && other.isNegative) {
            return 1;
        } else if (!this.isNegative && !other.isNegative) {
            if (this.len > other.len) {
                return 1;
            } else if (this.len < other.len) {
                return -1;
            } else {
                int i = this.len - 1;
                while (this.arr[i] == other.arr[i]) {
                    if (i == 0) {
                        return 0;
                    }
                    i--;
                }
                return (this.arr[i] > other.arr[i]) ? 1 : -1;
            }
        } else {
            this.isNegative = false;
            other.isNegative = false;
            int result = this.compareTo(other);
            this.isNegative = true;
            other.isNegative = true;
            if (result == 1) {
                return -1;
            } else if (result == -1) {
                return 1;
            } else {
                return 0;
            }
        }

    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    /**
     * @author Vishal
     */
    public void printList() {
        System.out.print(base() + ": ");

        if (isNegative)
            System.out.print("- ");
        System.out.print("(");
        int i = 0;
        while (i < this.len) {
            if (i == this.len - 1)
                System.out.print(arr[i]);
            else
                System.out.print(this.arr[i] + ", ");
            i++;
        }
        System.out.println(")");
    }

    // Return number to a string in base 10
    /**
     * @author Vishal
     */
    public String toString() {
        this.len = this.arr.length;
        StringBuilder output = new StringBuilder();
        if (isNegative)
            output.append("-");
        long temp, countDigits = 0, maxNo = base - 1;
        for (int i = len - 1; i >= 0; i--) {
            if (i == len - 1) {
                output.append(arr[i]);
                continue;
            }
            temp = arr[i];
            while (temp < maxNo && temp > 0) {
                temp = temp / 10;
                countDigits++;
            }
            output.append(arr[i]);
        }
        return output.toString();
    }

    public long base() {
        return base;
    }

    // Return number equal to "this" number, in base=newBase
    /**
     * @author vedant
     * @param newBase
     * @return
     */
    public Num convertBase(int newBase) {
        Num num = new Num();
        long newLongBase = newBase;
        Num nBase = new Num(newLongBase);
        for (int i = 0; i < this.len; i++)
            num = add(num, new Num(Num.product(new Num((long)arr[i]), Num.power(new Num((long)this.base), i))));
        num.base = this.base;
		/*Num mod;
        List<Long> li = new ArrayList<>();
        while (num.len > 0) {
            mod = mod(num, nBase);
            li.add(mod.arr[0]);
            num = divide(num, nBase);
        }
        long arr[] = new long[li.size()];
        for(int i=0; i<li.size(); i++)
            arr[i]=li.get(i);
        num = new Num();
        num.arr = arr;
        num.len = arr.length;
        num.base = newBase;*/
        return this;
    }

    /**
     * @author pratik
     * @return
     */
    // Divide by 2, for using in binary search
    public Num by2() {
        StringBuilder stringBuilder = new StringBuilder();
        String answer;
        long carry = 0;

        for (int i = this.len - 1; i >= 0; i--) {
            stringBuilder.append((this.base * carry + arr[i]) / 2);
            carry = arr[i] % 2;
        }

        answer = stringBuilder.toString();
        if (answer.charAt(0) == '0' && answer.length() > 1) {
            answer = answer.substring(1, answer.length());
        }
        return new Num(answer);
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*. There is no unary minus operator.
    /**
     * @author saumya
     * @param expr
     * @return
     */
    public static Num evaluatePostfix(String[] expr) {

        Stack<Num> stack = new Stack<>();
        Num tempvar1;
        Num tempvar2;
        for (int i = 0; i < expr.length; i++) {
            String token = expr[i];
            if (!isOperand(token)) {

                stack.push(new Num(token));
            } else {
                tempvar1 = stack.pop();
                tempvar2 = stack.pop();
                stack.push(str_eval(token, tempvar1, tempvar2));

            }
        }
        return stack.pop();
    }

    /**
     * @author saumya Check if the string is an operand or not
     * @param str1: a string
     * @return boolean: True if string contains operand else False
     */
    private static boolean isOperand(String str1) {
        if (str1 == "+" || str1 == "-" || str1 == "/" || str1 == "*" || str1 == "%" || str1 == "^") {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @author saumya
     * @param op
     * @param temp1
     * @param temp2
     * @return
     */
    private static Num str_eval(String op, Num temp1, Num temp2) {
        switch (op) {
            case "+": {
                return add(temp2, temp1);
            }
            case "-": {
                return subtract(temp2, temp1);
            }
            case "*": {
                return product(temp2, temp1);
            }
            case "/": {
                return divide(temp2, temp1);
            }
            case "%": {
                return mod(temp2, temp1);
            }
            case "^": {
                return power(temp2, temp1);
            }
        }
        return null;
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*. There is no unary minus operator.
    /**
     * @author vedant
     * @param expr
     * @return
     */
    public static Num evaluateInfix(String[] expr) {
        Queue<String> out = new LinkedList<String>();
        Stack<String> stc = new Stack<>();
        for (String ch : expr) {
            if ("^ * % / + -".contains(ch)) {
                if (!stc.isEmpty()) {
                    if ("* / %".contains(ch)) {
                        while (!stc.isEmpty() && !"( + -".contains(stc.peek())) {
                            if (stc.peek().equals("("))
                                stc.pop();
                            out.add(stc.pop());
                        }
                    } else if ("+ -".contains(ch)) {
                        while (!stc.isEmpty() && !stc.peek().equals("(")) {
                            if (stc.peek().equals("("))
                                stc.pop();
                            out.add(stc.pop());
                        }
                    }
                    stc.push(ch);
                } else {
                    stc.push(ch);
                }
            } else if (ch.equals("("))
                stc.push("(");
            else if (ch.equals(")")) {
                while (!stc.peek().equals("("))
                    out.add(stc.pop());

                stc.pop();
            } else
                out.add(ch);

        }
        while (!stc.isEmpty())
            out.add(stc.pop());
        String[] st = new String[out.size()];
        int j = 0;
        while (!out.isEmpty()) {
            // System.out.print(out.peek()+" ");
            st[j++] = out.poll();
        }
        return evaluatePostfix(st);
    }

    public static void main(String[] args) {
        Num x = new Num("3659535532566681673026857047264590495633096120170316011130546064934049533282760410899967541");
        x.convertBase(1000);
        System.out.println();
//        Num x = new Num((long) 1987);
//        Num y = new Num((long) 20);
//        Num z = Num.add(x, y);
//        Num mod = mod(x,y);
//        System.out.println(z);
//        Num a = Num.power(x, 8);
//        System.out.println(a);
//        if (z != null)
//            z.printList();
//        evaluateInfix(new String[] { "1", "+", "2", "*", "3", "/", "4", "-", "5", "%", "6", "*", "7", "/", "8", "+",
//                "9", "-", "1" });
    }
}
