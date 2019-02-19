package ua.procamp.streams.stream;

import ua.procamp.streams.function.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AsIntStream implements IntStream {

    private int[] data;

    private List<Operation> operations = new ArrayList<>();

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
        this.operations.add(new Operation() {
            @Override
            Integer handle(int e) {
                if (predicate.test(e)) {
                    return e;
                } else {
                    return null;
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

        this.operations.add(new Operation() {

            @Override
            Integer handle(int e) {
                return mapper.apply(e);
            }

        });
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {


        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {

        for (int i : data) {
            for(Operation oper : operations) {
                Integer el = oper.handle(i);
                if (el != null) {
                    op.apply()
                }
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        abstract Integer handle(int e);
    }


}
