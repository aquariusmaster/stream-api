package ua.procamp.streams.stream;

import ua.procamp.streams.function.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AsIntStream implements IntStream {

    private int[] data;

    private List<Operation> operationsPipeline = new ArrayList<>();

    private AsIntStream() {
        // To Do
    }

    public static IntStream of(int... values) {
        AsIntStream stream = new AsIntStream();
        stream.data = values;
        return stream;
    }

    @Override
    public Double average() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer max() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer min() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer sum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        this.operationsPipeline.add(new Operation() {
            @Override
            int handle(int e) {
                if (predicate.test(e)) {
                    return e;
                } else {
                    this.setHasValue(false);
                    return 0;
                }
            }
        });
        return this;
    }

    @Override
    public void forEach(IntConsumer action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        this.operationsPipeline.add(new Operation() {

            @Override
            int handle(int e) {
                int val = mapper.apply(e);
                System.out.println("Map=" + val);
                return val;
            }

        });
        return this;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {


        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        System.out.println("Reduce start=" + identity);
        label: for (int i : data) {
            for(Operation oper : operationsPipeline) {
                int el = oper.handle(i);
                System.out.println("Handle: el=" + i + ", identity=" + identity + ", operation has value=" + oper.isHasValue());
                if (oper.isHasValue()) {
                    identity = op.apply(identity,i);
                } else {
                    oper.setHasValue(true);
                    continue label;
                }
            }
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    private class Operation<T> {
//        private T operator;
//
//        private Operation(T operator) {
//            this.operator = operator;
//        }
//
//        public T getOperator() {
//            return operator;
//        }
//    }

    private abstract static class Operation {
        private boolean hasValue = true;
        abstract int handle(int e);

        public boolean isHasValue() {
            return hasValue;
        }

        public void setHasValue(boolean hasValue) {
            this.hasValue = hasValue;
        }
    }



}
